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
import ua.com.vertex.dao.interfaces.AccountingDaoInf;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;

import javax.sql.DataSource;

@Repository
public class PaymentDaoImpl implements PaymentDaoInf {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AccountingDaoInf accountingDaoInf;

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);
    private static final String DEAL_ID = "dealId";
    private static final String AMOUNT = "amount";
    private static final String PAYMENT_DATE = "paymentDate";


    @Override
    public int createNewPayment(Payment payment) throws DataAccessException {
        LOGGER.debug(String.format("Try create new payment by dealId = ($s), to db.Payments", payment.getDealId()));

        String query = "INSERT INTO Payments (deal_id, amount, payment_date) " +
                "VALUES (:dealID , :amount, :paymentDate)";

        MapSqlParameterSource source = new MapSqlParameterSource(DEAL_ID, payment.getDealId());
        source.addValue(AMOUNT, payment.getAmount());
        source.addValue(PAYMENT_DATE, payment.getPaymentDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(query, source, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    @Transactional
    public int createNewPaymentAndUpdateAccounting(int courseId, int userId, Payment payment) throws DataAccessException {

        LOGGER.debug(String.format(" Call accountingDaoInf.updateUserDept(courseId =(%s), userId = (%s), payment (%s))",
                courseId, userId, payment));

        payment.setDealId(accountingDaoInf.updateUserDept(courseId, userId, payment));

        LOGGER.debug(String.format(" Call createNewPayment(%s)", payment));

        return createNewPayment(payment);
    }

    @Autowired
    public PaymentDaoImpl(DataSource dataSource, AccountingDaoInf accountingDaoInf) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.accountingDaoInf = accountingDaoInf;
    }
}
