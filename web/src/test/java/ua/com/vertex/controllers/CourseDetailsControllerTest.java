//package ua.com.vertex.controllers;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//import ua.com.vertex.logic.interfaces.CourseLogic;
//import ua.com.vertex.logic.interfaces.UserLogic;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static ua.com.vertex.controllers.CreateCertificateAndAddToUser.SELECT_USER_JSP;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class CourseDetailsControllerTest {
//
//    private final String MSG_INVALID_DATA = "Have wrong objects in model";
//    private final String MSG_INVALID_VIEW = "Have wrong viewName in ModelAndView";
//
//    private CourseDetailsController courseDetailsController;
//    private Model model;
//
//    @Mock
//    private CourseLogic courseLogic;
//
//    @Mock
//    UserLogic userLogic;
//
//    @Mock
//    private BindingResult bindingResult;
//
//    @Before
//    public void setUp() throws Exception {
//        courseDetailsController = new CourseDetailsController(courseLogic, userLogic);
//    }
//
//    @Test
//    public void searchCourseJspReturnCorrectView() throws Exception {
//        ModelAndView result = courseDetailsController.searchCourseJsp();
//        assertEquals(MSG_INVALID_VIEW, result.getViewName(), SELECT_USER_JSP);
//        assertTrue(MSG_INVALID_DATA,result.getModel().containsKey());
//    }
//
//}
