package com.fys.esclient;


import static org.elasticsearch.index.query.QueryBuilders.*;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ESQueryBuilder {
    private SearchSourceBuilder _source;

    ESQueryBuilder() {
        this._source = new SearchSourceBuilder();
    }

    ESQueryBuilder( SearchSourceBuilder source) {
        setSearchSource(source);
    }

    public void setSearchSource(SearchSourceBuilder source) {
        this._source = source;
    }

    public SearchSourceBuilder searchSource() {
        return this._source;
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
