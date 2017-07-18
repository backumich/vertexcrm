package ua.com.vertex.dao.interfaces;


import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Payment;

import java.util.Optional;

public interface PaymentDaoInf {

    int createNewPayment(int courseId, int userId, Payment payment) throws DataAccessException;

    Optional<Payment> getPaymentByIdWithOutDate(int paymentId) throws DataAccessException;
}
