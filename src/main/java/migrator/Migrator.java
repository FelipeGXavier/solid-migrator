package migrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ElasticConnectionImpl;
import core.MalformedDocumentException;
import core.SchedulerMigrator;
import core.contracts.IDatabaseHandler;
import core.contracts.ITableReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.*;

public class Migrator implements Runnable {

    private ElasticConnectionImpl elasticConnection;
    private Set<IDatabaseHandler> databaseHandlers;
    private static final Logger logger = LoggerFactory.getLogger(SchedulerMigrator.class);

    @Inject
    public Migrator(Set<IDatabaseHandler> databaseHandlers, ElasticConnectionImpl elasticConnection) {
        this.elasticConnection = elasticConnection;
        this.databaseHandlers = databaseHandlers;
    }

    @Override
    public void run() {
        ObjectMapper objectMapper = new ObjectMapper();
        for (IDatabaseHandler instance : this.databaseHandlers) {
            String origin = instance.getOriginTable();
            String destination = instance.getDestinationTable();
            try {
                for (ITableReference table : instance.getDatabaseRows()) {
                    if (table.getRefer().isEmpty() || (origin == null || origin.isEmpty()) || (destination == null || destination.isEmpty())) {
                        logger.error("malformed document for class " + table.getClass());
                        throw new MalformedDocumentException("Malformed document {} " + table.getClass());
                    }
                    try {
                        String json = objectMapper.writeValueAsString(table);
                        this.elasticConnection.put(destination, table.getRefer(), json);
                        instance.updateRow(table);
                        logger.info("updated row with refer " + table.getRefer() + " for " + table.getClass());
                    } catch (Exception e) {
                        logger.error("indexing error {}", Arrays.toString(e.getStackTrace()));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
