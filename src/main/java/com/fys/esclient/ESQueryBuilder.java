package com.fys.esclient;


import static org.elasticsearch.index.query.QueryBuilders.*;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ESQueryBuilder {

    public static SearchSourceBuilder matchAll() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchAllQuery());
        sourceBuilder.from(0);
        sourceBuilder.size(0);
        return sourceBuilder;
    }

    public static SearchSourceBuilder myRangeQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(0);
        sourceBuilder.query(
                rangeQuery("year")
                .from("2000")
                .to("2018")
                .includeLower(true)
                .includeUpper(true)
        );
        return sourceBuilder;
    }

    public static SearchSourceBuilder generateQuery() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(0);
        sourceBuilder.query(
                constantScoreQuery(
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
                )
        );

        sourceBuilder.aggregation(
                AggregationBuilders.terms("CATEGORY_TERMS")
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
                )
        );

        return sourceBuilder;
    }

    public static void main(String[] args) {
        System.out.println("ES Query DSL Builder");
    }
}
