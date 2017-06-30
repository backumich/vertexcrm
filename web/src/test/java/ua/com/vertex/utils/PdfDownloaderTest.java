package ua.com.vertex.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RunWith(MockitoJUnitRunner.class)
public class PdfDownloaderTest {

    @Mock
    private LogInfo logInfo;

    @Spy
    private final HttpServletResponse response = new MockHttpServletResponse();

    private PdfDownloader pdfDownloader;
    private static final String PDF_FILE_NAME = "PdfDownloaderTest.pdf";
    private final File file = new File(PDF_FILE_NAME);

    @Before
    public void setUp() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        pdfDownloader = new PdfDownloader(logInfo);
    }

    @After
    public void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void downloadPdfReturnsHttpStatusOk() throws IOException {
        ServletOutputStream stream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(stream);

        int status = pdfDownloader.downloadPdf(PDF_FILE_NAME, response);

        verify(response).setContentType("application/pdf");
        verify(response).addHeader("Content-Disposition", "attachment; filename=certificate.pdf");
        verify(stream).flush();
        assertEquals(200, status);
    }
}
