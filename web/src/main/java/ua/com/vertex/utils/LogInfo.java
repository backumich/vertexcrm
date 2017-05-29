package ua.com.vertex.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class LogInfo {

    public String getId() {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        String email = getEmail();

        return email == null ? String.format("[Session id: %s] ", sessionId)
                : String.format("[Session id: %s Email: %s] ", sessionId, email);
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
}
