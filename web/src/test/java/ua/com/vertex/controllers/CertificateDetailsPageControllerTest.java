package ua.com.vertex.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.MainContext;
import ua.com.vertex.logic.interfaces.CertDetailsPageLogic;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainContext.class)
public class CertificateDetailsPageControllerTest {

    @Autowired
    private CertDetailsPageLogic logic;

    @Test
    public void logicShouldNotBeNull() {
        assertNotNull(logic);
    }
}
