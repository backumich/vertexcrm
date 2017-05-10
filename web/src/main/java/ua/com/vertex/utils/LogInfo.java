package ua.com.vertex.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class LogInfo {

    public String getId() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        String email = null;
        try {
            email = getEmail();
        } catch (ClassCastException e) {
            email = null;
        } catch (NullPointerException e) {
            sessionId = null;
        }
        return email == null ? String.format("[Session id: %s] ", sessionId)
                : String.format("[Session id: %s Email: %s] ", sessionId, email);
    }

    public String getEmail() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
