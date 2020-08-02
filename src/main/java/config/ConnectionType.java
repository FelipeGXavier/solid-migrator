package config;

public enum ConnectionType {

    FOOPOSTGRES("postgresql"), FOOMYSQL("mysql"), BARPOSTGRES("postgresql");

    private String database;

    ConnectionType(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }
}
