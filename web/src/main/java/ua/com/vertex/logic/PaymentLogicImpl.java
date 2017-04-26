package ua.com.vertex.logic;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Payment;
import ua.com.vertex.beans.PaymentForm;
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;
import ua.com.vertex.logic.interfaces.PaymentLogic;

import java.time.LocalDateTime;


@Service
public class PaymentLogicImpl implements PaymentLogic {

    private static final Logger LOGGER = LogManager.getLogger(PaymentLogicImpl.class);

    private final PaymentDaoInf paymentDaoInf;
    private final AccountingDaoInf accountingDaoInf;

    @Autowired
    public PaymentLogicImpl(PaymentDaoInf paymentDaoInf, AccountingDaoInf accountingDaoInf) {
        this.paymentDaoInf = paymentDaoInf;
        this.accountingDaoInf = accountingDaoInf;
    }

    @Override
    @Transactional
    public int createNewPaymentAndUpdateAccounting(PaymentForm payment) {

        LOGGER.debug(String.format("Call - accountingDaoInf.updateUserDept((%s), (%s) , (%f)) ;",
                payment.getCourseId(), payment.getUserID(), payment.getPayment().getAmount()));
        accountingDaoInf.updateUserDept(payment.getCourseId(), payment.getUserID(),
                payment.getPayment().getAmount().doubleValue());

        LOGGER.debug(String.format("Call - paymentDaoInf.createNewPayment((%s), (%s) , (%f)) ;",
                payment.getCourseId(), payment.getUserID(), payment.getPayment().getAmount()));
        return paymentDaoInf.createNewPayment(payment.getCourseId(), payment.getUserID(),
                new Payment(payment.getPayment().getAmount(), LocalDateTime.now()));
    }
}
