package config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.enterprise.context.Dependent;
import java.io.File;
import java.io.IOException;

@Dependent
public class Env {

    private Configuration configuration;
    private final String path = "src/main/resources/config.yaml";

    public Env() throws IOException {
        this.configuration = this.getEnvFromFile();
    }

    public Configuration getEnvFromFile() throws IOException {
        File file = new File(this.path);
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Configuration.class);
    }
}
