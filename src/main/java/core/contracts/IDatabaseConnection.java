package core.contracts;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface IDatabaseConnection {


    DataSource getConnection() throws SQLException;

}
