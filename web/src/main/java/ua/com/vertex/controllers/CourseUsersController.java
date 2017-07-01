package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.List;

@Controller
public class CourseUsersController {
    private final CourseLogic courseLogic;
    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(CourseUsersController.class);

    private static final String COURSE_USERS = "courseUsers";
    private static final String ASSIGNED_USERS = "assignedUsers";
    private static final String REMOVAL_CONFIRM = "courseUserRemovalConfirm";
    private static final String FREE_USERS = "freeUsers";
    private static final String SEARCH = "search";
    private static final String DTO = "dto";

    @GetMapping(value = "/showCourseAndUsers")
    public String showCourseAndUsersPage(@ModelAttribute Course course, Model model) {

        LOGGER.debug(logInfo.getId() + "Show users assigned to course id=" + course.getId());

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(course.getId());
        CourseUserDto dto = new CourseUserDto();
        dto.setCourseId(course.getId());

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(new User());
        model.addAttribute(DTO, dto);

        return COURSE_USERS;
    }

    @PostMapping(value = "/removeUserFromCourse")
    public String removeUserFromAssigned(@ModelAttribute CourseUserDto dto, Model model) {

        LOGGER.debug(logInfo.getId() + String.format("Remove user=%s from course id=%d",
                dto.getEmail(), dto.getCourseId()));

        courseLogic.removeUserFromCourse(dto);
        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(DTO, dto);

        return COURSE_USERS;
    }

    @PostMapping(value = "/assignUser")
    public String assignUserToCourse(@ModelAttribute CourseUserDto dto, Model model) {

        LOGGER.debug(logInfo.getId() + String.format("Assign user=%s to course id=%d",
                dto.getEmail(), dto.getCourseId()));

        courseLogic.assignUserToCourse(dto);
        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        List<User> freeUsers = courseLogic.searchForUsersToAssign(dto);

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(FREE_USERS, freeUsers);
        model.addAttribute(DTO, dto);
        model.addAttribute(SEARCH, true);

        return COURSE_USERS;
    }

    @GetMapping(value = "/searchForUsersToAssign")
    public String searchForUsersToAssign(@ModelAttribute CourseUserDto dto, Model model) {

        LOGGER.debug(logInfo.getId() + String.format("Search for users that can be assigned to course id=%d" +
                "by searchType=%s and searchParam=%s", dto.getCourseId(), dto.getSearchType(), dto.getSearchParam()));

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        List<User> freeUsers = courseLogic.searchForUsersToAssign(dto);

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(FREE_USERS, freeUsers);
        model.addAttribute(DTO, dto);
        model.addAttribute(SEARCH, true);

        return COURSE_USERS;
    }

    @GetMapping(value = "/clearSearchResults")
    public String clearSearchResults(@ModelAttribute CourseUserDto dto, Model model) {

        LOGGER.debug(logInfo + "Clear free users search results");

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(DTO, dto);

        return COURSE_USERS;
    }

    @PostMapping(value = "/confirmUserRemovalFromCourse")
    public String confirmUserRemovalFromCourse(@ModelAttribute CourseUserDto dto, Model model) {

        LOGGER.debug(logInfo.getId() + String.format("Confirm removing user=%s from course id=%d",
                dto.getEmail(), dto.getCourseId()));

        model.addAttribute(new Course());
        model.addAttribute(DTO, dto);

        return REMOVAL_CONFIRM;
    }

    @Autowired
    public CourseUsersController(CourseLogic courseLogic, LogInfo logInfo) {
        this.courseLogic = courseLogic;
        this.logInfo = logInfo;
    }
}
