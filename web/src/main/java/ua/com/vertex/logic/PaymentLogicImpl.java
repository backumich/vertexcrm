package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Payment;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;
import ua.com.vertex.logic.interfaces.Paymentlogic;

import java.time.LocalDateTime;

@Service
public class PaymentLogicImpl implements Paymentlogic {

    private final PaymentDaoInf paymentDaoInf;

    @Override
    public int createNewPaymentAndUpdateAccounting(int courseId, int userId, Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        return paymentDaoInf.createNewPaymentAndUpdateAccounting(courseId, userId, payment);
    }

    @Autowired
    public PaymentLogicImpl(PaymentDaoInf paymentDaoInf) {
        this.paymentDaoInf = paymentDaoInf;
    }
}
