package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.vertex.beans.PdfDto;
import ua.com.vertex.utils.LogInfo;
import ua.com.vertex.utils.PdfDownloader;
import ua.com.vertex.utils.PdfGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
public class PdfController {
    private static final Logger LOGGER = LogManager.getLogger(PdfController.class);
    private final PdfGenerator pdfGenerator;
    private final PdfDownloader pdfDownloader;
    private final LogInfo logInfo;

    @PostMapping(value = "/generatePdf")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void generatePdf(@ModelAttribute PdfDto dto, HttpServletResponse response) throws Exception {

        LOGGER.debug(logInfo.getId() + "GeneratePdf page accessed");
        String pdfFileName = logInfo.getEmail() + "_certificate.pdf";
        File pdfFile = new File(pdfFileName);

        try {
            pdfGenerator.generatePdf(pdfFileName, dto);
            pdfDownloader.downloadPdf(pdfFileName, response);
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
