package ua.com.vertex.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Storage {
    private byte[] photo;
    private String sessionId;
    private String email;

    private static long count = 0;

    public Storage() {
        count++;
    }

    public long getCount() {
        return count;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return email == null ? String.format("[Session id: %s] ", sessionId)
                : String.format("[Session id: %s Email: %s] ", sessionId, email);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
