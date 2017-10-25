package ua.com.vertex.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.*;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.*;
import ua.com.vertex.logic.interfaces.CertificateLogic;
import ua.com.vertex.logic.interfaces.CourseLogic;
import ua.com.vertex.logic.interfaces.PaymentLogic;
import ua.com.vertex.testutils.TransactionProvider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class TransactionsInLogicTest {

    @Autowired
    private TransactionProvider<CourseLogic, DtoCourseUser, Object, Object> transactionProviderCourseLogic;
    @Autowired
    private CourseLogic courseLogic;

    @Autowired
    private TransactionProvider<CertificateLogic, Certificate, User, Object> transactionProviderCertificateLogic;
    @Autowired
    private CertificateLogic certificateLogic;
    @Autowired
    private UserDaoInf userDao;

    @Autowired
    private TransactionProvider<PaymentLogic, PaymentForm, Object, Object> transactionProviderPaymentLogic;
    @Autowired
    private PaymentLogic paymentLogic;
    @Autowired
    private AccountingDaoImplForTest accountingDaoImplForTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void assignUserToCourseTransactionRollbackCorrectly() {
        int courseId = 2;
        int userId = 401;
        DtoCourseUser dto = new DtoCourseUser();
        dto.setCourseId(courseId);
        dto.setUserId(userId);

        assertFalse(String.format("user with id = %d should not be assigned to the course with id = %d before test!",
                userId, courseId),
                courseLogic.getUsersAssignedToCourse(courseId).stream().anyMatch((u) -> u.getUserId() == userId));

        try {
            transactionProviderCourseLogic.withPropagationRequiresNew(CourseLogic::assignUserToCourse,
                    courseLogic, dto, true);
        } catch (RuntimeException ignored) {}

        assertFalse(String.format("user with id = %d is still assigned to course with id = %d ! " +
                "Rollback failed or method was changed!", userId, courseId),
                courseLogic.getUsersAssignedToCourse(courseId).stream().anyMatch((u) -> u.getUserId() == userId));

    }

    @Test
    public void addCertificateAndCreateUserTransactionRollbackCorrectly() {
        String userEmail = "addCertificateAndCreateTest@test.com";
        Certificate certificate = new Certificate.Builder().setCourseName("Java")
                .setCertificationDate(LocalDate.parse("2016-12-01")).setLanguage("java").getInstance();
        User user = new User.Builder().setEmail(userEmail)
                .setFirstName("addCertificateAndCreate").setLastName("addCertificateAndCreate").getInstance();

        assertFalse(String.format("no user with email = %s should exist before test!", userEmail),
                userDao.getUserByEmail(userEmail).isPresent());

        try {
            transactionProviderCertificateLogic.withPropagationRequiresNew(CertificateLogic::addCertificateAndCreateUser,
                    certificateLogic, certificate, user, true);
        } catch (RuntimeException ignored) {}

        assertFalse(String.format("no user with email = %s should exist! " +
                        "Rollback failed or method was changed!", userEmail),
                userDao.getUserByEmail(userEmail).isPresent());
    }

    @Test
    public void createNewPaymentAndUpdateAccountingTransactionRollbackCorrectly() {
        int courseId = 1;
        int userId = 2;
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setCourseId(courseId);
        paymentForm.setUserID(userId);
        paymentForm.setPayment(new Payment.Builder().setAmount(new BigDecimal(2000)).getInstance());

        Optional<Accounting> accounting = accountingDaoImplForTest.getAccountingByCourseIdAndUserId(courseId, userId);

        assertTrue(String.format("accounting row for user with id = %d and course with id = %d should exist before test!",
                userId, courseId),
                accounting.isPresent());
        assertEquals("accounting row for user with id = %d and course with id = %d is incorrect!",
                4000d, accounting.get().getDebt(), 0);

        try {
            transactionProviderPaymentLogic.withPropagationRequiresNew(PaymentLogic::createNewPaymentAndUpdateAccounting,
                    paymentLogic, paymentForm, true);
        } catch (RuntimeException ignored) {}

        accounting = accountingDaoImplForTest.getAccountingByCourseIdAndUserId(courseId, userId);

        assertTrue(String.format("accounting row for user with id = %d and course with id = %d should exist! " +
                        "Rollback failed or method was changed!", userId, courseId),
                accounting.isPresent());
        assertEquals("accounting row for user with id = %d and course with id = %d is incorrect! " +
                        "Rollback failed or method was changed!", 4000d, accounting.get().getDebt(), 0);

    }

}
