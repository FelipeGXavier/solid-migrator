package core.contracts;

public interface Migrator {

    void migrate();

    DatabaseIterator getIterator();
}
