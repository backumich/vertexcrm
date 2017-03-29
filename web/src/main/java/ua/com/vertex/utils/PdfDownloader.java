package ua.com.vertex.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class PdfDownloader {

    public int downloadPdf(String pdfFileName, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=certificate.pdf");
        Files.copy(Paths.get(pdfFileName), response.getOutputStream());
        response.getOutputStream().flush();

        return response.getStatus();
    }
}
