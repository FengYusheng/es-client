package com.fys.esclient;

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
    public void test_range_query() {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = ESQueryBuilder.myRangeQuery();
        searchRequest.source(sourceBuilder);
        System.out.println(sourceBuilder.toString());
    }

    @Test
    public void test_constant_score_query() {
        SearchSourceBuilder sourceBuilder = ESQueryBuilder.myConstantScoreQuery();
        System.out.println(sourceBuilder.toString());
    }
}
