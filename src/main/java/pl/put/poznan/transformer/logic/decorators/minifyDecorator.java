package pl.put.poznan.transformer.logic.decorators;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.put.poznan.transformer.logic.Json;

public class minifyDecorator extends JsonDecorator {

    public minifyDecorator(Json text){
        super(text);
    }

    @Override
    public String getData(){
        return minify(super.getData());
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
