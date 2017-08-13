package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.PaymentForm;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;
import ua.com.vertex.logic.interfaces.PaymentLogic;

@Service
public class PaymentLogicImpl implements PaymentLogic {

    private final PaymentDaoInf paymentDaoInf;
    private final AccountingDaoInf accountingDaoInf;

    @Override
    @Transactional
    public int createNewPaymentAndUpdateAccounting(PaymentForm payment) {
        accountingDaoInf.updateUserDept(payment.getCourseId(), payment.getUserID(),
                payment.getPayment().getAmount().doubleValue());

        return paymentDaoInf.createNewPayment(payment.getCourseId(), payment.getUserID(),
                payment.getPayment());
    }

    @Autowired
    public PaymentLogicImpl(PaymentDaoInf paymentDaoInf, AccountingDaoInf accountingDaoInf) {
        this.paymentDaoInf = paymentDaoInf;
        this.accountingDaoInf = accountingDaoInf;
    }
}
