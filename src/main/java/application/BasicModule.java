package application;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import config.Env;
import core.ConnectionJdbc;
import core.IteratorWrapper;
import core.contracts.DatabaseRows;
import migrator.module.foo.connection.FooPostgresConnection;
import migrator.module.foo.postgres.dashboard.FooDashboardIterator;
import migrator.module.foo.postgres.dashboard.FooDashboardRows;
import migrator.module.foo.postgres.notice.FooNoticeIterator;
import migrator.module.foo.postgres.notice.FooNoticeRows;

import java.io.IOException;


public class BasicModule extends AbstractModule {

    private final String PATH;

    public BasicModule(String path) {
        this.PATH = path;
    }

    @Override
    protected void configure() {
        Multibinder<IteratorWrapper> multibinder = Multibinder.newSetBinder(binder(), IteratorWrapper.class);
        multibinder.addBinding().to(FooNoticeIterator.class);
        multibinder.addBinding().to(FooDashboardIterator.class);

        bind(DatabaseRows.class)
                .annotatedWith(Names.named("FooDashboardRows"))
                .to(FooDashboardRows.class);

        bind(DatabaseRows.class)
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
    }
}
