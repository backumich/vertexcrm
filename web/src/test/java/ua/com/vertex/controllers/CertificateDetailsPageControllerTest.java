package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.TestContext;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class)
public class CertificateDetailsPageControllerTest {

    @Autowired
    private CertDetailsPageLogic logic;

    private CertificateDetailsPageController controller;

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private HttpSession session = mock(HttpSession.class);

    @Test
    public void logicShouldNotBeNull() {
        assertNotNull(logic);
    }

    @Before
    public void setUp() {
        controller = new CertificateDetailsPageController(logic);
    }

    @Test
    public void doGetInvokesLogic() {
        when(request.getParameter("certificationId")).thenReturn("1");
        when(request.getSession()).thenReturn(new MockHttpSession());

        controller.doGet(request, response);
    }

    @Test
    public void showUserPhotoInvokesLogic() throws IOException {
        ServletOutputStream stream = mock(ServletOutputStream.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn("22");
        when(response.getOutputStream()).thenReturn(stream);

        controller.showUserPhoto(request, response);
    }
}
