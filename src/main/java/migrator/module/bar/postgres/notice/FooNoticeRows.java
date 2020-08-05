package migrator.module.bar.postgres.notice;

import core.ConnectionJdbc;
import core.contracts.DatabaseRows;
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
@Named("fooNoticeRows")
public class FooNoticeRows implements DatabaseRows {

    private ConnectionJdbc connectionJdbc;

    @Inject
    public FooNoticeRows(@Named("fooPostgres") ConnectionJdbc connectionJdbc){
        this.connectionJdbc = connectionJdbc;
    }

    public Collection<?> getDatabaseRows() throws SQLException {
        return this.selectDatabaseRows();
    }

    private List<Notice> selectDatabaseRows() throws SQLException {
        String sql = "select * from notice where migrated = 0";
        DataSource connection = this.connectionJdbc.getConnection();
        QueryRunner run = new QueryRunner(connection);
        ResultSetHandler<List<Notice>> noticeMapper = new BeanListHandler<>(Notice.class);
        return run.query(sql, noticeMapper);
    }


}
