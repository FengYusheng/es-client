package com.fys.esclient;

import org.apache.http.HttpHost;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class ESQueryBuilder {

    public static MatchAllQueryBuilder matchAll() {
        return matchAllQuery();
    }

    public static void main(String[] args) {
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http")
//                )
//        );
//
//        SearchRequest searchRequest = new SearchRequest("bank");
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(matchAllQuery());
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(10);
//        searchRequest.source(searchSourceBuilder);
//
//        try {
//            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//            System.out.println(response.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            client.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
