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


    @PostMapping("/postingDeleted")
    public String post4(@RequestParam("DeletedJSON") String finalInput,
                        @RequestParam("DeleteAttributes") String attributes, Model model) {
        String[] arrStr = {};
        JsonTools tools = new JsonTools(arrStr);
        try{
            String[] deleted_json = tools.delete_elemnt(finalInput, attributes);
            model.addAttribute("json", deleted_json[0]);
            model.addAttribute("deleted", deleted_json[1]);

        } catch (Exception e) {
            model.addAttribute("json", "Invalid JSON input: " + e.getMessage());
        }
        return "resultDeleted";
    }

    @PostMapping("/postingComparison")
    public String post5(@RequestParam("MainJSON") String mainInput,@RequestParam("SecJSON") String secInput, Model model) {
        String[] arrStr = {};
        JsonTools tools = new JsonTools(arrStr);
        try{
            String[] json = tools.comparison(mainInput,secInput);
            model.addAttribute("main", json[0]);
            model.addAttribute("sec", json[1]);
            model.addAttribute("comp", json[2]);
        } catch (Exception e) {
            model.addAttribute("json", "Invalid JSON input: " + e.getMessage());
        }
        return "resultComparison";
    }
}