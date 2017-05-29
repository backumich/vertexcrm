package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;

import javax.validation.Valid;

import java.util.List;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@Controller
public class CourseDetailsController {

    static final String COURSE_DETAILS_JSP = "courseDetails";
    static final String SEARCH_COURSE_JSP = "searchCourse";
    private static final String COURSE_DATA = "courseForInfo";

    private static final Logger LOGGER = LogManager.getLogger(CourseDetailsController.class);

    private final CourseLogic courseLogic;

    @GetMapping(value = "/searchCourseJsp")
    public ModelAndView searchCourseJsp() {
        LOGGER.debug(String.format("Show search page for courses"));
        return new ModelAndView(SEARCH_COURSE_JSP, COURSE_DATA, new Course());
    }

    @PostMapping(value = "/searchCourse")
    public String searchCourse(@Validated @ModelAttribute(COURSE_DATA) Course course, BindingResult bindingResult,
                               Model model) {
        LOGGER.debug(String.format("Search user by name - (%s) and finished - ", course.getName(), course.isFinished()));

        try {
            List<Course> courses = courseLogic.searchCourseByNameAndStatus(course);
            model.addAttribute("courses", courses);
            if (courses.isEmpty()) {
                model.addAttribute(MSG, "sdgdssfasfasfasfafafafafasfasf");
            }
        } catch (DataAccessException e) {

        } catch (Exception e) {

        }

        return SEARCH_COURSE_JSP;
    }

    @PostMapping(value = "/updateCourse")
    public String updateCourse(@Valid @ModelAttribute(COURSE_DATA) Course course, Model model) {
        LOGGER.debug(String.format("Update course with new data: - (%s)", course));

        String result = "redirect:home";

        try {
            courseLogic.updateCourseExceptPrice(course);
        } catch (DataAccessException e) {
            LOGGER.warn(e.getStackTrace());
            result = COURSE_DATA;
            model.addAttribute(MSG, "Some problems with database, try again");
            model.addAttribute(COURSE_DATA, course);
        } catch (Exception e) {
            LOGGER.warn(e);
            result = ERROR;
        }

        return result;
    }

    @Autowired
    public CourseDetailsController(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }
}
