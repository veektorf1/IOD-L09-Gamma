package pl.put.poznan.transformer.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.JsonImpl;
import pl.put.poznan.transformer.logic.JsonTransformer;

@RestController
public class ApiController {
    @RequestMapping(method = RequestMethod.POST,value = "/request")
    public ResponseEntity<String> postAPI(@RequestParam(name = "method", defaultValue = "full") String method,
                                          @RequestParam(name = "attributes", defaultValue = "") String attributes,
                                          @RequestBody String data) {
        System.out.println(method);
        System.out.println(data);
        System.out.println(attributes);

        Json json = new JsonImpl(data);
        JsonTransformer transformer = new JsonTransformer(method, attributes);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(transformer.transform(json));
    }
}
