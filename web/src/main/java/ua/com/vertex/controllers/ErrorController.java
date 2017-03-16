package ua.com.vertex.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    private static final String ERROR = "error";

    @GetMapping
    public String error() {
        return ERROR;
    }
}
