package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Payment;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;

import javax.sql.DataSource;

import static ua.com.vertex.dao.AccountingDaoImpl.COURSE_ID;
import static ua.com.vertex.dao.AccountingDaoImpl.USER_ID;

@Repository
public class PaymentDaoImpl implements PaymentDaoInf {

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);
    private static final String AMOUNT = "amount";
    private static final String PAYMENT_DATE = "paymentDate";
    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    public PaymentDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int createNewPayment(int courseId, int userId, Payment payment) throws DataAccessException {
        LOGGER.debug(String.format("Try create new payment by courseId = (%s) and userId - (%s)", courseId, userId));

        String query = "INSERT INTO Payments (deal_id, amount, payment_date) VALUES ((SELECT deal_id FROM accounting " +
                "WHERE course_id = :courseId AND user_id = :userId) , :amount, :paymentDate)";

        MapSqlParameterSource source = new MapSqlParameterSource(COURSE_ID, courseId);
        source.addValue(USER_ID, userId);
        source.addValue(AMOUNT, payment.getAmount().doubleValue());
        source.addValue(PAYMENT_DATE, payment.getPaymentDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(query, source, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
