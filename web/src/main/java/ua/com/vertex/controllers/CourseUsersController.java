package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.List;

@Controller
public class CourseUsersController {
    private static final Logger LOGGER = LogManager.getLogger(CourseUsersController.class);
    private static final String COURSE_USERS = "courseUsers";
    private static final String ASSIGNED_USERS = "assignedUsers";
    private static final String REMOVAL_CONFIRM = "courseUserRemovalConfirm";
    private static final String FREE_USERS = "freeUsers";
    private static final String SEARCH = "search";
    private static final String DTO = "dtoCourseUser";

    private final CourseLogic courseLogic;

    @GetMapping(value = "/showCourseAndUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCourseAndUsersPage(@ModelAttribute Course course, Model model) {

        LOGGER.debug("Show users assigned to course id=" + course.getId());

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(course.getId());
        DtoCourseUser dtoCourseUser = new DtoCourseUser();
        dtoCourseUser.setCourseId(course.getId());

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(new User());
        model.addAttribute(DTO, dtoCourseUser);

        return COURSE_USERS;
    }

    @PostMapping(value = "/removeUserFromCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeUserFromAssigned(@ModelAttribute DtoCourseUser dtoCourseUser, Model model) {

        LOGGER.debug(String.format("Remove user=%d from course id=%d", dtoCourseUser.getUserId(),
                dtoCourseUser.getCourseId()));

        courseLogic.removeUserFromCourse(dtoCourseUser);
        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dtoCourseUser.getCourseId());
        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(DTO, dtoCourseUser);

        return COURSE_USERS;
    }

    @PostMapping(value = "/assignUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String assignUserToCourse(@ModelAttribute DtoCourseUser dtoCourseUser, Model model) {

        LOGGER.debug(String.format("Assign user=%d to course id=%d", dtoCourseUser.getUserId(),
                dtoCourseUser.getCourseId()));

        courseLogic.assignUserToCourse(dtoCourseUser);
        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dtoCourseUser.getCourseId());
        List<User> freeUsers = courseLogic.searchForUsersToAssign(dtoCourseUser);

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(FREE_USERS, freeUsers);
        model.addAttribute(DTO, dtoCourseUser);
        model.addAttribute(SEARCH, true);

        return COURSE_USERS;
    }

    @GetMapping(value = "/searchForUsersToAssign")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchForUsersToAssign(@ModelAttribute DtoCourseUser dtoCourseUser, Model model) {

        LOGGER.debug(String.format("Search for users that can be assigned to course id=%d" +
                        "by searchType=%s and searchParam=%s", dtoCourseUser.getCourseId(), dtoCourseUser.getSearchType(),
                dtoCourseUser.getSearchParam()));

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dtoCourseUser.getCourseId());
        List<User> freeUsers = courseLogic.searchForUsersToAssign(dtoCourseUser);

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(FREE_USERS, freeUsers);
        model.addAttribute(DTO, dtoCourseUser);
        model.addAttribute(SEARCH, true);

        return COURSE_USERS;
    }

    @GetMapping(value = "/clearSearchResults")
    @PreAuthorize("hasRole('ADMIN')")
    public String clearSearchResults(@ModelAttribute DtoCourseUser dtoCourseUser, Model model) {

        LOGGER.debug("Clear free users search results");

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dtoCourseUser.getCourseId());
        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(DTO, dtoCourseUser);

        return COURSE_USERS;
    }

    @PostMapping(value = "/confirmUserRemovalFromCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public String confirmUserRemovalFromCourse(@ModelAttribute DtoCourseUser dtoCourseUser, Model model) {

        LOGGER.debug(String.format("Confirm removing user id=%d from course id=%d",
                dtoCourseUser.getUserId(), dtoCourseUser.getCourseId()));

        model.addAttribute(new Course());
        model.addAttribute(DTO, dtoCourseUser);

        return REMOVAL_CONFIRM;
    }

    @Autowired
    public CourseUsersController(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }
}
