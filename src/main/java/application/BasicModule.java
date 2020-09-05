package application;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import config.Env;
import core.ConnectionJdbc;
import core.contracts.IDatabaseHandler;
import migrator.systems.foo.connection.FooPostgresConnection;
import migrator.systems.foo.rows.FooDashboardRows;
import migrator.systems.foo.rows.FooNoticeRows;

import java.io.IOException;


public class BasicModule extends AbstractModule {

    private final String PATH;

    public BasicModule(String path) {
        this.PATH = path;
    }

    @Override
    protected void configure() {

        bind(IDatabaseHandler.class)
                .annotatedWith(Names.named("FooDashboardRows"))
                .to(FooDashboardRows.class);

        bind(IDatabaseHandler.class)
                .annotatedWith(Names.named("FooNoticeRows"))
                .to(FooNoticeRows.class);

        bind(ConnectionJdbc.class)
                .annotatedWith(Names.named("FooPostgres"))
                .to(FooPostgresConnection.class);

        bind(Env.class).toProvider(() -> {
            try {
                return new Env(PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });

        Multibinder<IDatabaseHandler> multibinder = Multibinder.newSetBinder(binder(), IDatabaseHandler.class);
        multibinder.addBinding().to(FooNoticeRows.class);
        multibinder.addBinding().to(FooDashboardRows.class);


    }
}
