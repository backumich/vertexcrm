package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class CourseUsersControllerTest {
    private static final String COURSE_USERS = "courseUsers";
    private static final String REMOVAL_CONFIRM = "courseUserRemovalConfirm";
    private static final String ASSIGNED_USERS = "assignedUsers";
    private static final String FREE_USERS = "freeUsers";
    private static final String SEARCH = "search";
    private static final String SEARCH_TYPE_FIRST_NAME = "First Name";
    private static final String SEARCH_TYPE_LAST_NAME = "Last Name";
    private static final String SEARCH_TYPE_EMAIL = "Email";
    private static final String DTO = "dtoCourseUser";
    private static final int COURSE_ID = 1;

    @Mock
    private CourseLogic courseLogicMocked;

    @Autowired
    private CourseLogic courseLogic;

    @Mock
    private Model model;
    private MockMvc mockMvc;
    private CourseUsersController controller;
    private User user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12;
    private DtoCourseUser dto;
    private List<User> assignedUsers;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(new CourseUsersController(courseLogicMocked))
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();

        controller = new CourseUsersController(courseLogic);
        user1 = new User.Builder().setUserId(401).setEmail("user1@email.com")
                .setFirstName("Name1").setLastName("Surname1").setPhone("+38050 111 1111").getInstance();
        user2 = new User.Builder().setUserId(402).setEmail("user2@email.com")
                .setFirstName("Name2").setLastName("Surname2").setPhone("+38050 222 2222").getInstance();
        user3 = new User.Builder().setUserId(403).setEmail("user3@email.com")
                .setFirstName("Name3").setLastName("Surname3").setPhone("+38050 333 3333").getInstance();
        user4 = new User.Builder().setUserId(1).setEmail("email1").setFirstName("FirstName").setLastName("LastName")
                .setPhone("38066 000 00 00").getInstance();
        user5 = new User.Builder().setUserId(2).setEmail("email1@test.com").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user6 = new User.Builder().setUserId(22).setEmail("22@test.com").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user7 = new User.Builder().setUserId(33).setEmail("33@test.com").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user8 = new User.Builder().setUserId(34).setEmail("34@test.com").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user9 = new User.Builder().setUserId(44).setEmail("44@test.com").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user10 = new User.Builder().setUserId(501).setEmail("forBruteTest_1").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user11 = new User.Builder().setUserId(502).setEmail("forBruteTest_2").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        user12 = new User.Builder().setUserId(503).setEmail("forBruteTest_3").setFirstName("FirstName")
                .setLastName("LastName").setPhone("38066 000 00 00").getInstance();
        dto = new DtoCourseUser();
    }

    @Test
    public void showCourseAndUsersPageReturnsCorrectView() throws Exception {
        mockMvc.perform(get("/showCourseAndUsers"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void removeUserFromAssignedReturnsCorrectView() throws Exception {
        mockMvc.perform(post("/removeUserFromCourse"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void assignUserToCourseReturnsCorrectView() throws Exception {
        mockMvc.perform(post("/assignUser"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void searchForUsersToAssignReturnsCorrectView() throws Exception {
        mockMvc.perform(get("/searchForUsersToAssign"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void clearSearchResultsReturnsCorrectView() throws Exception {
        mockMvc.perform(get("/clearSearchResults"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void confirmUserRemovalFromCourseReturnsCorrectView() throws Exception {
        mockMvc.perform(post("/confirmUserRemovalFromCourse"))
                .andExpect(view().name(REMOVAL_CONFIRM));
    }

    @Test
    @WithAnonymousUser
    public void showCourseAndUsersPageFillsModelAttributes() {
        Course course = new Course.Builder().setId(COURSE_ID).getInstance();
        assignedUsers = Arrays.asList(user1, user2);
        dto.setCourseId(course.getId());

        controller.showCourseAndUsersPage(course, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(new User());
        verify(model, times(1)).addAttribute(DTO, dto);
    }

    @Test
    @WithAnonymousUser
    public void removeUserFromAssignedFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        assignedUsers = Arrays.asList(user1, user2);

        controller.removeUserFromAssigned(dto, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
    }

    @Test
    @WithAnonymousUser
    public void assignUserToCourseFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setUserId(user3.getUserId());
        dto.setSearchType(SEARCH_TYPE_FIRST_NAME);
        assignedUsers = Arrays.asList(user1, user2, user3);

        controller.assignUserToCourse(dto, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(FREE_USERS, new ArrayList<>());
        verify(model, times(1)).addAttribute(DTO, dto);
        verify(model, times(1)).addAttribute(SEARCH, true);
    }

    @Test
    @WithAnonymousUser
    public void searchForUsersByFirstNameToAssignFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setSearchType(SEARCH_TYPE_FIRST_NAME);
        dto.setSearchParam("FirstName");
        assignedUsers = Arrays.asList(user1, user2);
        List<User> freeUsers = Arrays.asList(user4, user5, user6, user7, user8, user9, user10, user11, user12);

        controller.searchForUsersToAssign(dto, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(FREE_USERS, freeUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
        verify(model, times(1)).addAttribute(SEARCH, true);
    }

    @Test
    @WithAnonymousUser
    public void searchForUsersByLastNameToAssignFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setSearchType(SEARCH_TYPE_LAST_NAME);
        dto.setSearchParam("LastName");
        assignedUsers = Arrays.asList(user1, user2);
        List<User> freeUsers = Arrays.asList(user4, user5, user6, user7, user8, user9, user10, user11, user12);

        controller.searchForUsersToAssign(dto, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(FREE_USERS, freeUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
        verify(model, times(1)).addAttribute(SEARCH, true);
    }

    @Test
    @WithAnonymousUser
    public void searchForUsersByEmailToAssignFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        dto.setSearchType(SEARCH_TYPE_EMAIL);
        dto.setSearchParam("44@test.com");
        assignedUsers = Arrays.asList(user1, user2);
        List<User> freeUsers = Arrays.asList(user9);

        controller.searchForUsersToAssign(dto, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(FREE_USERS, freeUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
        verify(model, times(1)).addAttribute(SEARCH, true);
    }

    @Test
    @WithAnonymousUser
    public void clearSearchResultsFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);
        assignedUsers = Arrays.asList(user1, user2);

        controller.clearSearchResults(dto, model);

        verify(model, times(1)).addAttribute(ASSIGNED_USERS, assignedUsers);
        verify(model, times(1)).addAttribute(DTO, dto);
    }

    @Test
    @WithAnonymousUser
    public void confirmUserRemovalFromCourseFillsModelAttributes() {
        dto.setCourseId(COURSE_ID);

        controller.confirmUserRemovalFromCourse(dto, model);

        verify(model, times(1)).addAttribute(new Course());
        verify(model, times(1)).addAttribute(DTO, dto);
    }
}