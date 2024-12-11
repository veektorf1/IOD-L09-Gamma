package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

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

    public String [] comparison(String main_json, String json_to_compare) {
        ObjectMapper mapper = new ObjectMapper();
        String[] result = new String[3];
        try {
            JsonNode main_json_tree = mapper.readTree(main_json);
            JsonNode second_json_tree = mapper.readTree(json_to_compare);

            String main_json_string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(main_json_tree);
            String second_json_string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(second_json_tree);
            Scanner main_scanner = new Scanner(main_json_string);
            Scanner second_scanner = new Scanner(second_json_string);

            String comparison_result = "";
            while (main_scanner.hasNextLine()) {
                String line = main_scanner.nextLine();
                if(second_scanner.hasNextLine()) {
                    if(line.equals(second_scanner.nextLine())) {
                        comparison_result = comparison_result + " Same \n";
                    } else {
                        comparison_result = comparison_result + " Different \n";
                    }
                } else {
                    comparison_result = comparison_result + " Different \n";
                }
            }

            result[0]=main_json_string;
            result[1]=second_json_string;
            result[2]=comparison_result;

            return result;
        } catch (Exception e) {
            System.err.println("Error while removing attributes: " + e.getMessage());
        }
        return result;
        /*
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(json_text);
            String html = "{";
    String html = mapper.writeValueAsString(json);
            html = html.substring(1,html.length() - 1);



            Iterator<String> iterator = json.fieldNames();

            while(iterator.hasNext()) {
                String curr_value = iterator.next();
                if(curr_value == "topping") {
                    html = html + "<div style=\"color:Tomato;\">" + '"' + curr_value + '"' + " : " +
                            json.get(curr_value) + "," + "</div>";
                } else {
                    html =  html + '"' + curr_value + '"' + " : " + json.get(curr_value) + ",";
                }
            }
            html = html.substring(0, html.length() - 1) + '}';



            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(html);
        } catch (Exception e) {
            System.err.println("Error while removing attributes: " + e.getMessage());
        }
        return "";
         */
    }
}
