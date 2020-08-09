package resources.helpers;

import config.ConnectionType;
import config.Env;
import core.ConnectionJdbc;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class InMemoryConnection extends ConnectionJdbc {

    public InMemoryConnection(Env configuration) {
        super(configuration);
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.H2;
    }

    @Override
    public DataSource getConnection() {
        final String url = "jdbc:%s:mem:%s;INIT=RUNSCRIPT FROM 'src/test/java/resources/init.sql';DB_CLOSE_DELAY=-1";
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.connection.getDriver());
        dataSource.setUsername(this.connection.getUser());
        dataSource.setPassword(this.connection.getPassword());
        dataSource.setUrl(String.format(url, this.getType().getDatabase(),this.connection.getDatabase()));
        dataSource.setMaxIdle(10);
        dataSource.setInitialSize(5);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }
}
