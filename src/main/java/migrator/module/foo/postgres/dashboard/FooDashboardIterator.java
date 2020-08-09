package migrator.module.foo.postgres.dashboard;

import core.IteratorWrapper;
import core.contracts.DatabaseRows;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;

public class FooDashboardIterator extends IteratorWrapper {

    @Inject
    public FooDashboardIterator(@Named("fooDashboardRows") DatabaseRows rows) throws SQLException {
        super(rows);
    }

    public String getOrigin() {
        return "tbdashboard";
    }

    public String getDestination() {
        return "dashboard";
    }
}
