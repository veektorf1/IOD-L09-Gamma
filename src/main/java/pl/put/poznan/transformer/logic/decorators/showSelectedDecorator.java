package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.JsonTransformer;


/**
 * Dekorator JSON, który pozwala na wybór i zwrócenie tylko określonych atrybutów z danych JSON.
 * Jeśli wybrane atrybuty nie istnieją, informacja o tym zostanie zwrócona w dodatkowym komunikacie.
 *
 * Przykładowe użycie:
 *
 * Json json = new SimpleJson("{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}");
 * showSelectedDecorator decorator = new showSelectedDecorator(json);
 * decorator.setAttributes("name, city");
 * String[] result = decorator.getDataSelected();
 *
 * Wynik:
 *
 * result[0]: {
 *   "name" : "John",
 *   "city" : "New York"
 * }
 *
 * result[1]: "Selected attributes: name, city."
 *
 * Jeśli podany atrybut nie istnieje:
 *
 * result[1]: "Selected attributes: name. Didn't found: country."
 *
 * Klasa rzuca RuntimeException, jeśli podane dane nie są poprawnym JSON-em.
 */
public class showSelectedDecorator extends JsonDecorator {

    private String attributes;

    /**
     * Konstruktor dekoratora.
     *
     * @param text Obiekt Json, który ma zostać przetworzony.
     */
    public showSelectedDecorator(Json text) {
        super(text);
    }

    /**
     * Ustawia atrybuty, które mają zostać wybrane z JSON-a.
     *
     * @param attributes Lista atrybutów oddzielonych przecinkami.
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    /**
     * Pobiera listę atrybutów do wybrania.
     *
     * @return Atrybuty w postaci ciągu znaków.
     */
    public String getAttributes() {
        return this.attributes;
    }

    /**
     * Zwraca wybrane atrybuty w postaci JSON oraz komunikat o znalezionych i brakujących atrybutach.
     *
     * @return Tablica String, gdzie:
     *         result[0] - JSON zawierający tylko wybrane atrybuty
     *         result[1] - Informacja o znalezionych i brakujących atrybutach
     */
    public String[] getDataSelected(){
        return show_selected(super.getData(),getAttributes());
    }

    /**
     * Wybiera określone atrybuty z JSON-a.
     *
     * @param jsonText  Wejściowy JSON w postaci String.
     * @param attributes Lista atrybutów oddzielonych przecinkami.
     * @return Tablica String:
     *         result[0] - JSON z wybranymi atrybutami sformatowany w czytelny sposób
     *         result[1] - Informacja o atrybutach (znalezione i brakujące)
     * @throws RuntimeException Jeśli podane dane nie są poprawnym JSON-em.
     */
    public String [] show_selected(String json_text,  String attributes){
        ObjectMapper mapper = new ObjectMapper();
        String[] final_output = new String[2];

        String[] splitted = attributes.split(",");
        String left_attributes = "";
        String jsonText = "{";
        String fianl_atributes = "Selected attributes: ";


        try {
            JsonNode json = mapper.readTree(json_text);
            for (String s : splitted) {
                JsonNode single_node = json.get(s.trim());
                if (single_node != null) {
                    fianl_atributes = fianl_atributes + s.trim() + ',';
                    jsonText = jsonText + '"' + s.trim() + '"' + " : " + mapper.writeValueAsString(single_node) + ",";
                } else {
                    left_attributes = left_attributes + s.trim() + ", ";
                }

            }

            if(jsonText.equals("{")) {
                fianl_atributes = "None of chosen attributes were found in data.";
                jsonText = "";
            } else {
                jsonText = jsonText.substring(0, jsonText.length() - 1) + '}';

                fianl_atributes = fianl_atributes.substring(0, fianl_atributes.length() - 1);

                if(!left_attributes.isEmpty()) {
                    left_attributes = left_attributes.substring(0, left_attributes.length() - 2);

                    fianl_atributes = fianl_atributes + ". Didn't found: " + left_attributes + '.';
                }
            }
            JsonNode final_json = mapper.readTree(jsonText);

            final_output[0] = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(final_json);
            final_output[1] = fianl_atributes;

            return final_output;
        } catch (Exception e) {
            System.err.println("Error while converting text to JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
