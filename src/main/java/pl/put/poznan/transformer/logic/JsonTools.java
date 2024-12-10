package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        String[] splitted = attributes.split(",");
        String jsonObject = "{";
        try {
            JsonNode json = mapper.readTree(json_text);
            for (String s : splitted) {
                JsonNode single_node = json.get(s.trim());
                if (single_node != null) {
                    jsonObject = jsonObject + '"' + s.trim() + '"' + " : " + mapper.writeValueAsString(single_node) + ",";
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

    public String [] delete_elemnt(String json_text,  String attributes) {
        ObjectMapper mapper = new ObjectMapper();
        String[] splitted = attributes.split(",");
        String successful_remove = "Removed attributes: ";
        String[] final_output = new String[2];
        try {
            JsonNode json = mapper.readTree(json_text);
            for (String s : splitted) {
                if(json.get(s.trim()) != null) {
                    ((ObjectNode) json).remove(s.trim());
                    successful_remove = successful_remove + s.trim() + ", ";
                }
            }
            final_output[0] = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            if(successful_remove.equals("Removed attributes: ")) {

                final_output[1] = "None of given attributes were found in JSON file.";
            } else {
                successful_remove = successful_remove.substring(0, successful_remove.length() - 2);
                final_output[1] = successful_remove;
            }
            return final_output;
        } catch (Exception e) {
            System.err.println("Error while removing attributes: " + e.getMessage());
        }
        return final_output;
    }
}
