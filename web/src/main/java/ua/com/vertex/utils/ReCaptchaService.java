package ua.com.vertex.utils;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.com.vertex.beans.Captcha;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Component
@PropertySource("classpath:reCaptcha.properties")
public class ReCaptchaService {
    @Value("${reCaptcha.url}")
    private String reCaptchaUrl;

    @Value("${reCaptcha.secretKey}")
    private String secretKey;

    private static final Logger LOGGER = LogManager.getLogger(ReCaptchaService.class);

    public Boolean verify(String reCaptchaResponse, String reCaptchaRemoteAddr) throws IOException {
        Boolean verified = false;
        HttpsURLConnection connection = null;

        try {
            LOGGER.debug("Trying to verify the validity of the captcha");
            URL url = new URL(reCaptchaUrl);
            String postParams = "secret=" + secretKey + "&response=" + reCaptchaResponse + "&remoteip=" + reCaptchaRemoteAddr;

            connection = getConnection(url);
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(postParams);
            }
            String line;
            StringBuilder outputString = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    outputString.append(line);
                }
            }

            Captcha captcha = new Gson().fromJson(outputString.toString(), Captcha.class);
            verified = captcha.isSuccess();
        } catch (IOException e) {
            LOGGER.warn(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return verified;
    }

    private HttpsURLConnection getConnection(URL url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        return connection;
    }
}
