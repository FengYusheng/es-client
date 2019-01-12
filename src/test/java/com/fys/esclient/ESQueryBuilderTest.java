package com.fys.esclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

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
}
