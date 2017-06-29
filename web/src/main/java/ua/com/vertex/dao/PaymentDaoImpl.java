package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Payment;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Optional;

import static ua.com.vertex.dao.AccountingDaoImpl.COURSE_ID;
import static ua.com.vertex.dao.AccountingDaoImpl.USER_ID;

@Repository
public class PaymentDaoImpl implements PaymentDaoInf {

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);
    private static final String AMOUNT = "amount";
    private static final String PAYMENT_ID = "paymentId";
    private static final String COLUMN_PAYMENT_ID = "payment_id";
    private static final String COLUMN_DEAL_ID = "deal_id";
    private static final String COLUMN_AMOUNT = "amount";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public int createNewPayment(int courseId, int userId, Payment payment) throws DataAccessException {
        LOGGER.debug(String.format("Call - paymentDaoInf.createNewPayment((%s), (%s) , (%s)) ;",
                courseId, userId, payment));

        String query = "INSERT INTO Payments (deal_id, amount) VALUES ((SELECT deal_id FROM Accounting " +
                "WHERE course_id = :courseId AND user_id = :userId) , :amount)";
        MapSqlParameterSource source = new MapSqlParameterSource(COURSE_ID, courseId);
        source.addValue(USER_ID, userId);
        source.addValue(AMOUNT, payment.getAmount().doubleValue());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        LOGGER.debug(String.format("Try create new Payment = (%s), by courseId = (%s) and userId - (%s)",
                payment, courseId, userId));
        jdbcTemplate.update(query, source, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Optional<Payment> getPaymentByIdWithOutDate(int paymentId) {
        LOGGER.debug(String.format("Try get payment by paymentId = (%s)", paymentId));

        String query = "SELECT payment_id, deal_id, amount FROM Payments WHERE payment_id = :paymentId";

        Payment result = null;

        try {
            result = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(PAYMENT_ID, paymentId), (resultSet, i) ->
                    new Payment.Builder().setPaymentId(resultSet.getInt(COLUMN_PAYMENT_ID))
                            .setDealId(resultSet.getInt(COLUMN_DEAL_ID))
                            .setAmount(BigDecimal.valueOf(resultSet.getDouble(COLUMN_AMOUNT))).getInstance());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("No payment in DB, where id = (%s)", paymentId));
        }

        if (result != null) {
            LOGGER.debug(String.format("getPaymentById(%s) return - ", paymentId) + result);
        }
        return Optional.ofNullable(result);
    }

    @Autowired
    public PaymentDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
