package put.io.testing.mocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;


import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.decorators.*;

public class JsonToolsTest {

    @Test
    public void toFullJSONTest() {
        Json mockJson = mock(Json.class);
        String inputJson = "{\"name\":\"John\",\"age\":30}";
        String expectedJson = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"age\" : 30\n" +
                "}";

        when(mockJson.getData()).thenReturn(inputJson);
        fullJsonDecorator decorator = new fullJsonDecorator(mockJson);
        String result = decorator.getData();

        assertEquals(expectedJson, result,"Dekorator powinien poprawnie sformatować zminifikowany JSON na pełny format JSON");
        verify(mockJson,times(1)).getData();

    }
    @Test
    public void toFullJSONTest2() {
        Json mockJson = mock(Json.class);
        String inputJson = "{}";
        String expectedJson = "{ }";

        when(mockJson.getData()).thenReturn(inputJson);
        fullJsonDecorator decorator = new fullJsonDecorator(mockJson);
        String result = decorator.getData();

        assertEquals(expectedJson, result, "Dekorator powinien poprawnie sformatować pusty JSON");
        verify(mockJson, times(1)).getData();
    }

    @Test
    public void toFullJSONTest3() {
        Json mockJson = mock(Json.class);
        String inputJson = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}";
        String expectedJson = "{\n" +
                "  \"glossary\" : {\n" +
                "    \"title\" : \"example glossary\",\n" +
                "    \"GlossDiv\" : {\n" +
                "      \"title\" : \"S\",\n" +
                "      \"GlossList\" : {\n" +
                "        \"GlossEntry\" : {\n" +
                "          \"ID\" : \"SGML\",\n" +
                "          \"SortAs\" : \"SGML\",\n" +
                "          \"GlossTerm\" : \"Standard Generalized Markup Language\",\n" +
                "          \"Acronym\" : \"SGML\",\n" +
                "          \"Abbrev\" : \"ISO 8879:1986\",\n" +
                "          \"GlossDef\" : {\n" +
                "            \"para\" : \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                "            \"GlossSeeAlso\" : [ \"GML\", \"XML\" ]\n" +
                "          },\n" +
                "          \"GlossSee\" : \"markup\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        when(mockJson.getData()).thenReturn(inputJson);
        fullJsonDecorator decorator = new fullJsonDecorator(mockJson);
        String result = decorator.getData();

        assertEquals(expectedJson, result, "Dekorator powinien poprawnie sformatować zagnieżdżony JSON");
        verify(mockJson, times(1)).getData();
    }
    @Test
    public void toFullJSONTest4() {
        Json mockJson = mock(Json.class);
        String inputJson = "";
        when(mockJson.getData()).thenReturn("");

        fullJsonDecorator decorator = new fullJsonDecorator(mockJson);
        String result = decorator.getData();

        assertEquals("null",result,"Dekorator powinien zwrócić null dla pustego pola");
        verify(mockJson, times(1)).getData();
    }

    @Test
    public void minifyTest(){
        Json mockJson = mock(Json.class);
        String inputJSON = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"age\" : 30\n" +
                "}";
        String expectedJSON = "{\"name\":\"John\",\"age\":30}";

        minifyDecorator decorator = new minifyDecorator(mockJson);
        when(mockJson.getData()).thenReturn(inputJSON);
        String result = decorator.getData();

        assertEquals(expectedJSON,result,"Dekorator powinien zminifikować pełną wersję JSONa");
        verify(mockJson,times(1)).getData();
    }

    @Test
    public void minifyTest2(){
        Json mockJson = mock(Json.class);
        String expectedJSON = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}";
        String inputJSON = "{\n" +
                "  \"glossary\" : {\n" +
                "    \"title\" : \"example glossary\",\n" +
                "    \"GlossDiv\" : {\n" +
                "      \"title\" : \"S\",\n" +
                "      \"GlossList\" : {\n" +
                "        \"GlossEntry\" : {\n" +
                "          \"ID\" : \"SGML\",\n" +
                "          \"SortAs\" : \"SGML\",\n" +
                "          \"GlossTerm\" : \"Standard Generalized Markup Language\",\n" +
                "          \"Acronym\" : \"SGML\",\n" +
                "          \"Abbrev\" : \"ISO 8879:1986\",\n" +
                "          \"GlossDef\" : {\n" +
                "            \"para\" : \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                "            \"GlossSeeAlso\" : [ \"GML\", \"XML\" ]\n" +
                "          },\n" +
                "          \"GlossSee\" : \"markup\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";


        minifyDecorator decorator = new minifyDecorator(mockJson);
        when(mockJson.getData()).thenReturn(inputJSON);
        String result = decorator.getData();

        assertEquals(expectedJSON,result);
        verify(mockJson,times(1)).getData();
    }

    @Test
    public void showSelectedTest(){
        Json mockJson = mock(Json.class);
        String inputJSON = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"age\" : 30\n" +
                "}";
        String expectedJSON = "{\n" +
                "  \"name\" : \"John\"\n" +
                "}";

        showSelectedDecorator decorator = new showSelectedDecorator(mockJson);
        decorator.setAttributes("name");
        System.out.println(decorator.getAttributes());
        when(mockJson.getData()).thenReturn(inputJSON);

        String result = decorator.getDataSelected()[0];

        assertEquals(expectedJSON, result, "Dekorator powinien zwrócić tylko atrybut 'name'");
        verify(mockJson,times(1)).getData();
    }

    @Test
    public void showSelectedTest2(){
        Json mockJson = mock(Json.class);
        String inputJSON = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"age\" : 30\n" +
                "}";
        String expectedJSON = "null";

        showSelectedDecorator decorator = new showSelectedDecorator(mockJson);
        decorator.setAttributes("");
        System.out.println(decorator.getAttributes());
        when(mockJson.getData()).thenReturn(inputJSON);

        String result = decorator.getDataSelected()[0];

        assertEquals(expectedJSON, result, "Dekorator powinien zwrócić null");
        verify(mockJson,times(1)).getData();
    }

    @Test
    public void deleteTest(){
        Json mockJson = mock(Json.class);
        String inputJSON = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"age\" : 30,\n" +
                "  \"sex\" : \"Men\" \n" +
                "}";
        String expectedJSON = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"sex\" : \"Men\"\n" +
                "}";

        deleteElementDecorator decorator = new deleteElementDecorator(mockJson);
        decorator.setAttributes("age");
        System.out.println(decorator.getAttributes());
        when(mockJson.getData()).thenReturn(inputJSON);

        String result = decorator.getDataDeleted()[0];

        assertEquals(expectedJSON, result, "Dekorator powinien zwrócić atrybuty 'name i sex'");
        verify(mockJson,times(1)).getData();
    }

    @Test
    public void comparisonTest(){
        Json mockJson = mock(Json.class);
        String inputJSON = "{\"name\":\"John\",\"age\":30}";
        String compareJSON = "{\n" +
                "  \"name\" : \"Michael\",\n" +
                "  \"age\" : 30\n" +
                "}";
        String expectedResult = " Same \n" +
                " Different \n" +
                " Same \n" +
                " Same \n";

        comparisonDecorator decorator = new comparisonDecorator(mockJson);
        decorator.setAttributes(compareJSON);
        System.out.println(decorator.getAttributes());
        when(mockJson.getData()).thenReturn(inputJSON);

        String result = decorator.getDataComparison()[2];

        assertEquals(expectedResult, result, "Dekorator powinien zwrócić różnice pomiędzy oba jsonami w linijce z atrybutem name");
        verify(mockJson,times(1)).getData();
    }
}
