package migrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Env;
import core.IteratorWrapper;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Iterator;

@Dependent
public class Migrator implements Runnable{

    @Any
    private Instance<IteratorWrapper> iterators;
    private Env config;

    @Inject
    public Migrator(Instance<IteratorWrapper> iterators, Env env) {
        this.iterators = iterators;
        this.config = env;
    }

    @Override
    public void run() {
        for (IteratorWrapper wrapper : this.iterators){
            Iterator<?> iterator = wrapper.createIterator();
            ObjectMapper objectMapper = new ObjectMapper();
            while (iterator.hasNext()){
                String json = null;
                try {
                    json = objectMapper.writeValueAsString(iterator.next());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println(json);
            }
        }
    }

}
