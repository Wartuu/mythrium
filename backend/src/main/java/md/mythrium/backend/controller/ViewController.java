package md.mythrium.backend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class ViewController {


    @RequestMapping(value = "/")
    public String application() {
        return "index.html";
    }

}
