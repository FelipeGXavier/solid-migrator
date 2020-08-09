package config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class Env {

    private Configuration configuration;
    private static final String PATH = "src/main/resources/config.yaml";

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
