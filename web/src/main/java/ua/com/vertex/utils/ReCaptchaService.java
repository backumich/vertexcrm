package ua.com.vertex.utils;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.com.vertex.beans.Captcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Configuration
@PropertySource("classpath:reCaptcha.properties")
public class ReCaptchaService {
    @Value("${reCaptcha.url}")
    private String reCaptchaUrl;

    @Value("${reCaptcha.secretKey}")
    private String secretKey;

    private static final Logger LOGGER = LogManager.getLogger(ReCaptchaService.class);

    public Boolean verify(String reCaptchaResponse, String reCaptchaRemoteAddr) {
        Boolean verified = false;
        try {
            LOGGER.debug("Trying to verify the validity of the captcha");
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + reCaptchaResponse + "&remoteip=" + reCaptchaRemoteAddr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String line;
            StringBuilder outputString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString.append(line);
            }
            Gson gson = new Gson();
            Captcha captcha = gson.fromJson(outputString.toString(), Captcha.class);
            verified = captcha.isSuccess();
        } catch (IOException e) {
            LOGGER.warn(e);
        }
        return verified;
    }

    public String getReCaptchaUrl() {
        return reCaptchaUrl;
    }

    public void setReCaptchaUrl(String reCaptchaUrl) {
        this.reCaptchaUrl = reCaptchaUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReCaptchaService that = (ReCaptchaService) o;
        return Objects.equals(reCaptchaUrl, that.reCaptchaUrl) &&
                Objects.equals(secretKey, that.secretKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reCaptchaUrl, secretKey);
    }

    @Override
    public String toString() {
        return "ReCaptchaService{" +
                "reCaptchaUrl='" + reCaptchaUrl + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
