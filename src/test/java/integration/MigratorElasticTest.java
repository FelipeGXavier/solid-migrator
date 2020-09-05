package integration;

import config.Env;
import core.ConnectionJdbc;
import core.contracts.IDatabaseHandler;
import migrator.Migrator;
import migrator.systems.foo.rows.FooNoticeRows;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        Set<IDatabaseHandler> databaseHandlers = new LinkedHashSet<>();
        databaseHandlers.add(fooNoticeRows);
        Migrator migrator = new Migrator(databaseHandlers, elasticConnection);
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
