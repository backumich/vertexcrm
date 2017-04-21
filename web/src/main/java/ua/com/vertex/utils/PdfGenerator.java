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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.US);
    private static final int LENGTH = 16;

    public void generatePdf(String pdfFileName, String firstName, String lastName, String courseName,
                            String certificationDate, String certificateUid) {

        Document document = new Document();

        try (FileOutputStream outputStream = new FileOutputStream(pdfFileName)) {

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Rectangle dimensions = new Rectangle(1120, 530);
            document.setPageSize(dimensions);

            document.open();
            setCanvas(writer, dimensions);
            setText(writer, firstName, lastName, courseName, certificationDate, certificateUid);
            document.close();

            LOGGER.debug(logInfo.getId() + pdfFileName + " file generated");

        } catch (Exception e) {
            LOGGER.warn(logInfo.getId(), e);
        }
    }

    private void setCanvas(PdfWriter writer, Rectangle dimensions) throws IOException, DocumentException {

        PdfContentByte canvas = writer.getDirectContentUnder();
        ClassPathResource imageToSet = new ClassPathResource("certificate.png");
        Image image = Image.getInstance(imageToSet.getURL());

        image.scaleAbsolute(dimensions);
        image.setAbsolutePosition(0, 0);
        canvas.addImage(image);
    }

    private void setText(PdfWriter writer, String firstName, String lastName, String courseName,
                         String certificationDate, String certificateUid) throws IOException, DocumentException {

        String fullName = firstName + " " + lastName;
        String date = formatter.format(LocalDate.parse(certificationDate));
        certificateUid = prepareCertificateUid(certificateUid);

        setTextRow(writer, "Certificate of Achievement", BaseFont.TIMES_BOLD, 44, 560, 350);
        setTextRow(writer, "This certificate acknowledges that", BaseFont.TIMES_ROMAN, 18, 560, 315);
        setTextRow(writer, fullName, BaseFont.TIMES_BOLDITALIC, 36, 560, 270);
        setTextRow(writer, "has successfully completed the course", BaseFont.TIMES_ROMAN, 18, 560, 230);
        setTextRow(writer, courseName, BaseFont.TIMES_BOLD, 32, 560, 185);
        setTextRow(writer, "Certificate was issued by vertex-academy.com", BaseFont.TIMES_ROMAN, 18, 280, 140);
        setTextRow(writer, "Director General: Piskokha M. A.", BaseFont.TIMES_ROMAN, 18, 850, 140);
        setTextRow(writer, "Certificate UID: " + certificateUid, BaseFont.TIMES_ROMAN, 18, 280, 115);
        setTextRow(writer, date, BaseFont.TIMES_ROMAN, 18, 850, 115);
    }

    private void setTextRow(PdfWriter writer, String parameter, String font, int fontSize, int shiftX, int shiftY)
            throws IOException, DocumentException {

        BaseFont baseFont = BaseFont.createFont(font, BaseFont.WINANSI, BaseFont.EMBEDDED);
        PdfContentByte cb = writer.getDirectContent();

        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(baseFont, fontSize);
        cb.showTextAligned(Element.ALIGN_CENTER, parameter, shiftX, shiftY, 0);
        cb.endText();
        cb.restoreState();
    }

    private String prepareCertificateUid(String certificateUid) {
        if (certificateUid.length() != LENGTH) {
            throw new IllegalArgumentException("ID length must be " + LENGTH + " symbols");
        }

        char[] chars = certificateUid.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i <= LENGTH; i++) {
            builder.append(chars[i - 1]);
            if (i % 4 == 0) {
                builder.append("-");
            }
        }

        System.out.println(builder.toString());

        return builder.toString().substring(0, builder.length() - 1);
    }

    @Autowired
    public PdfGenerator(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
