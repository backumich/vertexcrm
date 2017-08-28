package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.UserLogic;
import ua.com.vertex.utils.DataNavigator;
import ua.com.vertex.utils.EmailExtractor;

import java.sql.SQLException;
import java.util.List;

@Controller
@SessionAttributes(value = "viewCourses")
@RequestMapping(value = "/viewCourses")
public class ViewAllCoursesController {
    private static final String PAGE_JSP = "viewCourses";

    private static final Logger LOGGER = LogManager.getLogger(ViewAllCoursesController.class);

    private final CourseLogic courseLogic;
    private final UserLogic userLogic;
    private final EmailExtractor emailExtractor;

    @Autowired
    public ViewAllCoursesController(CourseLogic courseLogic, UserLogic userLogic, EmailExtractor emailExtractor) {
        this.courseLogic = courseLogic;
        this.userLogic = userLogic;
        this.emailExtractor = emailExtractor;
    }

    @RequestMapping(value = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllCourses(@ModelAttribute DataNavigator dataNavigator) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        int quantityCourses = courseLogic.getQuantityCourses();

        dataNavigator.updateDataNavigator(quantityCourses);
        dataNavigator.setCurrentNamePage("viewCourses/all");
        List<Course> courses = courseLogic.getCoursesPerPage(dataNavigator);

        modelAndView.addObject("viewCourses", dataNavigator);
        modelAndView.addObject("courses", courses);
        modelAndView.setViewName(PAGE_JSP);
        LOGGER.debug("Received a list of all courses and transferred to the model");

        return modelAndView;
    }

    @RequestMapping(value = "/teacher")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ModelAndView viewTeacherCourses(@ModelAttribute DataNavigator dataNavigator) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        User currentUser = userLogic.getUserByEmail(emailExtractor.getEmailFromAuthentication())
                .orElseThrow(() -> new RuntimeException("Not logged in: failed to get login details"));
        int quantityCourses = courseLogic.getQuantityCourses(currentUser);

        dataNavigator.updateDataNavigator(quantityCourses);
        dataNavigator.setCurrentNamePage("viewCourses/teacher");
        List<Course> courses = courseLogic.getCoursesPerPage(dataNavigator, currentUser);

        modelAndView.addObject("viewCourses", dataNavigator);
        modelAndView.addObject("courses", courses);
        modelAndView.setViewName(PAGE_JSP);
        LOGGER.debug("Received a list of all courses and transferred to the model");

        return modelAndView;
    }

    @ModelAttribute
    public DataNavigator createDataNavigator() {
        return new DataNavigator("viewCourses");
    }
}

