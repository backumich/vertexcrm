package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Payment;

import java.util.Optional;

public interface PaymentDaoInf {

    int createNewPayment(int courseId, int userId, Payment payment);

    Optional<Payment> getPaymentById(int paymentId);

}
