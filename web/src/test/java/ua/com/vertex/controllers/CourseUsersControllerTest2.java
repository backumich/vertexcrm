package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDTO;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.LogInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class CourseUsersControllerTest2 {

    @Autowired
    private CourseLogic courseLogic;

    @Mock
    private LogInfo logInfo;

    @Mock
    private Model model;

    private CourseUsersController controller;
    private User user1;
    private User user2;
    private User user3;
    private CourseUserDTO dto;
    private List<User> assignedUsers;

    private static final String ASSIGNED_USERS = "assignedUsers";
    private static final String FREE_USERS = "freeUsers";
    private static final String SEARCH = "search";
    private static final String DTO = "dto";
    private static final int COURSE_ID = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new CourseUsersController(courseLogic, logInfo);
        user1 = new User.Builder()
                .setEmail("user1@email.com")
                .setFirstName("Name1")
                .setLastName("Surname1")
                .setPhone("+38050 111 1111")
                .getInstance();
        user2 = new User.Builder()
                .setEmail("user2@email.com")
                .setFirstName("Name2")
                .setLastName("Surname2")
                .setPhone("+38050 222 2222")
                .getInstance();
        user3 = new User.Builder()
                .setEmail("user3@email.com")
                .setFirstName("Name3")
                .setLastName("Surname3")
                .setPhone("+38050 333 3333")
                .getInstance();
        dto = new CourseUserDTO();
    }

    @Test
    @WithAnonymousUser
    public void showCourseAndUsersPageFillsModelAttributes() {
        Course course = new Course.Builder().setId(COURSE_ID).getInstance();
        assignedUsers = Arrays.asList(user1, user2);
        dto.setCourseId(course.getId());

        String view = controller.showCourseAndUsersPage(course, model);
        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(new User());
        verify(model, times(1)).addAttribute(DTO, dto);
    }

    @Test
    @WithAnonymousUser
    public void removeUserFromAssignedFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setEmail(user2.getEmail());
        assignedUsers = Arrays.asList(user1);

        String view = controller.removeUserFromAssigned(dto, model);
        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
    }

    @Test
    @WithAnonymousUser
    public void assignUserToCourseFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setEmail(user3.getEmail());
        dto.setFirstName(user3.getFirstName());
        dto.setLastName(user3.getLastName());
        dto.setPhone(user3.getPhone());
        dto.setSearchType("first_name");
        assignedUsers = Arrays.asList(user1, user2, user3);

        String view = controller.assignUserToCourse(dto, model);
        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(FREE_USERS, new ArrayList<>());
        verify(model, times(1)).addAttribute(DTO, dto);
        verify(model, times(1)).addAttribute(SEARCH, true);
    }

    @Test
    @WithAnonymousUser
    public void searchForUsersToAssignFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setSearchType("first_name");
        dto.setSearchParam("");
        User u1, u2, u3, u4, u5, u6;
        u1 = new User.Builder().setEmail("email1").setFirstName("FirstName").setLastName("LastName")
                .setPhone("38066 000 00 00").getInstance();
        u2 = new User.Builder().setEmail("emailTest").setFirstName("first_name").setLastName("last_name")
                .setPhone("666666666").getInstance();
        u3 = new User.Builder().setEmail("22@test.com").setFirstName("FirstName").setLastName("LastName")
                .setPhone("38066 000 00 00").getInstance();
        u4 = new User.Builder().setEmail("33@test.com").setFirstName("FirstName").setLastName("LastName")
                .setPhone("38066 000 00 00").getInstance();
        u5 = new User.Builder().setEmail("34@test.com").setFirstName("FirstName").setLastName("LastName")
                .setPhone("38066 000 00 00").getInstance();
        u6 = new User.Builder().setEmail("44@test.com").setFirstName("FirstName").setLastName("LastName")
                .setPhone("38066 000 00 00").getInstance();

        assignedUsers = Arrays.asList(user1, user2);
        List<User> freeUsers = Arrays.asList(u1, u2, u3, u4, u5, u6);

        String view = controller.searchForUsersToAssign(dto, model);
        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model).addAttribute(FREE_USERS, freeUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
        verify(model, times(1)).addAttribute(SEARCH, true);
    }

    @Test
    @WithAnonymousUser
    public void clearSearchResultsFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        assignedUsers = Arrays.asList(user1, user2);

        String view = controller.clearSearchResults(dto, model);
        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
    }

    @Test
    @WithAnonymousUser
    public void confirmUserRemovalFromCourseFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);

        String view = controller.confirmUserRemovalFromCourse(dto, model);
        verify(model, times(1)).addAttribute(new Course());
        verify(model, times(1)).addAttribute(DTO, dto);
    }
}
