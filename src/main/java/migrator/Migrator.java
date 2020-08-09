package migrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Env;
import core.ElasticConnection;
import core.IteratorWrapper;
import core.MalformedDocument;
import core.contracts.TableRefer;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Iterator;

@Dependent
public class Migrator implements Runnable {

    @Any
    private Instance<IteratorWrapper> iterators;
    private ElasticConnection elasticConnection;

    @Inject
    public Migrator(Instance<IteratorWrapper> iterators, Env env, ElasticConnection elasticConnection) {
        this.iterators = iterators;
        this.elasticConnection = elasticConnection;
    }

    @Override
    public void run() {
        for (IteratorWrapper wrapper : this.iterators) {
            Iterator<? extends TableRefer> iterator = wrapper.createIterator();
            ObjectMapper objectMapper = new ObjectMapper();
            while (iterator.hasNext()) {
                try {
                    TableRefer table = iterator.next();
                    if(table.getRefer().isEmpty() || wrapper.getDestination().isEmpty() || wrapper.getOrigin().isEmpty()){
                        throw new MalformedDocument("Malformed document "+ table.getClass());
                    }
                    String json = objectMapper.writeValueAsString(table);
                    this.elasticConnection.put(wrapper.getDestination(), table.getRefer(), json);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
