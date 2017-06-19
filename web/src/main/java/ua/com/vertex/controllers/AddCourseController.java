package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/addCourse")
public class AddCourseController {

    private static final String PAGE_JSP = "addCourse";
    private static final String ALL_COURSE_PAGE_JSP = "/viewAllCourses";
    private static final String ERROR_JSP = "error";
    private static final String NAME_MODEL = "course";

    private static final Logger LOGGER = LogManager.getLogger(AddCourseController.class);

    private CourseLogic courseLogic;


    @Autowired
    public AddCourseController(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }

    @GetMapping
    public ModelAndView viewAddCourseForm() {
        LOGGER.debug("First request to " + PAGE_JSP);
        return new ModelAndView(PAGE_JSP, NAME_MODEL, new Course());
    }

    @PostMapping
    public ModelAndView addCourse(@Valid @ModelAttribute(NAME_MODEL)
                                          Course course, BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            LOGGER.debug("The user entered incorrect course data -" + course);
            modelAndView.setViewName(PAGE_JSP);
        } else {
            try {
                course.setId(courseLogic.addCourse(course));
                LOGGER.debug("Added a new course into DB" + course);
                modelAndView.setViewName("redirect:" + ALL_COURSE_PAGE_JSP);
                //return new ModelAndView("redirect:" + ALL_COURSE_PAGE_JSP);
            } catch (Exception e) {
                modelAndView.setViewName(ERROR_JSP);
                LOGGER.warn("Could not save data to database", e);
            }
        }
        return modelAndView;
    }
}