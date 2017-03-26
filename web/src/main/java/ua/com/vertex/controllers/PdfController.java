package ua.com.vertex.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vertex.utils.LogInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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

        // todo: to finish

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Document document = new Document(PageSize.A6.rotate());

        try (InputStream inputStream = classLoader.getResourceAsStream("certificate.docx");
             FileOutputStream outputStream = new FileOutputStream("certificate.pdf")) {

            XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(inputStream));

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Rectangle dimensions = new Rectangle(1120, 530);
            document.setPageSize(dimensions);
            document.open();
            String fileData = extractor.getText();

            PdfContentByte canvas = writer.getDirectContentUnder();
            ClassPathResource imageToSet = new ClassPathResource("sky.jpg");
            Image image = Image.getInstance(imageToSet.getURL());
            image.scaleAbsolute(dimensions);
            image.setAbsolutePosition(0, 0);
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.6f);
            canvas.setGState(state);
            canvas.addImage(image);

            Paragraph text = new Paragraph(fileData);
            text.setAlignment(Element.ALIGN_CENTER);
            document.add(text);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }

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
    public PdfController(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
