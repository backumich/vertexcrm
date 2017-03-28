package ua.com.vertex.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class PdfDownloader {

    public int downloadPdf(String pdfFileName, HttpServletResponse response) throws IOException {

        File pdfToDownload = new File(pdfFileName);
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=certificate.pdf");
        Files.copy(Paths.get(pdfToDownload.getPath()), response.getOutputStream());
        response.getOutputStream().flush();

        return response.getStatus();
    }
}
