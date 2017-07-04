package ua.com.vertex.dao.interfaces;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Accounting;

import java.util.Optional;

@Profile("test")
public interface AccountingDaoImplForTest {

    int createAccounting(Accounting accounting) throws DataAccessException;

    Optional<Accounting> getAccountingByCourseIdAndUserId(int courseId, int userId) throws DataAccessException;
}
