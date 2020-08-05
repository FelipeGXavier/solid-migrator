package config;

import javax.enterprise.context.Dependent;
import java.util.Map;

@Dependent
public class Configuration {

    private Map<ConnectionType, Connection> connections;
    private String cron;

    public Map<ConnectionType, Connection> getConnections() {
        return connections;
    }

    public void setConnections(Map<ConnectionType, Connection> connections) {
        this.connections = connections;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
