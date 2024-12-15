package pl.put.poznan.transformer.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Json;
import pl.put.poznan.transformer.logic.JsonImpl;
import pl.put.poznan.transformer.logic.JsonTransformer;

@RestController
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(JsonHome.class);

    @RequestMapping(method = RequestMethod.POST,value = "/request")
    public String postAPI(@RequestParam(name = "method", defaultValue = "full") String method,
                                          @RequestParam(name = "attributes", defaultValue = "") String attributes,
                                          @RequestBody String data) {
        logger.info("Request for " + method);
        if(!attributes.equals("")) {
            logger.debug("Attributes: " + attributes);
        }

        Json json = new JsonImpl(data);
        JsonTransformer transformer = new JsonTransformer(method, attributes);

        return transformer.transform(json);
    }
}
