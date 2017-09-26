package ua.com.vertex.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.PdfDto;
import ua.com.vertex.context.TestConfig;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PdfGeneratorTest {

    private PdfGenerator pdfGenerator;
    private final File file = new File(PDF_FILE_NAME);
    private static final String PDF_FILE_NAME = "PdfGeneratorTest.pdf";

    @Before
    public void setUp() throws IOException {
        if (file.exists()) {
            file.delete();
        }
        pdfGenerator = new PdfGenerator();
    }

    @After
    public void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @WithMockUser
    public void generatePdfCreatesFileOnDisk() throws Exception {
        final String email = "email";
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String courseName = "Java Professional";
        final String certificationDate = "2016-12-01";
        final String certificationId = "id";

        PdfDto dto = new PdfDto(email, firstName, lastName, courseName,
                certificationDate, certificationId);

        pdfGenerator.generatePdf(PDF_FILE_NAME, dto);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
