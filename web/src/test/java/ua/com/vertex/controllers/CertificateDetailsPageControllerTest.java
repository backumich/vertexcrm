package ua.com.vertex.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainContext;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainContext.class)
public class CertificateDetailsPageControllerTest {

    @Autowired
    private CertDetailsPageLogic logic;

    @Test
    public void logicShouldNotBeNull() {
        assertNotNull(logic);
    }

    @Test
    public void doGetInvokesLogic() {
        CertificateDetailsPageController controller = new CertificateDetailsPageController(logic);
        HttpServletRequest request = createMock(HttpServletRequest.class);
        HttpServletResponse response = new MockHttpServletResponse();

        expect(request.getParameter("certificationId")).andReturn("1").times(1);
        expect(request.getSession()).andReturn(new MockHttpSession()).times(1);
        replay(request);

        controller.doGet(request, response);
    }
}
