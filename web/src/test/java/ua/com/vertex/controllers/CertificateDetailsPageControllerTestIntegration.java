package ua.com.vertex.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.ImageStorage;
import ua.com.vertex.context.MainTestContext;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainTestContext.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CertificateDetailsPageControllerTestIntegration {

    @Autowired
    private CertDetailsPageLogic logic;

    @Autowired
    private ImageStorage storage;

    @Test
    public void logicShouldNotBeNull() {
        assertNotNull(logic);
    }

    @Test
    public void imageStorageShouldNotBeNull() {
        assertNotNull(storage);
    }
}
