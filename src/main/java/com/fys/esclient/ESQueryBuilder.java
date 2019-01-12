package com.fys.esclient;


import static org.elasticsearch.index.query.QueryBuilders.*;
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

    public static SearchSourceBuilder myConstantScoreQuery() {
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
                                matchQuery("email", "@pyrami.com")
                        )
                )
        );

        return sourceBuilder;
    }

    public static void main(String[] args) {
        System.out.println("ES Query DSL Builder");
    }
}
