package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.JsonImpl;
import pl.put.poznan.transformer.logic.JsonTransformer;

import static org.junit.jupiter.api.Assertions.*;

class JsonHomeTest {

    private final JsonHome jsonHome = new JsonHome();

    @Test
    void testPost1_FullJson() {
        String inputJson = "{           \"first name\": \"John\",           \"city\": \"Florida\",           \"age\": \"22\"   }";
        ConcurrentModel model = new ConcurrentModel();
        String viewName = jsonHome.post1(inputJson, model);
        JsonTransformer trans = new JsonTransformer("full", "");

        assertEquals("result", viewName);
        assertEquals("{\r\n" +
                "  \"first name\" : \"John\",\r\n" +
                "  \"city\" : \"Florida\",\r\n" +
                "  \"age\" : \"22\"\r\n" +
                "}", model.getAttribute("input2"));
    }

    @Test
    void testPost2_MinifyJson() {
        String inputJson = "{\r\n" +
                "  \"first name\" : \"John\",\r\n" +
                "  \"city\" : \"Florida\",\r\n" +
                "  \"age\" : \"22\"\r\n" +
                "}";
        ConcurrentModel model = new ConcurrentModel();

        String viewName = jsonHome.post2(inputJson, model);
        assertEquals("resultMinify", viewName);
        assertEquals("{\"first name\":\"John\",\"city\":\"Florida\",\"age\":\"22\"}", model.getAttribute("input1"));
    }

    @Test
    void testPost3_SelectAttributes() {
        String inputJson = "{\r\n" +
                "  \"first name\" : \"John\",\r\n" +
                "  \"city\" : \"Florida\",\r\n" +
                "  \"age\" : \"22\"\r\n" +
                "}";
        String attributes = "city";
        ConcurrentModel model = new ConcurrentModel();

        String viewName = jsonHome.post3(inputJson, attributes, model);

        assertEquals("resultSelected", viewName);

        // Sprawdzamy dane dodane do modelu
        assertEquals("{\r\n" +
                "  \"city\" : \"Florida\"\r\n" +
                "}", model.getAttribute("json"));
        assertEquals("Selected attributes: " + attributes, model.getAttribute("selected"));
    }

    @Test
    void testPost4_SelectAttributesEmpty() {
        String inputJson = "{\r\n" +
                "  \"first name\" : \"John\",\r\n" +
                "  \"city\" : \"Florida\",\r\n" +
                "  \"age\" : \"22\"\r\n" +
                "}";
        String attributes = "";
        ConcurrentModel model = new ConcurrentModel();

        String viewName = jsonHome.post3(inputJson, attributes, model);

        assertEquals("resultSelected", viewName);

        // Sprawdzamy dane dodane do modelu
        assertEquals("null", model.getAttribute("json"));
        assertEquals("None of chosen attributes were found in data." + attributes, model.getAttribute("selected"));
    }

    @Test
    void testPost5_SelectAttributesSomwWrong() {
        String inputJson = "{\r\n" +
                "  \"first name\" : \"John\",\r\n" +
                "  \"city\" : \"Florida\",\r\n" +
                "  \"age\" : \"22\"\r\n" +
                "}";
        String good_attributes = "city";
        String bad_attributes = "first namee";
        ConcurrentModel model = new ConcurrentModel();

        String viewName = jsonHome.post3(inputJson, good_attributes + "," + bad_attributes, model);

        assertEquals("resultSelected", viewName);

        // Sprawdzamy dane dodane do modelu
        assertEquals("{\r\n" +
                "  \"city\" : \"Florida\"\r\n" +
                "}", model.getAttribute("json"));
        assertEquals("Selected attributes: " + good_attributes + ". Didn't found: " + bad_attributes + ".",
                model.getAttribute("selected"));
    }

    @Test
    void testPost6_DeleteAttributes() {
        String inputJson = "{\r\n" +
                "  \"first name\" : \"John\",\r\n" +
                "  \"city\" : \"Florida\",\r\n" +
                "  \"age\" : \"22\"\r\n" +
                "}";
        String attributes = "age, first name";
        ConcurrentModel model = new ConcurrentModel();

        String viewName = jsonHome.post4(inputJson, attributes, model);

        assertEquals("resultDeleted", viewName);
        assertEquals("{\r\n" +
                "  \"city\" : \"Florida\"\r\n" +
                "}", model.getAttribute("json"));
        assertEquals("Removed attributes: " + attributes, model.getAttribute("deleted"));
    }

    @Test
    void testPost7_DeleteAttributesEmpty() {
        String inputJson = "";
        String attributes = "";
        ConcurrentModel model = new ConcurrentModel();

        String viewName = jsonHome.post4(inputJson, attributes, model);
        assertEquals("resultDeleted", viewName);
        assertEquals("null", model.getAttribute("json"));
        assertEquals("None of given attributes were found in JSON file." + attributes, model.getAttribute("deleted"));
    }
}