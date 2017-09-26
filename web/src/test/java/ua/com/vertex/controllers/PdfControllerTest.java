package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import ua.com.vertex.beans.PdfDto;
import ua.com.vertex.utils.EmailExtractor;
import ua.com.vertex.utils.PdfDownloader;
import ua.com.vertex.utils.PdfGenerator;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PdfControllerTest {
    private static final String PDF_FILE_NAME = "null_certificate.pdf";

    @Mock
    private PdfGenerator pdfGenerator;

    @Mock
    private PdfDownloader pdfDownloader;

    @Mock
    private EmailExtractor emailExtractor;
    private PdfController pdfController;

    @Before
    public void setUp() {
        pdfController = new PdfController(pdfGenerator, pdfDownloader, emailExtractor);
    }

    @Test
    public void generatePdfInvokesPdfGenerator() throws Exception {
        final String email = "";
        final String firstName = "";
        final String lastName = "";
        final String courseName = "";
        final String certificationDate = "";
        final String certificationId = "";

        MockHttpServletResponse response = new MockHttpServletResponse();
        PdfDto dto = new PdfDto(email, firstName, lastName, courseName, certificationDate, certificationId);
        when(emailExtractor.getEmailFromAuthentication()).thenReturn(null);

        pdfController.generatePdf(dto, response);

        verify(pdfGenerator, times(1)).generatePdf(PDF_FILE_NAME, dto);
        verify(pdfDownloader, times(1)).downloadPdf(PDF_FILE_NAME, response);
    }
}
