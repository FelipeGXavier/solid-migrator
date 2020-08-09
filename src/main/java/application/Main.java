package application;


import com.google.inject.Guice;
import com.google.inject.Injector;
import migrator.Migrator;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        injector.getInstance(Migrator.class).run();
    }

}
