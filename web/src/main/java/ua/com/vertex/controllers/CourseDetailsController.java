package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.UserLogic;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static ua.com.vertex.controllers.AdminController.ADMIN_JSP;
import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@Controller
public class CourseDetailsController {
    static final String COURSE_DETAILS_JSP = "courseDetails";
    static final String SEARCH_COURSE_JSP = "searchCourse";
    static final String COURSE_DATA = "courseForInfo";
    static final String COURSE = "course";
    static final String COURSES = "courses";
    static final String COURSE_ID = "courseId";
    static final String TEACHERS = "teachers";
    static final String LISTENERS = "listeners";
    static final String LOGGER_SERVER_EXCEPTION = "Problems with the server, try again later.";

    private static final Logger LOGGER = LogManager.getLogger(CourseDetailsController.class);

    private final CourseLogic courseLogic;
    private final UserLogic userLogic;

    @PostMapping(value = "/searchCourseJsp")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView searchCourseJsp() {
        LOGGER.debug("Show search page for courses");
        return new ModelAndView(SEARCH_COURSE_JSP, COURSE_DATA, new Course());
    }

    @PostMapping(value = "/searchCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchCourse(@Validated @ModelAttribute(COURSE_DATA) Course course,
                               BindingResult bindingResult, Model model) {
        LOGGER.debug(String.format("Search user by name - (%s) and finished - (%s).",
                course.getName(), course.isFinished()));
        String result = SEARCH_COURSE_JSP;

        if (!bindingResult.hasErrors()) {
            try {
                List<Course> courses = courseLogic.searchCourseByNameAndStatus(course.getName(), course.isFinished());
                if (courses.isEmpty()) {
                    model.addAttribute(MSG, String.format("Course with name - '(%s)' not found. " +
                            "Please check the data and try it again.", course.getName()));
                } else {
                    model.addAttribute("courses", courses);
                }
            } catch (DataAccessException e) {
                LOGGER.warn(e);
                model.addAttribute(MSG, LOGGER_SERVER_EXCEPTION);
            } catch (Exception e) {
                LOGGER.warn(e);
                result = ERROR;
            }
        }

        return result;
    }

    @GetMapping(value = "/courseDetails")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView courseDetails(@RequestParam(COURSE_ID) int courseId) {
        LOGGER.debug(String.format("Go to the course information page. Course ID -: - (%s)", courseId));

        ModelAndView result = new ModelAndView(COURSE_DETAILS_JSP);

        try {
            result.addObject(COURSE, courseLogic.getCourseById(courseId).orElseThrow((NoSuchElementException::new)));
            result.addObject(TEACHERS, userLogic.getTeachers());
        } catch (Exception e) {
            LOGGER.warn(e);
            result.setViewName(ERROR);
        }

        return result;
    }

    @PostMapping(value = "/updateCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCourse(@Valid @ModelAttribute(COURSE) Course course, BindingResult bindingResult, Model model) {
        LOGGER.debug(String.format("Update course with course ID - (%s). Course details: - (%s)", course.getId(), course));
        String result = ADMIN_JSP;
        if (!bindingResult.hasErrors()) {
            try {
                model.addAttribute(MSG, String.format("Course with id - (%s) updated.",
                        courseLogic.updateCourseExceptPrice(course)));
            } catch (DataAccessException e) {
                LOGGER.warn(e);
                model.addAttribute(MSG, LOGGER_SERVER_EXCEPTION);
                result = SEARCH_COURSE_JSP;
            } catch (Exception e) {
                LOGGER.warn(e);
                result = ERROR;
            }
        } else {
            result = COURSE_DETAILS_JSP;
            model.addAttribute(TEACHERS, userLogic.getTeachers());
        }
        return result;
    }

    @PostMapping(value = "/teacherCourseDetails")
    @PreAuthorize("hasRole('TEACHER')")
    public ModelAndView teacherCourseDetails(@RequestParam(COURSE_ID) int courseId) {

        LOGGER.debug(String.format("Call teacherCourseDetails(%s)", courseId));
        ModelAndView result = new ModelAndView(COURSE_DETAILS_JSP);

        LOGGER.debug(String.format("Try add course details by id - (%s).", courseId));
        result.addObject(COURSE, courseLogic.getCourseById(courseId).orElseThrow(NoSuchElementException::new));

        LOGGER.debug(String.format("Try add course listeners by id - (%s).", courseId));
        result.addObject(LISTENERS, userLogic.getCourseUsers(courseId));

        LOGGER.debug(String.format("Return new ModelAndView - %s", result));
        return result;
    }

    @Autowired
    public CourseDetailsController(CourseLogic courseLogic, UserLogic userLogic) {
        this.courseLogic = courseLogic;
        this.userLogic = userLogic;
    }
}
