package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.LoggingLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private LoggingLogic loggingLogic;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = loggingLogic.logIn(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        } else {
            User user = userOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    authorities);
        }
    }

    @Autowired
    public void setSpringDataUserDetailsService(LoggingLogic loggingLogic) {
        this.loggingLogic = loggingLogic;
    }
}
