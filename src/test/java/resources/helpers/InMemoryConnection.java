package resources.helpers;

import config.ConnectionType;
import config.Env;
import core.ConnectionJdbc;

public class InMemoryConnection extends ConnectionJdbc {

    public InMemoryConnection(Env configuration) {
        super(configuration);
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.H2;
    }
}
