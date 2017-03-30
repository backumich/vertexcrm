package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.utils.LogInfo;
import ua.com.vertex.utils.PdfDownloader;
import ua.com.vertex.utils.PdfGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Controller
@RequestMapping(value = "/generatePdf")
public class PdfController {

    private final PdfGenerator pdfGenerator;
    private final PdfDownloader pdfDownloader;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(PdfController.class);

    @PostMapping
    public void generatePdf(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String courseName, @RequestParam String certificationDate,
                            @RequestParam int certificationId, HttpServletResponse response) {

        LOGGER.debug(logInfo.getId() + "Generate pdf page accessed");
        String pdfFileName = logInfo.getEmail() + "_certificate.pdf";
        File pdfFile = new File(pdfFileName);

        try {
            pdfGenerator.generatePdf(pdfFileName, firstName, lastName, courseName, certificationDate, certificationId);
            pdfDownloader.downloadPdf(pdfFileName, response);
        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e);
        } finally {
            if (pdfFile.exists()) {
                pdfFile.delete();
            }
        }
    }

    @Autowired
    public PdfController(PdfGenerator pdfGenerator, PdfDownloader pdfDownloader, LogInfo logInfo) {
        this.pdfGenerator = pdfGenerator;
        this.pdfDownloader = pdfDownloader;
        this.logInfo = logInfo;
    }
}
