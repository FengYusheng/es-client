package com.fys.esclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.*;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.FileWriter;
import java.io.IOException;


public class ESQueryBuilderTest {
    private static RestHighLevelClient client;
    private static final String index = "bank";

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before class.");
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After class.");
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_match_all() {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = ESQueryBuilder.matchAll();
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            assertHitCount(response, 1000);
            assertNoFailures(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_generate_expected_query() {
        SearchSourceBuilder sourceBuilder = ESQueryBuilder.generateQuery();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(sourceBuilder.toString());
        try {
            FileWriter writer = new FileWriter("query.json");
            writer.write(gson.toJson(je));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
