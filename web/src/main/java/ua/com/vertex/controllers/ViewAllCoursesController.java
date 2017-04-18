package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.DataNavigator;

import java.util.List;

@Controller
@RequestMapping(value = "/viewAllCourses")
@SessionAttributes(value = "viewAllCourses")
public class ViewAllCoursesController {
    private static final String ERROR_JSP = "error";
    private static final String PAGE_JSP = "viewAllCourses";

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private UserLogic userLogic;

    @GetMapping
    public ModelAndView viewAllCourses(@ModelAttribute DataNavigator dataNavigator) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Course> courses = userLogic.getCoursesPerPages(dataNavigator);
            modelAndView.addObject("viewAllUsers", dataNavigator);
            modelAndView.addObject("courses", courses);
            modelAndView.setViewName(PAGE_JSP);
            LOGGER.debug("Received a list of all users and transferred to the model");
            String allCourses = "";
            for (Course course : courses) {
                allCourses += course.getName() + "|";
            }
            LOGGER.debug("Quantity users -" + courses.size());
            LOGGER.debug("All users list -" + allCourses);
        } catch (Exception e) {
            LOGGER.warn(e);
            modelAndView.setViewName(ERROR_JSP);
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView reload(@ModelAttribute DataNavigator dataNavigator) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Course> courses = userLogic.getCoursesPerPages(dataNavigator);
            modelAndView.addObject("courses", courses);
            modelAndView.addObject("viewAllUsers", dataNavigator);
            modelAndView.setViewName(PAGE_JSP);
            LOGGER.debug("Received a list of all users and transferred to the model");
            String allCourses = "";
            for (Course course : courses) {
                allCourses += course.getName() + "|";
            }
            LOGGER.debug("Quantity users -" + courses.size());
            LOGGER.debug("All users list -" + allCourses);
        } catch (Exception e) {
            LOGGER.warn(e);
            modelAndView.setViewName(ERROR_JSP);
        }
        return modelAndView;
    }

    @ModelAttribute
    public DataNavigator createDataNavigator() {
        return new DataNavigator("viewAllUsers");
    }

    @Autowired
    public ViewAllCoursesController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}

