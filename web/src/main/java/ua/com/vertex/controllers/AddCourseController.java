package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;

import static ua.com.vertex.controllers.CourseDetailsController.TEACHERS;

@Controller
@RequestMapping(value = "/addCourse")
@PreAuthorize("hasRole('ADMIN')")
public class AddCourseController {
    private static final String PAGE_JSP = "addCourse";
    private static final String ALL_COURSE_PAGE_JSP = "redirect:/viewAllCourses";
    private static final String ERROR_JSP = "error";
    private static final String COURSE = "course";

    private static final Logger LOGGER = LogManager.getLogger(AddCourseController.class);

    private CourseLogic courseLogic;
    private UserLogic userLogic;

    @GetMapping
    public ModelAndView viewAddCourseForm() {
        LOGGER.debug("Get page -" + PAGE_JSP);
        ModelAndView result = new ModelAndView(PAGE_JSP, COURSE, new Course());
        result.addObject(TEACHERS, userLogic.getTeachers());
        return result;
    }

    @PostMapping
    public ModelAndView addCourse(@Valid @ModelAttribute(COURSE) Course course,
                                  BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            LOGGER.debug("The user entered incorrect course data -" + course);
            modelAndView.setViewName(PAGE_JSP);
        } else {
//            try {
                course.setId(courseLogic.addCourse(course));
                LOGGER.debug("Added a new course into DB" + course);
                modelAndView.setViewName(ALL_COURSE_PAGE_JSP);
//            } catch (Exception e) {
//                modelAndView.setViewName(ERROR_JSP);
//                LOGGER.warn("Could not save data to database", e);
//            }
        }
        return modelAndView;
    }

    @Autowired
    public AddCourseController(CourseLogic courseLogic, UserLogic userLogic) {
        this.courseLogic = courseLogic;
        this.userLogic = userLogic;
    }
}