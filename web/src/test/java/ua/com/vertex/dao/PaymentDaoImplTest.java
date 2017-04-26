package ua.com.vertex.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.com.vertex.beans.Payment;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PaymentDaoImplTest {

    @Autowired
    private PaymentDaoInf paymentDaoInf;

    @Test
    public void createNewPaymentInsertPaymentToDb() throws Exception {
        Payment payment = new Payment.Builder().setAmount(BigDecimal.valueOf(100.00))
                .setPaymentDate(LocalDateTime.now()).getInstance();

        int result = paymentDaoInf.createNewPayment(1, 1, payment);
        payment.setDealId(1);
        payment.setPaymentId(result);

        //noinspection ConstantConditions
        assertEquals("Maybe method was changed", payment, paymentDaoInf.getPaymentById(result).get());
    }
}
