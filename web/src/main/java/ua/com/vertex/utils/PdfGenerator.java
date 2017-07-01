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
import ua.com.vertex.beans.PdfDto;

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

    public void generatePdf(String pdfFileName, PdfDto dto) throws Exception {

        Document document = new Document();

        try (FileOutputStream outputStream = new FileOutputStream(pdfFileName)) {

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            Rectangle dimensions = new Rectangle(1120, 530);
            document.setPageSize(dimensions);

            document.open();
            setCanvas(writer, dimensions);
            setText(writer, dto);
            document.close();

            LOGGER.debug(logInfo.getId() + pdfFileName + " file generated");
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

    private void setText(PdfWriter writer, PdfDto dto) throws IOException, DocumentException {

        String fullName = dto.getFirstName() + " " + dto.getLastName();
        String date = formatter.format(LocalDate.parse(dto.getCertificationDate()));

        setTextRow(writer, "Certificate of Achievement", BaseFont.TIMES_BOLD, 44, 560, 350);
        setTextRow(writer, "This certificate acknowledges that", BaseFont.TIMES_ROMAN, 18, 560, 315);
        setTextRow(writer, fullName, BaseFont.TIMES_BOLDITALIC, 36, 560, 270);
        setTextRow(writer, "has successfully completed the course", BaseFont.TIMES_ROMAN, 18, 560, 230);
        setTextRow(writer, dto.getCourseName(), BaseFont.TIMES_BOLD, 32, 560, 185);
        setTextRow(writer, "Certificate was issued by vertex-academy.com", BaseFont.TIMES_ROMAN, 18, 280, 140);
        setTextRow(writer, "Director General: Piskokha M. A.", BaseFont.TIMES_ROMAN, 18, 850, 140);
        setTextRow(writer, "Certificate UID: " + dto.getCertificateUid(), BaseFont.TIMES_ROMAN, 18, 280, 115);
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

    @Autowired
    public PdfGenerator(LogInfo logInfo) {
        this.logInfo = logInfo;
    }
}
