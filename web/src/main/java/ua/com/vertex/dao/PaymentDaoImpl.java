package ua.com.vertex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.dao.interfaces.PaymentDaoInf;

import javax.sql.DataSource;

@Repository
public class PaymentDaoImpl implements PaymentDaoInf {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PaymentDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int createNewPayment(int userId) {
        String query = "INSERT INTO Payments (deal_id, amount, payment_date) " +
                "VALUES (:dealID , :amount, :paymentDate";
        MapSqlParameterSource source = new MapSqlParameterSource()
        return jdbcTemplate.query(query, );
    }
}
