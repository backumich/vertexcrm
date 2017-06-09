package ua.com.vertex.controllers.exceptionHandling;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    private static final String ERROR = "error";

    @RequestMapping(value = "/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "Database is temporarily unavailable");
        return ERROR;
    }

    @RequestMapping(value = "/404")
    public String handle404(Model model) {
        model.addAttribute("errorMessage", "404 — page not found");
        return ERROR;
    }
}
