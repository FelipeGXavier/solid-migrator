package migrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ElasticConnectionImpl;
import core.IteratorWrapper;
import core.MalformedDocumentException;
import core.SchedulerMigrator;
import core.contracts.TableRefer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Migrator implements Runnable {

    private Set<IteratorWrapper> iterators;
    private ElasticConnectionImpl elasticConnection;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerMigrator.class);

    @Inject
    public Migrator(Set<IteratorWrapper> iterators, ElasticConnectionImpl elasticConnection) {
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
                    logger.error("malformed document for class " + table.getClass());
                    throw new MalformedDocumentException("Malformed document {} " + table.getClass());
                }
                try {
                    String json = objectMapper.writeValueAsString(table);
                    this.elasticConnection.put(wrapper.getDestination(), table.getRefer(), json);
                    wrapper.updateRow(table);
                    logger.info("updated row with refer " + table.getRefer() + " for " + table.getClass());
                } catch (Exception e) {
                    logger.error("indexing error {}", Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

}
