package migrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ElasticConnection;
import core.IteratorWrapper;
import core.MalformedDocumentException;
import core.contracts.TableRefer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Set;

public class Migrator implements Runnable {

    private Set<IteratorWrapper> iterators;
    private ElasticConnection elasticConnection;

    @Inject
    public Migrator(Set<IteratorWrapper> iterators, ElasticConnection elasticConnection) {
        this.iterators = iterators;
        this.elasticConnection = elasticConnection;
    }

    @Override
    public void run() {
        for (IteratorWrapper wrapper : this.iterators) {
            Iterator<? extends TableRefer> iterator = wrapper.createIterator();
            ObjectMapper objectMapper = new ObjectMapper();
            while (iterator.hasNext()) {
                TableRefer table = iterator.next();
                String origin = wrapper.getOrigin();
                String destination = wrapper.getDestination();
                if (table.getRefer().isEmpty() || (origin == null || origin.isEmpty()) || (destination == null || destination.isEmpty())) {
                    throw new MalformedDocumentException("Malformed document {} " + table.getClass());
                }
                try {
                    String json = objectMapper.writeValueAsString(table);
                    this.elasticConnection.put(wrapper.getDestination(), table.getRefer(), json);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
