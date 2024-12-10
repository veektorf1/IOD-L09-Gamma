package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
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

    public String show_selected(String json_text,  String attributes){
        ObjectMapper mapper = new ObjectMapper();
        String[] splited = attributes.split(",");
        String jsonObject = "{";
        try {
            JsonNode json = mapper.readTree(json_text);
            for(int i = 0; i< splited.length; i++) {
                JsonNode single_node = json.get(splited[i].trim());
                if (single_node != null) {
                    jsonObject = jsonObject + '"' + splited[i].trim() + '"'+ " : " + mapper.writeValueAsString(single_node) + ",";
                }
            }
            jsonObject = jsonObject.substring(0, jsonObject.length() - 1) + '}';
            System.out.println(jsonObject);
            JsonNode final_json = mapper.readTree(jsonObject);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(final_json);
        } catch (Exception e) {
            System.err.println("Error while converting text to JSON: " + e.getMessage());
        }
        return json_text;
    }
}
