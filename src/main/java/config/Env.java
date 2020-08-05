package config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.enterprise.context.Dependent;
import java.io.File;
import java.io.IOException;

@Dependent
public class Env {

    private Configuration configuration;
    private static final String PATH = "src/main/resources/config.yaml";

    public Env() throws IOException {
        this.configuration = this.getEnvFromFile();
    }

    private Configuration getEnvFromFile() throws IOException {
        File file = new File(PATH);
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Configuration.class);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
