package md.mythrium.backend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {


    @GetMapping(value = {"/", ""})
    public String application() {
        return "/index.html";
    }

}
