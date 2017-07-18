package ua.com.vertex.dao.interfaces;

import org.springframework.context.annotation.Profile;
import ua.com.vertex.beans.User;

@Profile("test")
public interface UserDaoForTest {

    int insertUser(User user);
}
