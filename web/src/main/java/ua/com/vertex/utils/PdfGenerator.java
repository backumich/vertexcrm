package ua.com.vertex.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class PdfGenerator {

    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(PdfGenerator.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US);

    public void generatePdf(String firstName, String lastName, String courseName, String certificationDate,
                            int certificationId) {

        Document document = new Document();

        try (FileOutputStream outputStream = new FileOutputStream("certificate.pdf")) {

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Rectangle dimensions = new Rectangle(1120, 530);
            document.setPageSize(dimensions);
            document.open();

            PdfContentByte canvas = writer.getDirectContentUnder();
            ClassPathResource imageToSet = new ClassPathResource("certificate.png");
            Image image = Image.getInstance(imageToSet.getURL());
            image.scaleAbsolute(dimensions);
            image.setAbsolutePosition(0, 0);
            canvas.addImage(image);

            String fullName = firstName + " " + lastName;
            String date = formatter.format(LocalDate.parse(certificationDate));

            setText(writer, fullName, BaseFont.TIMES_BOLDITALIC, 35, 560, 270);
            setText(writer, courseName, BaseFont.TIMES_ROMAN, 32, 560, 185);
            setText(writer, date, BaseFont.TIMES_BOLD, 16, 715, 115);
            setText(writer, String.format("%05d", certificationId), BaseFont.TIMES_BOLD, 16, 310, 115);

            document.close();

        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e);
        }
    }

    private void setText(PdfWriter writer, String parameter, String baseFont, int fontSize, int shiftX, int shiftY)
            throws IOException, DocumentException {

        BaseFont bf = BaseFont.createFont(baseFont, BaseFont.WINANSI, BaseFont.EMBEDDED);
        PdfContentByte cb = writer.getDirectContent();

        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, fontSize);
        cb.showTextAligned(Element.ALIGN_CENTER, parameter, shiftX, shiftY, 0);
        cb.endText();
        cb.restoreState();
    }

    @Autowired
    public PdfGenerator(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}