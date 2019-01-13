package com.fys.esclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.aggregations.*;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;


public class ESQueryBuilderTest {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Start to test whether it can generate the expected query.");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Test ends.");
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


    @Test
    public void test_create_a_search_source_builder() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        ESQueryBuilder queryBuilder = new ESQueryBuilder(sourceBuilder);
        assertSame(sourceBuilder, queryBuilder.searchSource());
    }

    @Test
    public void test_set_and_get_a_search_source_builder() {
        ESQueryBuilder queryBuilder = new ESQueryBuilder();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        queryBuilder.searchSource(sourceBuilder);
        assertSame(sourceBuilder, queryBuilder.searchSource());
    }

    @Test
    public void test_set_search_result_range() {
        ESQueryBuilder queryBuilder = new ESQueryBuilder();
        SearchSourceBuilder sourceBuilder = queryBuilder.searchSource();

        int[] expected = {-1, -1};
        int[] actual = {sourceBuilder.from(), sourceBuilder.size()};
        assertArrayEquals(expected, actual);

        queryBuilder.resultRange(0, 5);
        expected[0] = 0;
        expected[1] = 5;
        actual[0] = sourceBuilder.from();
        actual[1] = sourceBuilder.size();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test_set_and_get_query() {
        ESQueryBuilder queryBuilder = new ESQueryBuilder();
        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
        queryBuilder.query(query);
        assertSame(query, queryBuilder.query());
    }

    @Test
    public void test_set_and_get_aggregation() {
        ESQueryBuilder queryBuilder = new ESQueryBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("TEST");
        queryBuilder.aggregation(aggregationBuilder);
        assertSame(aggregationBuilder, queryBuilder.aggregation());
    }
}
