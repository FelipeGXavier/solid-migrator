package core;

import config.Configuration;
import config.Connection;
import config.ConnectionType;
import config.Env;
import core.contracts.IDatabaseConnection;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;

abstract public class ConnectionJdbc implements IDatabaseConnection {

    protected Configuration configuration;
    protected Connection connection;

    @Inject
    public ConnectionJdbc(Env configuration) {
        this.configuration = configuration.getConfiguration();
        this.connection = this.getConnectionFromEnv();
    }

    public DataSource getConnection() {
        final String url = "jdbc:%s://%s/%s";
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.connection.getDriver());
        dataSource.setUsername(this.connection.getUser());
        dataSource.setPassword(this.connection.getPassword());
        dataSource.setUrl(String.format(url, this.getType().getDatabase(), this.connection.getHost(), this.connection.getDatabase()));
        dataSource.setMaxIdle(10);
        dataSource.setInitialSize(5);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    private Connection getConnectionFromEnv() {
        return this.configuration.getConnections().get(this.getType());
    }

    abstract public ConnectionType getType();

}
