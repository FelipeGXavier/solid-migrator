package core.contracts;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface DatabaseConnection {


    DataSource getConnection() throws SQLException;

}
