package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;
import ua.com.vertex.logic.interfaces.Paymentlogic;

@Service
public class PaymentLogicImpl implements Paymentlogic {

    private final PaymentDaoInf paymentDaoInf;

    @Autowired
    public PaymentLogicImpl(PaymentDaoInf paymentDaoInf) {
        this.paymentDaoInf = paymentDaoInf;
    }

    @Override
    public int createNewPayment(int userId) {
        return paymentDaoInf.createNewPayment(userId);
    }
}
