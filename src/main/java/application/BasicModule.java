package application;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import core.ConnectionJdbc;
import core.IteratorWrapper;
import core.contracts.DatabaseRows;
import migrator.module.foo.connection.FooPostgresConnection;
import migrator.module.foo.postgres.dashboard.FooDashboardIterator;
import migrator.module.foo.postgres.dashboard.FooDashboardRows;
import migrator.module.foo.postgres.notice.FooNoticeIterator;
import migrator.module.foo.postgres.notice.FooNoticeRows;


public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<IteratorWrapper> multibinder = Multibinder.newSetBinder(binder(), IteratorWrapper.class);
        multibinder.addBinding().to(FooNoticeIterator.class);
        multibinder.addBinding().to(FooDashboardIterator.class);

        bind(DatabaseRows.class)
                .annotatedWith(Names.named("fooDashboardRows"))
                .to(FooDashboardRows.class);

        bind(DatabaseRows.class)
                .annotatedWith(Names.named("fooNoticeRows"))
                .to(FooNoticeRows.class);

        bind(ConnectionJdbc.class)
                .annotatedWith(Names.named("fooPostgres"))
                .to(FooPostgresConnection.class);
    }
}
