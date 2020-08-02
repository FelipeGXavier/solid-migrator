import config.Env;
import migrator.Migrator;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        SeContainerInitializer containerInit = SeContainerInitializer.newInstance();
        SeContainer container = containerInit.initialize();
        container.select(Migrator.class).get().run();
    }

}
