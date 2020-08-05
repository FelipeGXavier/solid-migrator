package migrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Env;
import core.ElasticConnection;
import core.IteratorWrapper;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Iterator;

@Dependent
public class Migrator implements Runnable {

    @Any
    private Instance<IteratorWrapper> iterators;
    private Env config;
    private ElasticConnection elasticConnection;

    @Inject
    public Migrator(Instance<IteratorWrapper> iterators, Env env, ElasticConnection elasticConnection) {
        this.iterators = iterators;
        this.config = env;
        this.elasticConnection = elasticConnection;
    }

    @Override
    public void run() {
        for (IteratorWrapper wrapper : this.iterators) {
            Iterator<?> iterator = wrapper.createIterator();
            ObjectMapper objectMapper = new ObjectMapper();
            while (iterator.hasNext()) {
                try {
                    String json = objectMapper.writeValueAsString(iterator.next());
                    this.elasticConnection.put(wrapper.getDestination(), "1", json);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
