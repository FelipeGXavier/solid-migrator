package core;

import core.contracts.DatabaseRows;
import core.contracts.TableRefer;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

abstract public class IteratorWrapper {

    protected Collection<? extends TableRefer> rows;
    protected DatabaseRows databaseRows;

    public IteratorWrapper(DatabaseRows rows) throws SQLException {
        this.rows = rows.getDatabaseRows();
        this.databaseRows = rows;
    }

    public abstract String getOrigin();
    public abstract String getDestination();

    public void updateRow(TableRefer notice) throws SQLException {
        this.databaseRows.updateRow(notice);
    }

    public Iterator<? extends TableRefer> createIterator() {
        return this.rows.iterator();
    }
}
