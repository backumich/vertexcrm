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

    private static final Logger LOGGER = LogManager.getLogger(UserDaoLogger.class);

    @Around("execution(* ua.com.vertex.dao.UserDaoImpl.getUser(..))" +
            "&& args(userId)")
    public Object aspectForGetUser(ProceedingJoinPoint jp, int userId) throws Throwable {

        LOGGER.debug(compose(jp) + " - Retrieving user by userID=" + userId + System.lineSeparator());
        Object object = jp.proceed();
        LOGGER.debug(compose(jp) + " - UserID=" + userId + " retrieved" + System.lineSeparator());

        return object;
    }

    private String compose(ProceedingJoinPoint jp) {
        return "class: " + jp.getSignature().getDeclaringType() + "; method: " + jp.getSignature().getName();
    }
}
