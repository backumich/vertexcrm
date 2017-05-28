package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.logic.interfaces.CourseLogic;

import static ua.com.vertex.controllers.CertificateDetailsPageController.ERROR;
import static ua.com.vertex.controllers.CreateCertificateAndUserController.MSG;

@Controller
public class CourseDetailsController {

    static final String CourseDetails_JSP = "courseDetails";
    private static final String COURSE_DATA = "courseForInfo";

    private static final Logger LOGGER = LogManager.getLogger(CourseDetailsController.class);

    private final CourseLogic courseLogic;

    @PostMapping(value = "/courseInfo")
    public ModelAndView cooraeInfo (@ModelAttribute (COURSE_DATA)Course course){
        LOGGER.debug(String.format("Show info page for course: - (%s)" ,course));
        return new ModelAndView(CourseDetails_JSP,COURSE_DATA,course);
    }

    @PostMapping(value = "/updateCourse")
    public String updateCourse (@ModelAttribute (COURSE_DATA)Course course, Model model){
        LOGGER.debug(String.format("Update course with new data: - (%s)" ,course));

        String result = "redirect:home";

        try {
            courseLogic.updateCourseExceptPrice(course);
        }catch (DataAccessException e){
            LOGGER.warn(e.getStackTrace());
            result = COURSE_DATA;
            model.addAttribute(MSG,"Some problems with database, try again");
            model.addAttribute(COURSE_DATA,course);
        }catch (Exception e){
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
