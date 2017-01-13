package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.User;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.util.ArrayList;
import java.util.List;

import static ua.com.vertex.beans.User.EMPTY_USER;


@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private UserLogic userLogic;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userLogic.logIn(username);
        if (user.equals(EMPTY_USER)) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        } else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    authorities);
        }
    }

    @Autowired
    public void setSpringDataUserDetailsService(UserLogic userLogic) {
        this.userLogic = userLogic;
    }
}
