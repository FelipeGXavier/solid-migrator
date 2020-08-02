package migrator.module.bar.postgres.dashboard;

import core.ConnectionJdbc;
import core.contracts.DatabaseRows;
import migrator.module.bar.tables.Dashboard;
import migrator.module.bar.tables.Notice;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Dependent
@Named("fooDashboardRows")
public class FooDashboardRows implements DatabaseRows {

    private ConnectionJdbc connectionJdbc;

    @Inject
    public FooDashboardRows(@Named("fooPostgres") ConnectionJdbc connectionJdbc){
        this.connectionJdbc = connectionJdbc;
    }

    public Collection<?> getDatabaseRows() throws SQLException {
        return this.selectDatabaseRows();
    }

    private List<Dashboard> selectDatabaseRows() throws SQLException {
        String sql = "select * from tbdashboard where migrated = false";
        DataSource connection = this.connectionJdbc.getConnection();
        QueryRunner run = new QueryRunner(connection);
        ResultSetHandler<List<Dashboard>> noticeMapper = new BeanListHandler<>(Dashboard.class);
        return run.query(sql, noticeMapper);
    }
}
