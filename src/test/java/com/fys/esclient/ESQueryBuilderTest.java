package com.fys.esclient;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;


public class ESQueryBuilderTest {
    private static RestHighLevelClient client;
    private static final String index = "bank";
    private SearchRequest searchRequest;

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
    public void testCase1() {
        System.out.println("Test Case 1");
    }

    @Test
    public void testCase2() {
        System.out.println("Test Case 2");
    }

    @Test
    public void testMatchAll() {
        searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(ESQueryBuilder.matchAll());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
