package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.utils.LogInfo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CourseUsersControllerTest1 {

    @Mock
    private CourseLogic courseLogic;

    @Mock
    private LogInfo logInfo;

    private CourseUsersController controller;

    private static final String COURSE_USERS = "courseUsers";
    private static final String REMOVAL_CONFIRM = "courseUserRemovalConfirm";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new CourseUsersController(courseLogic, logInfo);
    }

    @Test
    public void showCourseAndUsersPageReturnsCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();
        mockMvc.perform(get("/showCourseAndUsers"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void removeUserFromAssignedReturnsCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();
        mockMvc.perform(post("/removeUserFromCourse"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void assignUserToCourseReturnsCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();
        mockMvc.perform(post("/assignUser"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void searchForUsersToAssignReturnsCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();
        mockMvc.perform(get("/searchForUsersToAssign"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void clearSearchResultsReturnsCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();
        mockMvc.perform(get("/clearSearchResults"))
                .andExpect(view().name(COURSE_USERS));
    }

    @Test
    public void confirmUserRemovalFromCourseReturnsCorrectView() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView(COURSE_USERS))
                .build();
        mockMvc.perform(post("/confirmUserRemovalFromCourse"))
                .andExpect(view().name(REMOVAL_CONFIRM));
    }
}