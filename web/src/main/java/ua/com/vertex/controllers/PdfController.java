package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.utils.LogInfo;
import ua.com.vertex.utils.PdfGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping(value = "/generatePdf")
public class PdfController {

    private final PdfGenerator pdfGenerator;
    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(PdfController.class);

    @PostMapping
    public void generatePdf(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String courseName, @RequestParam String certificationDate,
                            @RequestParam int certificationId, HttpServletResponse response) throws IOException {

        pdfGenerator.generatePdf(firstName, lastName, courseName, certificationDate, certificationId);

        try {
            File pdfToDownload = new File("certificate.pdf");
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=Certificate.pdf");
            Files.copy(Paths.get(pdfToDownload.getPath()), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            LOGGER.warn(logInfo.getId(), e);
        }
    }

    @Autowired
    public PdfController(PdfGenerator pdfGenerator, LogInfo logInfo) {
        this.pdfGenerator = pdfGenerator;
        this.logInfo = logInfo;
    }
}
