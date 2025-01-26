package pl.put.poznan.transformer.logic.decorators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.JsonImpl;

import static org.junit.jupiter.api.Assertions.*;
class minifyDecoratorTest {
    private minifyDecorator decoratorMini;

    @Test
    public void testA(){
        String data = "{  \n" +
                "    \"employee\": {  \n" +
                "        \"name\":       \"sonoo\",   \n" +
                "        \"salary\":      56000,   \n" +
                "        \"married\":    true  \n" +
                "    }  \n" +
                "}  ";
        Json json = new JsonImpl(data);
        decoratorMini = new minifyDecorator(json);
        String expectedJson = "{\"employee\":{\"name\":\"sonoo\",\"salary\":56000,\"married\":true}}";
        assertEquals(expectedJson,decoratorMini.getData());
    }
    @Test
    public void testB(){
        String data = "{\n" +
                "    \"fruit\": \"Apple\",\n" +
                "    \"size\": \"Large\",\n" +
                "    \"color\": \"Red\"\n" +
                "}";
        Json json = new JsonImpl(data);
        decoratorMini = new minifyDecorator(json);
        String expectedJson = "{\"fruit\":\"Apple\",\"size\":\"Large\",\"color\":\"Red\"}";
        assertEquals(expectedJson,decoratorMini.getData());
    }
    @Test
    public void testC(){
        String data = "{\n" +
                "    \"quiz\": {\n" +
                "        \"sport\": {\n" +
                "            \"q1\": {\n" +
                "                \"question\": \"Which one is correct team name in NBA?\",\n" +
                "                \"options\": [\n" +
                "                    \"New York Bulls\",\n" +
                "                    \"Los Angeles Kings\",\n" +
                "                    \"Golden State Warriros\",\n" +
                "                    \"Huston Rocket\"\n" +
                "                ],\n" +
                "                \"answer\": \"Huston Rocket\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"maths\": {\n" +
                "            \"q1\": {\n" +
                "                \"question\": \"5 + 7 = ?\",\n" +
                "                \"options\": [\n" +
                "                    \"10\",\n" +
                "                    \"11\",\n" +
                "                    \"12\",\n" +
                "                    \"13\"\n" +
                "                ],\n" +
                "                \"answer\": \"12\"\n" +
                "            },\n" +
                "            \"q2\": {\n" +
                "                \"question\": \"12 - 8 = ?\",\n" +
                "                \"options\": [\n" +
                "                    \"1\",\n" +
                "                    \"2\",\n" +
                "                    \"3\",\n" +
                "                    \"4\"\n" +
                "                ],\n" +
                "                \"answer\": \"4\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Json json = new JsonImpl(data);
        decoratorMini = new minifyDecorator(json);
        String expectedJson = "{\"quiz\":{\"sport\":{\"q1\":{\"question\":\"Which one is correct team name in NBA?\",\"options\":[\"New York Bulls\",\"Los Angeles Kings\",\"Golden State Warriros\",\"Huston Rocket\"],\"answer\":\"Huston Rocket\"}},\"maths\":{\"q1\":{\"question\":\"5 + 7 = ?\",\"options\":[\"10\",\"11\",\"12\",\"13\"],\"answer\":\"12\"},\"q2\":{\"question\":\"12 - 8 = ?\",\"options\":[\"1\",\"2\",\"3\",\"4\"],\"answer\":\"4\"}}}}";
        assertEquals(expectedJson,decoratorMini.getData());
    }
    @Test
    public void testD(){
        String data = "{\n" +
                "\t\"id\": \"0001\",\n" +
                "\t\"type\": \"donut\",\n" +
                "\t\"name\": \"Cake\",\n" +
                "\t\"ppu\": 0.55,\n" +
                "\t\"batters\":\n" +
                "\t\t{\n" +
                "\t\t\t\"batter\":\n" +
                "\t\t\t\t[\n" +
                "\t\t\t\t\t{ \"id\": \"1001\", \"type\": \"Regular\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1002\", \"type\": \"Chocolate\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1003\", \"type\": \"Blueberry\" },\n" +
                "\t\t\t\t\t{ \"id\": \"1004\", \"type\": \"Devil's Food\" }\n" +
                "\t\t\t\t]\n" +
                "\t\t},\n" +
                "\t\"topping\":\n" +
                "\t\t[\n" +
                "\t\t\t{ \"id\": \"5001\", \"type\": \"None\" },\n" +
                "\t\t\t{ \"id\": \"5002\", \"type\": \"Glazed\" },\n" +
                "\t\t\t{ \"id\": \"5005\", \"type\": \"Sugar\" },\n" +
                "\t\t\t{ \"id\": \"5007\", \"type\": \"Powdered Sugar\" },\n" +
                "\t\t\t{ \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" },\n" +
                "\t\t\t{ \"id\": \"5003\", \"type\": \"Chocolate\" },\n" +
                "\t\t\t{ \"id\": \"5004\", \"type\": \"Maple\" }\n" +
                "\t\t]\n" +
                "}";
        Json json = new JsonImpl(data);
        decoratorMini = new minifyDecorator(json);
        String expectedJson = "{\"id\":\"0001\",\"type\":\"donut\",\"name\":\"Cake\",\"ppu\":0.55,\"batters\":{\"batter\":[{\"id\":\"1001\",\"type\":\"Regular\"},{\"id\":\"1002\",\"type\":\"Chocolate\"},{\"id\":\"1003\",\"type\":\"Blueberry\"},{\"id\":\"1004\",\"type\":\"Devil's Food\"}]},\"topping\":[{\"id\":\"5001\",\"type\":\"None\"},{\"id\":\"5002\",\"type\":\"Glazed\"},{\"id\":\"5005\",\"type\":\"Sugar\"},{\"id\":\"5007\",\"type\":\"Powdered Sugar\"},{\"id\":\"5006\",\"type\":\"Chocolate with Sprinkles\"},{\"id\":\"5003\",\"type\":\"Chocolate\"},{\"id\":\"5004\",\"type\":\"Maple\"}]}";
        assertEquals(expectedJson,decoratorMini.getData());
    }

  
}