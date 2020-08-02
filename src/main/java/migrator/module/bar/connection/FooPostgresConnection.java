package migrator.module.bar.connection;

import config.Connection;
import config.ConnectionType;
import config.Env;
import core.ConnectionJdbc;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Dependent
@Named("fooPostgres")
public class FooPostgresConnection extends ConnectionJdbc {


    @Inject
    public FooPostgresConnection(Env configuration) throws IOException {
        super(configuration);
    }

    @Override
    public ConnectionType getType() {
        return ConnectionType.FOOPOSTGRES;
    }
}
