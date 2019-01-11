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

    public static void main(String[] args) {
        System.out.println("ES Query DSL Builder");
    }
}
