package ua.com.vertex.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Storage {
    private byte[] imageData;
    private String sessionId;

    public byte[] getPhoto() {
        return imageData;
    }

    public void setPhoto(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        sessionId = sessionId;
    }
}
