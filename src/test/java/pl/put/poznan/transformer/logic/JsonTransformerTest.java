package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonTransformerTest {

    @Test
    void wrong_method() {
        JsonTransformer trans = new JsonTransformer("XD", "");
        Json json = new JsonImpl("");
        assertEquals("", trans.transform(json));
    }

    @Test
    void empty_json_full() {
        JsonTransformer trans = new JsonTransformer("full", "");
        Json json = new JsonImpl("");
        assertEquals("null", trans.transform(json));
    }

    @Test
    void josn_full_test() {
        JsonTransformer trans = new JsonTransformer("full", "");

        Json json = new JsonImpl("{           \"first name\": \"John\",           \"city\": \"Florida\",           \"age\": \"22\"   }");
        assertEquals("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}", trans.transform(json));

    }

    @Test
    void empty_json_minify() {
        JsonTransformer trans = new JsonTransformer("minify", "");
        Json json = new JsonImpl("");
        assertEquals("null", trans.transform(json));
    }


    @Test
    void json_minify_test() {
        JsonTransformer trans = new JsonTransformer("minify", "");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("{\"first name\":\"John\",\"city\":\"Florida\",\"age\":\"22\"}", trans.transform(json));
    }

    @Test
    void show_selected_null() {
        JsonTransformer trans = new JsonTransformer("select", "");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("null", trans.transform(json));
    }

    @Test
    void show_selected_wrong() {
        JsonTransformer trans = new JsonTransformer("select", "cityy");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("null", trans.transform(json));
    }

    @Test
    void show_selected_test() {
        JsonTransformer trans = new JsonTransformer("select", "city");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("{\n" +
                "  \"city\" : \"Florida\"\n" +
                "}", trans.transform(json));
    }

    @Test
    void show_selected_wrong_sepearator() {
        JsonTransformer trans = new JsonTransformer("select", "city. first name");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("null", trans.transform(json));
    }

    @Test
    void delete_selected_null() {
        JsonTransformer trans = new JsonTransformer("delete", "");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");


        assertEquals("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}", trans.transform(json));
    }

    @Test
    void delete_selected_wrong() {
        JsonTransformer trans = new JsonTransformer("delete", "agee");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}", trans.transform(json));
    }

    @Test
    void delete_selected_wrong_separator() {
        JsonTransformer trans = new JsonTransformer("delete", "age. first name");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}", trans.transform(json));
    }

    @Test
    void delete_selected_test() {
        JsonTransformer trans = new JsonTransformer("delete", "age, first name");

        Json json = new JsonImpl("{\n" +
                "  \"first name\" : \"John\",\n" +
                "  \"city\" : \"Florida\",\n" +
                "  \"age\" : \"22\"\n" +
                "}");
        assertEquals("{\n" +
                "  \"city\" : \"Florida\"\n" +
                "}", trans.transform(json));
    }
}