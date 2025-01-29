package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pl.put.poznan.transformer.logic.Json;

/**
 * Dekorator JSON, który usuwa wybrane atrybuty z danych JSON.
 * Jeśli podane atrybuty nie istnieją, zwraca odpowiednią informację.
 *
 * Przykładowe użycie:
 *
 * Json json = new SimpleJson("{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}");
 * deleteElementDecorator decorator = new deleteElementDecorator(json);
 * decorator.setAttributes("age, city");
 * String[] result = decorator.getDataDeleted();
 *
 * Wynik:
 *
 * result[0]: {
 *   "name" : "John"
 * }
 *
 * result[1]: "Removed attributes: age, city."
 *
 * Jeśli podany atrybut nie istnieje:
 *
 * result[1]: "None of given attributes were found in JSON file."
 *
 * Klasa rzuca RuntimeException, jeśli podane dane nie są poprawnym JSON-em.
 */
public class deleteElementDecorator extends JsonDecorator {

    private String attributes;

    /**
     * Konstruktor dekoratora.
     *
     * @param text Obiekt Json, który ma zostać przetworzony.
     */
    public deleteElementDecorator(Json text){
        super(text);
    }

    /**
     * Ustawia atrybuty, które mają zostać usunięte z JSON-a.
     *
     * @param attributes Lista atrybutów oddzielonych przecinkami.
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    /**
     * Pobiera listę atrybutów do usunięcia.
     *
     * @return Atrybuty w postaci ciągu znaków.
     */
    public String getAttributes() {
        return this.attributes;
    }

    /**
     * Usuwa wybrane atrybuty i zwraca nowy JSON oraz komunikat o usuniętych atrybutach.
     *
     * @return Tablica String, gdzie:
     *         result[0] - JSON po usunięciu wskazanych atrybutów
     *         result[1] - Informacja o usuniętych atrybutach lub ich braku
     */
    public String[] getDataDeleted(){
        return delete_element(super.getData(),attributes);
    }

    /**
     * Usuwa określone atrybuty z JSON-a.
     *
     * @param jsonText   Wejściowy JSON w postaci String.
     * @param attributes Lista atrybutów oddzielonych przecinkami.
     * @return Tablica String:
     *         result[0] - JSON po usunięciu atrybutów, sformatowany w czytelny sposób
     *         result[1] - Informacja o usuniętych atrybutach lub ich braku
     * @throws RuntimeException Jeśli podane dane nie są poprawnym JSON-em.
     */
    public String [] delete_element(String json_text,  String attributes) {
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
            throw new RuntimeException(e);
        }
    }

}
