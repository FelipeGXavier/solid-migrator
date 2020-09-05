package core.contracts;

import java.sql.SQLException;
import java.util.Collection;

public interface IDatabaseHandler {

    Collection<? extends ITableReference> getDatabaseRows() throws SQLException;
    void updateRow(ITableReference tableRefer) throws SQLException;
    String getOriginTable();
    String getDestinationTable();

}
