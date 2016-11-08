package ua.com.vertex.controllers;


@Controller
public class MainController {

    private static int count = 0;

    @RequestMapping("/welcome")
    public ModelAndView welcome() {
        String message = String.format("Welcome, you are %d today", ++count);
        ModelAndView result = new ModelAndView("redirect:/welcome.jsp", "message", message);
        return result;
    }
}
