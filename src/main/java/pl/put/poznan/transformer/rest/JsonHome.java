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
    public String post1(@RequestParam("input2") String finalInput, Model model) {
        String[] arrStr = {};
        JsonTools tools = new JsonTools(arrStr);
        try{
            String jsonfull = tools.fullJson(finalInput);
            model.addAttribute("input2",jsonfull) ;
        } catch (Exception e) {
            model.addAttribute("input2", "Invalid JSON input: " + e.getMessage());
        }
        return "result";
    }
    @PostMapping("/postingMinify")
    public String post2(@RequestParam("input1") String finalInput, Model model) {
        String[] arrStr = {};
        JsonTools tools = new JsonTools(arrStr);
        try{
            String jsonminify = tools.minify(finalInput);
            model.addAttribute("input1",jsonminify) ;
        } catch (Exception e) {
            model.addAttribute("input1", "Invalid JSON input: " + e.getMessage());
        }
        return "resultMinify";
    }

    @PostMapping("/postingSelected")
    public String post3(@RequestParam("SelectedJSON") String finalInput,
                        @RequestParam("SelectedAttributes") String attributes, Model model) {
        String[] arrStr = {};

        JsonTools tools = new JsonTools(arrStr);
        try{
            String selected_json= tools.show_selected(finalInput, attributes);
            model.addAttribute("json",selected_json) ;

        } catch (Exception e) {
            model.addAttribute("json", "Invalid JSON input: " + e.getMessage());
        }
        return "resultSelected";
    }

}