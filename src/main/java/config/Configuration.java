package config;

import javax.enterprise.context.Dependent;
import java.util.HashMap;

@Dependent
public class Configuration {

    private HashMap<ConnectionType, Connection> connections;
    private String cron;

    public HashMap<ConnectionType, Connection> getConnections() {
        return connections;
    }

    public void setConnections(HashMap<ConnectionType, Connection> connections) {
        this.connections = connections;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
