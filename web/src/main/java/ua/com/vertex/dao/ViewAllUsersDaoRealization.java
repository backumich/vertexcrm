package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.UserMainData;
import ua.com.vertex.controllers.UserController;
import ua.com.vertex.dao.interfaces.ViewAllUsersDaoRealizationInf;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ViewAllUsersDaoRealization implements ViewAllUsersDaoRealizationInf {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<UserMainData> getListUsers() throws DataAccessException {
        LOGGER.info("Adding a new user into database");

        String query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u";
        return jdbcTemplate.query(query, new UserRowMapping());
    }

    private static final class UserRowMapping implements RowMapper<UserMainData> {
        public UserMainData mapRow(ResultSet resultSet, int i) throws SQLException {
            return new UserMainData.Builder().
                    setUserId(resultSet.getInt("user_id")).
                    setEmail(resultSet.getString("email")).
                    setFirstName(resultSet.getString("first_name")).
                    setLastName(resultSet.getString("last_name")).
                    setPhone(resultSet.getString("phone")).getInstance();
        }
    }
}

