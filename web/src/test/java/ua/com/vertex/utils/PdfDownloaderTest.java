package ua.com.vertex.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class PdfDownloaderTest {

    private PdfDownloader pdfDownloader;
    private static final String PDF_FILE_NAME = "PdfDownloaderTest.pdf";
    private File file = new File(PDF_FILE_NAME);

    @Before
    public void setUpClass() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        pdfDownloader = new PdfDownloader();
    }

    @After
    public void tearDownClass() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void downloadPdfReturnsHttpStatusOk() throws IOException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        int status = pdfDownloader.downloadPdf(PDF_FILE_NAME, response);

        assertEquals(200, status);
    }
}
