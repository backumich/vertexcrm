package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceView;
import ua.com.vertex.beans.Certificate;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@ActiveProfiles("test")
public class CertificateDetailsPageControllerTestFunctional {

    @Mock
    private CertDetailsPageLogic logic;

    @Mock
    private ImageStorage storage;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Model model;

    private CertificateDetailsPageController controller;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CertificateDetailsPageController(logic, storage);
    }

    @Test
    public void webMvcShouldReturnCorrectView() throws Exception {
        Certificate certificate = mock(Certificate.class);
        User user = mock(User.class);

        when(logic.getCertificateDetails(222)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(22);
        when(logic.getUserDetails(22)).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("certificateDetails"))
                .build();
        mockMvc.perform(get("/showCertificateDetails")
                .param("certificationId", "222"))
                .andExpect(view().name("certificateDetails"));
    }

    @Test
    public void doGetFillsOutModelAttributesAfterRetrievingCertificateAndUser() {
        Certificate certificate = mock(Certificate.class);
        User user = mock(User.class);

        when(logic.getCertificateDetails(222)).thenReturn(certificate);
        when(certificate.getUserId()).thenReturn(22);
        when(logic.getUserDetails(22)).thenReturn(user);

        controller.showCertificateDetails("222", model);
        verify(model).addAttribute("certificate", certificate);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("result", "result");
    }

    @Test
    public void doGetFillsOutModelAttributesAfterNegativeValueRequestedId() {
        when(logic.getCertificateDetails(-1)).thenThrow(new NumberFormatException());

        controller.showCertificateDetails("-1", model);
        verify(model).addAttribute("certificateIsNull", "No such certificate! Try again!");
    }

    @Test
    public void doGetFillsOutModelAttributesAfterRequestedIdNumberFormatException() {
        controller.showCertificateDetails("", model);
        verify(model).addAttribute("certificateIsNull", "No such certificate! Try again!");
    }

    @Test
    public void doGetFillsOutModelAttributesAfterCertificateException() {
        when(logic.getCertificateDetails(0)).thenThrow(new EmptyResultDataAccessException("", 1));

        controller.showCertificateDetails("0", model);
        verify(model).addAttribute("certificateIsNull", "No such certificate! Try again!");
    }

    @Test
    public void doGetFillsOutModelAttributesAfterUserException() {
        when(logic.getCertificateDetails(500)).thenReturn(new Certificate.Builder()
                .setCertificationId(500)
                .setCertificationDate(LocalDate.now())
                .setCourseName("Java Professional")
                .setLanguage("Java")
                .getInstance());
        when(logic.getUserDetails(0)).thenThrow(new EmptyResultDataAccessException("", 1));

        controller.showCertificateDetails("500", model);
        model.addAttribute("userIsNull", "No holder is assigned to this certificate ID");
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
