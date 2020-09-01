package core;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ElasticConnectionImpl {

    private RestHighLevelClient client;

    public ElasticConnectionImpl() {
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public ElasticConnectionImpl(String host) {
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, 9200, "http")));
    }

    public GetResponse get(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        return client.get(getRequest, RequestOptions.DEFAULT);
    }

    public void put(String index, String id, String data) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index)
                .id(id).source(data, XContentType.JSON);
        this.client.index(indexRequest, RequestOptions.DEFAULT);
    }

    public RestHighLevelClient getClient() {
        return client;
    }
}
