package integration;

import config.Env;
import core.ConnectionJdbc;
import core.IteratorWrapper;
import core.contracts.DatabaseRows;
import core.contracts.TableRefer;
import migrator.Migrator;
import migrator.systems.foo.postgres.notice.FooNoticeIterator;
import migrator.systems.foo.postgres.notice.FooNoticeRows;
import migrator.systems.foo.tables.Notice;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import resources.helpers.ElasticSearchTestSetup;
import resources.helpers.InMemoryConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MigratorElasticTest extends ElasticSearchTestSetup {

    private static final String PATH = "src/test/java/resources/fake.yml";
    private ConnectionJdbc inMemoryDatabase;

    public MigratorElasticTest() throws IOException {
        Env env = new Env(PATH);
        this.inMemoryDatabase = new InMemoryConnection(env);
    }

    @Test
    public void noticeIndexExists() throws IOException {
        Assertions.assertTrue(elasticConnection.getClient().indices().exists(new GetIndexRequest("notice"), RequestOptions.DEFAULT));
    }

    @Test
    public void migrateRowToElastic() throws SQLException, IOException {
        Connection connection = this.inMemoryDatabase.getConnection().getConnection();
        String generatedColumns[] = {"id"};
        PreparedStatement preparedStatement = connection.prepareStatement("insert into notice (code, openingDate, finalDate, object, migrated) values " +
                "('2020/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste', 0)", generatedColumns);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("select * from notice where migrated = 0");
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        String id = rs.getString("id");
        FooNoticeRows fooNoticeRows = new FooNoticeRows(this.inMemoryDatabase);
        FooNoticeIterator fooNoticeIterator = new FooNoticeIterator(fooNoticeRows);
        Set<IteratorWrapper> iterators = new LinkedHashSet<>();
        iterators.add(fooNoticeIterator);
        Migrator migrator = new Migrator(iterators, elasticConnection);
        migrator.run();
        Assertions.assertTrue(elasticConnection.getClient().get(new GetRequest("notice", id), RequestOptions.DEFAULT).isExists());
    }

    @BeforeEach
    public void clean() throws SQLException {
        Connection connection = this.inMemoryDatabase.getConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from notice");
        preparedStatement.execute();
    }

}
