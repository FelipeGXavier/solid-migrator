package integration;

import config.Env;
import core.ConnectionJdbc;
import core.contracts.DatabaseRows;
import migrator.module.foo.postgres.notice.FooNoticeRows;
import org.junit.jupiter.api.Test;
import resources.helpers.InMemoryConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseRowsTest {

    private static final String PATH = "src/test/java/resources/helpers/fake.yml";
    private ConnectionJdbc connection;

    public DatabaseRowsTest() throws IOException{
        Env env = new Env(PATH);
        this.connection = new InMemoryConnection(env);
    }

    @Test
    public void databaseWithoutRowsToMigrateShouldReturnEmptyList() throws SQLException {
        FooNoticeRows noticeRows = new FooNoticeRows(this.connection);
        assertTrue(noticeRows.getDatabaseRows().isEmpty());
    }

    @Test
    public void givenDatabaseWithRowsShouldNotReturnEmptyList() throws SQLException {
        Connection connection = this.connection.getConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into notice values " +
                "(null, '2020/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste', 0), " +
                "(null, '2120/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste 2', 0)," +
                "(null, '2220/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste 3', 1)");
        preparedStatement.execute();
        DatabaseRows noticeRows = new FooNoticeRows(this.connection);
        assertEquals(noticeRows.getDatabaseRows().size(), 2);
    }



}
