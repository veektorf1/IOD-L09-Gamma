package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.JsonTools;


/**
 * Dekorator JSON, który formatuje dane wejściowe do czytelnego, wciętego JSON-a.
 * Klasa rozszerza JsonDecorator i używa ObjectMapper do przekształcenia JSON-a.
 *
 * Przykładowe użycie:
 *
 * Json json = new SimpleJson("{\"name\":\"John\",\"age\":30}");
 * Json decoratedJson = new fullJsonDecorator(json);
 * System.out.println(decoratedJson.getData());
 *
 * Wynik:
 *
 * {
 *   "name" : "John",
 *   "age" : 30
 * }
 *
 * Klasa rzuca RuntimeException, jeśli podany tekst nie jest poprawnym JSON-em.
 */
public class fullJsonDecorator extends JsonDecorator {

    /**
     * Konstruktor dekoratora.
     *
     * @param text Obiekt Json, który ma zostać sformatowany do czytelnej postaci.
     */
    public fullJsonDecorator(Json text){
        super(text);
    }

    /**
     * Pobiera dane JSON i zwraca je w sformatowanej postaci.
     *
     * @return Sformatowany JSON jako String
     */
    @Override
    public String getData() {
        return fullJson(super.getData());
    }

    /**
     * Konwertuje podany tekst na sformatowany JSON.
     *
     * @param text Surowy JSON jako String
     * @return Sformatowany JSON z wcięciami
     * @throws RuntimeException Jeśli przekazany tekst nie jest poprawnym JSON-em
     */
    public String fullJson(String text){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode main_json_tree = mapper.readTree(text);
           return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(main_json_tree);
        } catch (Exception e) {
            System.err.println("Error converting text to JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
