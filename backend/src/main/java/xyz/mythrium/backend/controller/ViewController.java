package xyz.mythrium.backend.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
public class ViewController {

    @GetMapping(value = "/")
    public String application() {
        return "/index.html";
    }

}
