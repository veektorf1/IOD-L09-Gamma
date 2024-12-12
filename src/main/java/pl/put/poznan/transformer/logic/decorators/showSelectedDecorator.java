package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.put.poznan.transformer.logic.Json;

public class showSelectedDecorator extends JsonDecorator {

    private String attributes;
    public showSelectedDecorator(Json text) {
        super(text);
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getData(){
        return show_selected(super.getData(),attributes);
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
}
