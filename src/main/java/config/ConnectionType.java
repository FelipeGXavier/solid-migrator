package config;

public enum ConnectionType {

    FOO("postgresql"),
    BAR("postgresql"),
    H2("h2");

    private String database;

    ConnectionType(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}
