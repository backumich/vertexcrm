package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static ua.com.vertex.controllers.AdminController.*;


public class AdminControllerTest {

    private AdminController underTest;

    @Before
    public void setUp() {
        underTest = new AdminController();
    }

    @Test
    public void adminHasCorrectDataInModel() throws Exception {
        ModelAndView result = underTest.admin();
        assertEquals("Have wrong viewName in ModelAndView", result.getViewName(), ADMIN_JSP);
    }
}