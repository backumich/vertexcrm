package ua.com.vertex.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class PdfDownloader {

    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(PdfDownloader.class);

    public int downloadPdf(String pdfFileName, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=certificate.pdf");
        Files.copy(Paths.get(pdfFileName), response.getOutputStream());
        response.getOutputStream().flush();

        LOGGER.debug(logInfo.getId() + pdfFileName + " file downloaded");

        return response.getStatus();
    }

    @Autowired
    public PdfDownloader(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
