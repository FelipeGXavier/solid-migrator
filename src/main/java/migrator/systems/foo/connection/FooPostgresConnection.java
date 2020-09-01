package migrator.systems.foo.connection;

import config.ConnectionType;
import config.Env;
import core.ConnectionJdbc;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named("FooPostgres")
public class FooPostgresConnection extends ConnectionJdbc {

    @Inject
    public FooPostgresConnection(Env configuration) throws IOException {
        super(configuration);
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.FOO;
    }
}
