package ua.com.vertex.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.utils.LogInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping(value = "/generatePdf")
public class PdfController {

    private final LogInfo logInfo;

    private static final Logger LOGGER = LogManager.getLogger(PdfController.class);

    @PostMapping
    public void generatePdf(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String courseName, @RequestParam String certificationDate,
                            HttpServletResponse response) throws IOException {

        try {
            File file = new File(createPdf(firstName, lastName, courseName, certificationDate).toString());

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=Certificate.pdf");
            Files.copy(Paths.get(file.getPath()), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            LOGGER.warn(logInfo.getId(), e);
        }
    }

    private Path createPdf(String firstName, String lastName, String courseName, String certificationDate) {
        ClassPathResource file = new ClassPathResource("");

        // todo

        return Paths.get("");
    }

    @Autowired
    public PdfController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
