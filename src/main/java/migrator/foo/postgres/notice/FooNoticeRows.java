package migrator.module.foo.postgres.notice;

import core.ConnectionJdbc;
import core.contracts.DatabaseRows;
import core.contracts.TableRefer;
import migrator.module.foo.tables.Notice;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Named("FooNoticeRows")
public class FooNoticeRows implements DatabaseRows {

    private ConnectionJdbc connectionJdbc;

    @Inject
    public FooNoticeRows(@Named("FooPostgres") ConnectionJdbc connectionJdbc){
        this.connectionJdbc = connectionJdbc;
    }


    public Collection<? extends TableRefer> getDatabaseRows() throws SQLException {
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
