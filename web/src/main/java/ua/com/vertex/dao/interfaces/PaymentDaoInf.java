package ua.com.vertex.dao.interfaces;


import ua.com.vertex.beans.Payment;

public interface PaymentDaoInf {

    int createNewPayment(int courseId, int userId, Payment payment);

}
