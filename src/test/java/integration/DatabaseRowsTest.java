package integration;

import config.Env;
import core.ConnectionJdbc;
import core.ElasticConnectionImpl;
import core.IteratorWrapper;
import core.contracts.DatabaseRows;
import migrator.Migrator;
import migrator.systems.foo.postgres.notice.FooNoticeIterator;
import migrator.systems.foo.postgres.notice.FooNoticeRows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import resources.helpers.InMemoryConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseRowsTest {

    private static final String PATH = "src/test/java/resources/fake.yml";
    private ConnectionJdbc inMemoryDatabase;

    public DatabaseRowsTest() throws IOException {
        Env env = new Env(PATH);
        this.inMemoryDatabase = new InMemoryConnection(env);
    }

    @Test
    public void databaseWithoutRowsShouldReturnEmptyList() throws SQLException {
        FooNoticeRows noticeRows = new FooNoticeRows(this.inMemoryDatabase);
        assertTrue(noticeRows.getDatabaseRows().isEmpty());
    }

    @Test
    public void databaseWithRowsShouldNotReturnEmptyList() throws SQLException {
        Connection connection = this.inMemoryDatabase.getConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into notice values " +
                "(null, '2020/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste', 0), " +
                "(null, '2120/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste 2', 0)," +
                "(null, '2220/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste 3', 1)");
        preparedStatement.execute();
        DatabaseRows noticeRows = new FooNoticeRows(this.inMemoryDatabase);
        assertEquals(noticeRows.getDatabaseRows().size(), 2);
    }

    @Test
    public void afterInsertElasticSearchMustUpdateTableInDatabase() throws SQLException {
        Connection connection = this.inMemoryDatabase.getConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into notice values (null, '2020/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste', 0)");
        preparedStatement.executeUpdate();
        DatabaseRows noticeRows = new FooNoticeRows(this.inMemoryDatabase);
        FooNoticeIterator fooNoticeIterator = new FooNoticeIterator(noticeRows);
        Set<IteratorWrapper> iterators = new LinkedHashSet<>();
        iterators.add(fooNoticeIterator);
        ElasticConnectionImpl elasticConnection = Mockito.mock(ElasticConnectionImpl.class);
        Migrator migrator = new Migrator(iterators, elasticConnection);
        migrator.run();
        preparedStatement = connection.prepareStatement("select * from notice where migrated = 0");
        ResultSet rs = preparedStatement.executeQuery();
        assertFalse(rs.next());
    }

    @BeforeEach
    public void clean() throws SQLException {
        Connection connection = this.inMemoryDatabase.getConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from notice");
        preparedStatement.execute();
    }


}
