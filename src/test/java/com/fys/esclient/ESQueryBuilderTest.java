package com.fys.esclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.aggregations.*;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

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

    @Test
    public void test_produce_expected_query() {
        ConstantScoreQueryBuilder query_ = new ConstantScoreQueryBuilder(
                boolQuery()
                        .must(
                                rangeQuery("year")
                                        .from(2000)
                                        .to(2018)
                                        .includeUpper(true)
                                        .includeLower(true)
                        )
                        .must(
                                nestedQuery(
                                        "people",
                                        termsQuery(
                                                "people.full_name",
                                                "David","Cane"
                                        ),
                                        ScoreMode.None
                                )
                        )
        );

        TermsAggregationBuilder agg_ = AggregationBuilders.terms("CATEGORY_TERMS")
                .field("categories")
                .size(25)
                .shardSize(500)
                .order(BucketOrder.aggregation("TIMES_CITED", false))
                .collectMode(Aggregator.SubAggCollectionMode.BREADTH_FIRST)
                .subAggregation(
                        AggregationBuilders.avg("IMPACT")
                                .field("timescited")
                )
                .subAggregation(
                        AggregationBuilders.sum("TIMES_CITED")
                                .field("timescited")
                );


        ESQueryBuilder queryBuilder = new ESQueryBuilder();
        queryBuilder.query(query_);
        queryBuilder.aggregation(agg_);
        assertSame(queryBuilder.query(), query_);
        assertSame(queryBuilder.aggregation(), agg_);

        queryBuilder.printQuery();
        queryBuilder.printQuery("./query.json");
    }
}
