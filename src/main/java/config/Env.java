package config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;

@Singleton
public class Env {

    private Configuration configuration;
    private static final String PATH = "src/main/resources/dev.yaml";

    public Env() throws IOException {
        this.configuration = this.getEnvFromFile(PATH);
    }

    public Env(String path) throws IOException {
        this.configuration = this.getEnvFromFile(path);
    }

    private Configuration getEnvFromFile(String path) throws IOException {
        File file = new File(path);
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Configuration.class);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
