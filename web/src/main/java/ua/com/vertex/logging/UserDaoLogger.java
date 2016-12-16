package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@SuppressWarnings("ArgNamesWarningsInspection")
@Component
@Aspect
public class UserDaoLogger {

    private final static Logger LOGGER = LogManager.getLogger(UserDaoLogger.class);

    @Pointcut("execution(* ua.com.vertex.dao.UserDaoImpl.getUser(int))" +
            "&& args(userId))")
    public void aspectForGetUser(int userId) {
    }

    @Before("aspectForGetUser(userId)")
    public void before(int userId) {
        LOGGER.debug("ua.com.vertex.dao.UserDaoImpl.getUser(..) " +
                "- Retrieving user by userID=" + userId + System.lineSeparator());
    }

    @AfterReturning("aspectForGetUser(userId)")
    public void after(int userId) {
        LOGGER.debug("ua.com.vertex.dao.UserDaoImpl.getUser(..) " +
                "- Exiting method" + System.lineSeparator());
    }
}
