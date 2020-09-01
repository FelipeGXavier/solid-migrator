package migrator.systems.foo.postgres.dashboard;

import core.ConnectionJdbc;
import core.contracts.DatabaseRows;
import core.contracts.TableRefer;
import migrator.systems.foo.tables.Dashboard;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Named("FooDashboardRows")
public class FooDashboardRows implements DatabaseRows {

    private ConnectionJdbc connectionJdbc;

    @Inject
    public FooDashboardRows(@Named("FooPostgres") ConnectionJdbc connectionJdbc){
        this.connectionJdbc = connectionJdbc;
    }

    public Collection<? extends TableRefer> getDatabaseRows() throws SQLException {
        return this.selectDatabaseRows();
    }

    @Override
    public void updateRow(TableRefer tableRefer) throws SQLException {
        final String sql = "update dashboard set migrated = 1 where id = ?";
        DataSource connection = this.connectionJdbc.getConnection();
        PreparedStatement ps = connection.getConnection().prepareStatement(sql);
        ps.setLong(1, Long.parseLong(tableRefer.getRefer()));
        ps.executeUpdate();
    }

    private List<Dashboard> selectDatabaseRows() throws SQLException {
        final String sql = "select * from dashboard where migrated = 0";
        DataSource connection = this.connectionJdbc.getConnection();
        QueryRunner run = new QueryRunner(connection);
        ResultSetHandler<List<Dashboard>> noticeMapper = new BeanListHandler<>(Dashboard.class);
        return run.query(sql, noticeMapper);
    }
}
