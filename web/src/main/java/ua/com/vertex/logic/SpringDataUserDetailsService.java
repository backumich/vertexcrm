package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.LoggingLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpringDataUserDetailsService implements UserDetailsService {

    private LoggingLogic loggingLogic;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loggingLogic.logIn(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                authorities);
    }

    @Autowired
    public void setSpringDataUserDetailsService(LoggingLogic loggingLogic) {
        this.loggingLogic = loggingLogic;
    }
}
