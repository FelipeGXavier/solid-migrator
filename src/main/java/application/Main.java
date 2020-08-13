package application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import core.SchedulerMigrator;
import org.quartz.SchedulerException;

public class Main {


    public static void main(String[] args) throws SchedulerException {
        Injector injector = Guice.createInjector(new BasicModule(args[0]));
        injector.getInstance(SchedulerMigrator.class).init();
    }

}
