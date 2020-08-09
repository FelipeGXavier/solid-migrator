package migrator.module.foo.postgres.notice;

import core.IteratorWrapper;
import core.contracts.DatabaseRows;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;

public class FooNoticeIterator extends IteratorWrapper {

    @Inject
    public FooNoticeIterator(@Named("fooNoticeRows") DatabaseRows rows) throws SQLException {
        super(rows);
    }

    public String getOrigin() {
        return "tbedital";
    }

    public String getDestination() {
        return "enotice";
    }
}
