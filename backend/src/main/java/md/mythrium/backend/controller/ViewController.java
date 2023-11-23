package md.mythrium.backend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class ViewController {


    @GetMapping(value = {"/", "/{path:[^.]+}/**"})
    public String application(@PathVariable(required = false) String path) {
        System.out.println(path);
        return "forward:/index.html";
    }

}
