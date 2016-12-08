package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestMainContext;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestMainContext.class)
@ActiveProfiles("test")
public class CertificateDetailsPageControllerTestUnit {

    @Mock
    private CertDetailsPageLogic logic;

    @Mock
    private ImageStorage storage;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private HttpSession session;

    private CertificateDetailsPageController controller;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        session = new MockHttpSession();
        controller = new CertificateDetailsPageController(logic, storage);
    }

    @Test
    public void doGetInvokesRedirectInAfterRetrievingCertificateAndUser() throws IOException {
        Certificate certificate = mock(Certificate.class);

        when(request.getParameter("certificationId")).thenReturn("222");
        when(request.getSession()).thenReturn(session);
        when(logic.getCertificateDetails(222)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(22);
        when(logic.getUserDetails(222)).thenReturn(new User.Builder().getInstance());

        controller.doGet(request, response);
        verify(response).sendRedirect("/certificateDetails.jsp");
    }

    @Test
    public void doGetInvokesRedirectAfterCertificateException() throws IOException {
        when(request.getParameter("certificationId")).thenReturn("0");
        when(request.getSession()).thenReturn(session);
        when(logic.getCertificateDetails(0)).thenThrow(new EmptyResultDataAccessException("", 1));

        controller.doGet(request, response);
        verify(response).sendRedirect("/certificateDetails.jsp");
    }

    @Test
    public void doGetFillsOutCertificateAttributesAfterCertificateEmptyResultDataAccessException()
            throws IOException {
        when(request.getParameter("certificationId")).thenReturn("0");
        when(request.getSession()).thenReturn(session);
        when(logic.getCertificateDetails(0)).thenThrow(new EmptyResultDataAccessException("", 1));

        controller.doGet(request, response);
        assertEquals("Attribute may have been changed",
                session.getAttribute("certificateIsNull"), "No such certificate! Try again!");
    }

    @Test
    public void doGetFillsOutUserAttributesAfterUserEmptyResultDataAccessException() throws IOException {
        when(request.getParameter("certificationId")).thenReturn("500");
        when(request.getSession()).thenReturn(session);
        when(logic.getCertificateDetails(500)).thenReturn(new Certificate.Builder()
                .setCertificationId(500)
                .setCertificationDate(LocalDate.now())
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance());
        when(logic.getUserDetails(0)).thenThrow(new EmptyResultDataAccessException("", 1));

        controller.doGet(request, response);
        assertEquals("Attribute may have been changed",
                session.getAttribute("userIsNull"), "No holder is assigned to this certificate ID");
    }

    @Test
    public void doGetFillsOutSessionAttributes() throws IOException {
        Certificate certificate = mock(Certificate.class);

        when(request.getParameter("certificationId")).thenReturn("100");
        when(request.getSession()).thenReturn(session);
        when(logic.getCertificateDetails(100)).thenReturn(new Certificate.Builder()
                .setCertificationId(100)
                .setUserId(10)
                .setCertificationDate(LocalDate.now())
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance());
        when(certificate.getUserId()).thenReturn(10);
        when(logic.getUserDetails(10)).thenReturn(new User.Builder()
                .setUserId(10)
                .setFirstName("John")
                .setLastName("Smith")
                .getInstance());

        controller.doGet(request, response);

        assertNull(session.getAttribute("certificateIsNull"));
        assertEquals(session.getAttribute("certificationId"), "Certification ID: 00100");
        assertEquals(session.getAttribute("userId"), 10);
        assertEquals(session.getAttribute("userFirstName"), "User First Name: John");
        assertEquals(session.getAttribute("userLastName"), "User Last Name: Smith");
        assertEquals(session.getAttribute("userPhoto"), "");
        assertEquals(session.getAttribute("certificationDate"), "Certification Date: " + LocalDate.now());
        assertEquals(session.getAttribute("courseName"), "Course Name: Java Professional");
        assertEquals(session.getAttribute("language"), "Programming Language: Java");
    }

    @Test
    public void showUserPhotoRetrievesByteArray() throws IOException {
        ServletOutputStream stream = mock(ServletOutputStream.class);
        byte[] data = {(byte) 1};

        when(storage.getImageData()).thenReturn(data);
        when(response.getOutputStream()).thenReturn(stream);

        controller.showUserPhoto(response);
        verify(stream).write(data);
    }


}
