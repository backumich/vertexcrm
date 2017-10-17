package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.NoSuchElementException;

@Controller
public class StudentDetailsController {
    static final String STUDENT_DETAILS_JSP = "studentDetails";
    static final String USER_ID = "userId";
    static final String USER = "user";

    private static final Logger logger = LogManager.getLogger(StudentDetailsController.class);

    private final UserLogic userLogic;

    @GetMapping(value = "/studentDetails")
    @PreAuthorize("hasRole('TEACHER')")
    public ModelAndView getStudentDetails(@RequestParam(USER_ID) int userId) {
        logger.debug(String.format("Call studentDetails(%s)", userId));
        return new ModelAndView(STUDENT_DETAILS_JSP)
                .addObject(USER, userLogic.getUserById(userId).orElseThrow(NoSuchElementException::new));
    }

    @Autowired
    public StudentDetailsController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}
