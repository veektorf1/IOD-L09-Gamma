package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class JsonTools {

    private final String[] transforms;

    public JsonTools(String[] transforms){
        this.transforms = transforms;
    }

    public String fullJson(String text){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object json = mapper.readValue(text, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            System.err.println("Error converting text to JSON: " + e.getMessage());
        }
        return text;
    }

    public String minify(String text){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object json = mapper.readValue(text, Object.class);
            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            System.err.println("Error while minifying: " + e.getMessage());
        }
        return text;
    }
}
