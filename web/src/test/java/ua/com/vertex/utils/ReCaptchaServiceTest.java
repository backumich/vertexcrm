package ua.com.vertex.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReCaptchaServiceTest {
    private final String MSG = "Maybe method was changed";

    private String reCaptchaResponse = "03AJz9lvSs2VBCrNUG8kQe9Z3SST63squTtaIeFg7hwdh6eoAw5hSTOtE7IjxRpAc7xB1XkYSylpKCaaYhFkMDONwGQGPwai3ZdN9AlXe3y4qtrodWlAOLc5SNrGuxZHP3tTIvAHQI4WUbjBCr_ZyWaombdwEywQDD_R3410";
    private String reCaptchaRemoteAddr = "localhost";

    @Mock
    private ReCaptchaService reCaptchaService;

    @Test
    public void VerificationPassed() {
        when(reCaptchaService.verify(reCaptchaResponse, reCaptchaRemoteAddr)).thenReturn(true);
        assertEquals(MSG, true, reCaptchaService.verify(reCaptchaResponse, reCaptchaRemoteAddr));
    }

    @Test
    public void VerificationFailed() {
        when(reCaptchaService.verify(reCaptchaResponse + "Test", reCaptchaRemoteAddr + "Test")).thenReturn(false);
        assertEquals(MSG, false, reCaptchaService.verify(reCaptchaResponse, reCaptchaRemoteAddr));
    }

}