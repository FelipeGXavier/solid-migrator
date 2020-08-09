package core;

import core.contracts.DatabaseRows;
import core.contracts.TableRefer;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

abstract public class IteratorWrapper {

    protected Collection<? extends TableRefer> rows;

    public IteratorWrapper(DatabaseRows rows) throws SQLException {
        this.rows = rows.getDatabaseRows();
    }

    public abstract String getOrigin();
    public abstract String getDestination();

    public Iterator<? extends TableRefer> createIterator() {
        return this.rows.iterator();
    }
}
