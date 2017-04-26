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
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String AMOUNT = "amount";
    private static final String PAYMENT_DATE = "paymentDate";
    private static final String PAYMENT_ID = "paymentId";
    private static final String COLUMN_PAYMENT_ID = "payment_id";
    private static final String COLUMN_DEAL_ID = "deal_id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_PAYMENT_DATE = "payment_date";

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

    @Override
    public Optional<Payment> getPaymentById(int paymentId) {
        LOGGER.debug(String.format("Try get payment by paymentId = (%s)", paymentId));

        String query = "SELECT payment_id, deal_id, amount, payment_date FROM Payments WHERE payment_id = :paymentId";

        Payment result = null;

        try {
            result = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(PAYMENT_ID, paymentId), (resultSet, i) ->
                    new Payment.Builder().setPaymentId(resultSet.getInt(COLUMN_PAYMENT_ID))
                            .setDealId(resultSet.getInt(COLUMN_DEAL_ID))
                            .setAmount(BigDecimal.valueOf(resultSet.getDouble(COLUMN_AMOUNT)))
                            .setPaymentDate(resultSet.getTimestamp(COLUMN_PAYMENT_DATE).toLocalDateTime()).getInstance());
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
