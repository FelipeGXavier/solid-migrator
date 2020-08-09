package setup;

import config.Env;
import org.junit.jupiter.api.Test;
import resources.helpers.InMemoryConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionJdbcTest {

    private static final String PATH = "src/test/java/resources/helpers/fake.yml";
    private Connection connection;

    public ConnectionJdbcTest() throws IOException, SQLException {
        Env env = new Env(PATH);
        InMemoryConnection memoryConnection = new InMemoryConnection(env);
        this.connection = memoryConnection.getConnection().getConnection();
    }


    @Test
    public void givenValidEnvironmentShouldReturnValidDataSource() throws SQLException {
        assertFalse(this.connection.isClosed());
    }


}
