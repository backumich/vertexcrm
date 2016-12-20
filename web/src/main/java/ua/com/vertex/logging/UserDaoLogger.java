package ua.com.vertex.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserDaoLogger {

    private final static Logger LOGGER = LogManager.getLogger(UserDaoLogger.class);

    @Around("execution(* ua.com.vertex.dao.UserDaoImpl.getUser(..))" +
            "&& args(userId)")
    public Object aspectForGetUser(ProceedingJoinPoint jp, int userId) throws Throwable {
        Object object;
        LOGGER.debug("ua.com.vertex.dao.UserDaoImpl.getUser(..) " +
                "- Retrieving user by userID=" + userId + System.lineSeparator());
        object = jp.proceed();
        LOGGER.debug("ua.com.vertex.dao.UserDaoImpl.getUser(..) " +
                "- UserID=" + userId + " retrieved" + System.lineSeparator());
        return object;
    }
}
