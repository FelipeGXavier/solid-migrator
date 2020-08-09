package core.contracts;

import java.sql.SQLException;
import java.util.Collection;

public interface DatabaseRows {

    Collection<? extends TableRefer> getDatabaseRows() throws SQLException;
}
