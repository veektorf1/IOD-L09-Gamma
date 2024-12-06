package pl.put.poznan.transformer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import pl.put.poznan.transformer.logic.JsonTools;

@Controller
public class JsonHome {
    public int i;



    @RequestMapping("/")
    public String returnIndex (){
        return "home";
    }
    @PostMapping("/posting")

    public String po(@RequestParam("input2") String finalInput, Model model) {
        //JsonTools tools = new JsonTools(finalInput);
        //tools.transform();
        //model.addAttribute("input2", ;
        String[] arrStr = {};
        JsonTools tools = new JsonTools(arrStr);
        String upper = tools.transform(finalInput);
        model.addAttribute("input2",upper) ;
        return "result";
    }


}