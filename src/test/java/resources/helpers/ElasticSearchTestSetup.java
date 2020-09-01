package resources.helpers;

import core.ElasticConnectionImpl;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchTestSetup {

    protected static ElasticConnectionImpl elasticConnection;
    private static final Map<String, String> INDICES = new HashMap<>();


    public ElasticSearchTestSetup() throws IOException {
        elasticConnection = new ElasticConnectionImpl();
        INDICES.put("notice", getMappingForNotice());
        cleanIndexesBeforeStart();
    }

    public static void cleanIndexesBeforeStart() throws IOException {
        for (Map.Entry<String, String> entry : INDICES.entrySet()) {
            if(verifyIfIndexExists(entry.getKey())){
                DeleteIndexRequest request = new DeleteIndexRequest(entry.getKey());
                elasticConnection.getClient().indices().delete(request, RequestOptions.DEFAULT);
            }
            CreateIndexRequest request = new CreateIndexRequest(entry.getKey());
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 2)
            );
            request.mapping(entry.getValue(), XContentType.JSON);
            elasticConnection.getClient().indices().create(request, RequestOptions.DEFAULT);
        }

    }

    private static boolean verifyIfIndexExists(String index) throws IOException {
        return elasticConnection.getClient().indices().exists(new GetIndexRequest(index), RequestOptions.DEFAULT);
    }

    private static String getMappingForNotice(){
        return "{\n" +
                "    \"properties\": {\n" +
                "      \"id\": {\"type\": \"long\"},\n" +
                "      \"code\": {\"type\": \"text\"},\n" +
                "      \"object\": {\"type\": \"text\"},\n" +
                "      \"opening_date\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\"},\n" +
                "      \"final_date\": {\"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss\"}\n" +
                "    }\n" +
                "  }";
    }
}
