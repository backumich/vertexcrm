package ua.com.vertex.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDTO;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.List;

@Controller
public class CourseUsersController {
    private final CourseLogic courseLogic;

    private static final String COURSE_USERS = "courseUsers";
    private static final String ASSIGNED_USERS = "assignedUsers";
    private static final String REMOVAL_CONFIRM = "courseUserRemovalConfirm";
    private static final String FREE_USERS = "freeUsers";
    private static final String SEARCH = "search";
    private static final String DTO = "dto";

    @RequestMapping(value = "/showCourseAndUsers")
    public String showCourseAndUsersPage(@ModelAttribute Course course, Model model) {

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(course.getId());
        CourseUserDTO dto = new CourseUserDTO();
        dto.setCourseId(course.getId());

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(new User());
        model.addAttribute(DTO, dto);

        return COURSE_USERS;
    }

    @PostMapping(value = "/removeUserFromCourse")
    public String removeUserFromAssigned(@ModelAttribute CourseUserDTO dto, Model model) {

        courseLogic.removeUserFromCourse(dto);
        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(DTO, dto);

        return COURSE_USERS;
    }

    @PostMapping(value = "/assignUser")
    public String assignUserToCourse(@ModelAttribute CourseUserDTO dto, Model model) {

        courseLogic.assignUserToCourse(dto);
        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        List<User> freeUsers = courseLogic.searchUsersToAssign(dto);

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(FREE_USERS, freeUsers);
        model.addAttribute(DTO, dto);
        model.addAttribute(SEARCH, true);

        return COURSE_USERS;
    }

    @PostMapping(value = "/searchUsersToAssign")
    public String searchUsersToAssign(@ModelAttribute CourseUserDTO dto, Model model) {

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        List<User> freeUsers = courseLogic.searchUsersToAssign(dto);

        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(FREE_USERS, freeUsers);
        model.addAttribute(DTO, dto);
        model.addAttribute(SEARCH, true);

        return COURSE_USERS;
    }

    @GetMapping(value = "/clearSearchResults")
    public String clearSearchResults(@ModelAttribute CourseUserDTO dto, Model model) {

        List<User> assignedUsers = courseLogic.getUsersAssignedToCourse(dto.getCourseId());
        model.addAttribute(ASSIGNED_USERS, assignedUsers);
        model.addAttribute(DTO, dto);

        return COURSE_USERS;
    }

    @PostMapping(value = "/confirmUserRemovalFromCourse")
    public String confirmUserRemovalFromCourse(@ModelAttribute CourseUserDTO dto, Model model) {

        model.addAttribute(new Course());
        model.addAttribute(DTO, dto);

        return REMOVAL_CONFIRM;
    }

    @Autowired
    public CourseUsersController(CourseLogic courseLogic) {
        this.courseLogic = courseLogic;
    }
}
