package com.fys.esclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class ESQueryBuilder {
    private SearchSourceBuilder _source;
    private AggregationBuilder _aggregation;

    ESQueryBuilder() {
        this._source = new SearchSourceBuilder();
    }

    ESQueryBuilder( SearchSourceBuilder source) {
        searchSource(source);
    }

    public void searchSource(SearchSourceBuilder source) {
        this._source = source;
    }

    public SearchSourceBuilder searchSource() {
        return this._source;
    }

    public void resultRange(int f, int s) {
        this._source.from(f);
        this._source.size(s);
    }


    public QueryBuilder query() {
        return this._source.query();
    }

    public void query(QueryBuilder builder) {
        this._source.query(builder);
    }

    public void aggregation(AggregationBuilder aggregation) {
        this._aggregation = aggregation;
        this._source.aggregation(aggregation);
    }

    public AggregationBuilder aggregation() {
        return this._aggregation;
    }

    public void printQuery() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(this._source.toString());
        System.out.println(gson.toJson(je));
    }

    public void printQuery(String destination) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(this._source.toString());
        try {
            FileWriter writer = new FileWriter(destination);
            writer.write(gson.toJson(je));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
