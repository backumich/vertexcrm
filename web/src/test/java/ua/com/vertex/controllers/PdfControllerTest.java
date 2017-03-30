package ua.com.vertex.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import ua.com.vertex.utils.LogInfo;
import ua.com.vertex.utils.PdfDownloader;
import ua.com.vertex.utils.PdfGenerator;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PdfControllerTest {

    @Mock
    private LogInfo logInfo;

    @Mock
    private PdfGenerator pdfGenerator;

    @Mock
    private PdfDownloader pdfDownloader;

    private PdfController pdfController;
    private static final String PDF_FILE_NAME = "null_certificate.pdf";

    @Before
    public void setUp() {
        pdfController = new PdfController(pdfGenerator, pdfDownloader, logInfo);
    }

    @Test
    public void generatePdfInvokesPdfGenerator() throws IOException {
        final String firstName = "";
        final String lastName = "";
        final String courseName = "";
        final String certificationDate = "";
        final int certificationId = 0;

        MockHttpServletResponse response = new MockHttpServletResponse();
        pdfController.generatePdf(firstName, lastName, courseName, certificationDate, certificationId, response);

        verify(pdfGenerator, times(1)).generatePdf(PDF_FILE_NAME, firstName, lastName, courseName, certificationDate,
                certificationId);
        verify(pdfDownloader, times(1)).downloadPdf(PDF_FILE_NAME, response);
    }
}
