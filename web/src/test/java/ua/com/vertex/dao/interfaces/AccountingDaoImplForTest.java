package ua.com.vertex.dao.interfaces;

import org.springframework.context.annotation.Profile;
import ua.com.vertex.beans.Accounting;

import java.util.Optional;

@Profile("test")
public interface AccountingDaoImplForTest {

    int createAccounting(Accounting accounting);

    Optional<Accounting> getAccountingByCourseIdAndUserId(int courseId, int userId);
}
