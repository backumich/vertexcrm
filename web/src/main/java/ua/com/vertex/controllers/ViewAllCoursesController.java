package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.DataNavigator;

import java.sql.SQLException;
import java.util.List;

@Controller
@SessionAttributes(value = "viewAllCourses")
public class ViewAllCoursesController {
    private static final String PAGE_JSP = "viewAllCourses";

    private static final Logger LOGGER = LogManager.getLogger(ViewAllCoursesController.class);

    private CourseLogic courseLogic;

    @RequestMapping(value = "/viewAllCourses")
    public ModelAndView viewAllCourses(@ModelAttribute DataNavigator dataNavigator) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        int quantityCourses = courseLogic.getQuantityCourses();

        dataNavigator.updateDataNavigator(quantityCourses);
        List<Course> courses = courseLogic.getCoursesPerPages(dataNavigator);

        modelAndView.addObject("viewAllCourses", dataNavigator);
        modelAndView.addObject("courses", courses);
        modelAndView.setViewName(PAGE_JSP);
        LOGGER.debug("Received a list of all courses and transferred to the model");

        return modelAndView;
    }

    @ModelAttribute
    public DataNavigator createDataNavigator() {
        return new DataNavigator("viewAllCourses");
    }

    @Autowired
    public ViewAllCoursesController(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }
}

