package ua.com.vertex.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.UserDaoForTest;

import javax.sql.DataSource;

@Repository
public class UserDaoForTestImpl implements UserDaoForTest {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSPORT_SCAN = "passport_scan";
    private static final String COLUMN_PHOTO = "photo";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_ROLE_NAME = "name";
    private static final String COLUMN_IS_ACTIVE = "is_active";

    @Override
    public int insertUser(User user) {
        String query = "INSERT INTO Users ( email, password, first_name, last_name, passport_scan, photo," +
                " discount, phone, role_id, is_active) VALUES ( :email, :password, :first_name, :last_name," +
                " :passport_scan, :photo, :discount, :phone," +
                " (SELECT r.role_id FROM Roles r WHERE r.name= :name), :is_active)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_USER_EMAIL,user.getEmail());
        source.addValue(COLUMN_PASSWORD,user.getPassword());
        source.addValue(COLUMN_FIRST_NAME,user.getFirstName());
        source.addValue(COLUMN_LAST_NAME,user.getLastName());
        source.addValue(COLUMN_PASSPORT_SCAN,user.getPassportScan());
        source.addValue(COLUMN_PHOTO,user.getPhoto());
        source.addValue(COLUMN_DISCOUNT,user.getDiscount());
        source.addValue(COLUMN_PHONE,user.getPhone());
        source.addValue(COLUMN_ROLE_NAME,user.getRole());
        source.addValue(COLUMN_IS_ACTIVE,user.isActive());

        jdbcTemplate.update(query, source, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Autowired
    public UserDaoForTestImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
