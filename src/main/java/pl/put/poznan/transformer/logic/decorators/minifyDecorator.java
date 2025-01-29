package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.put.poznan.transformer.logic.Json;

/**
 * Dekorator JSON, który usuwa zbędne białe znaki i zwraca JSON w wersji zminimalizowanej (bez wcięć i nowych linii).
 * Klasa rozszerza JsonDecorator i używa ObjectMapper do przekształcenia JSON-a.
 *
 * Przykładowe użycie:
 *
 * Json json = new SimpleJson("{\n  \"name\": \"John\",\n  \"age\": 30\n}");
 * Json decoratedJson = new minifyDecorator(json);
 * System.out.println(decoratedJson.getData());
 *
 * Wynik:
 *
 * {"name":"John","age":30}
 *
 * Klasa rzuca RuntimeException, jeśli podany tekst nie jest poprawnym JSON-em.
 */
public class minifyDecorator extends JsonDecorator {

    /**
     * Konstruktor dekoratora.
     *
     * @param text Obiekt Json, który ma zostać zminimalizowany.
     */
    public minifyDecorator(Json text){
        super(text);
    }

    /**
     * Pobiera dane JSON i zwraca je w wersji zminimalizowanej.
     *
     * @return Zminimalizowany JSON jako String
     */
    @Override
    public String getData(){
        return minify(super.getData());
    }

    /**
     * Konwertuje podany tekst na zminimalizowany JSON.
     *
     * @param text Surowy JSON jako String
     * @return Zminimalizowany JSON bez zbędnych białych znaków
     * @throws RuntimeException Jeśli przekazany tekst nie jest poprawnym JSON-em
     */
    public String minify(String text){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode main_json_tree = mapper.readTree(text);
            return mapper.writeValueAsString(main_json_tree);
        } catch (Exception e) {
            System.err.println("Error converting text to JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
