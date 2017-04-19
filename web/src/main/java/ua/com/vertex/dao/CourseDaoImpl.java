package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class CourseDaoImpl implements CourseDaoInf {

    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_TEACHER_NAME = "teacher_name";
    private static final String COLUMN_COURSE_START = "start";
    private static final String COLUMN_COURSE_PRICE = "price";
    private static final String COLUMN_COURSE_NOTES = "notes";

    @Override
    public List<Course> getAllActiveCourses() throws DataAccessException {
        LOGGER.debug("Try select all active courses from db.Courses");

        String query = "SELECT id, name, start, price, teacher_name, notes FROM Courses WHERE finished=0";
        return jdbcTemplate.query(query, (resultSet, i) -> new Course.Builder()
                .setId(resultSet.getInt(COLUMN_COURSE_ID))
                .setName(resultSet.getString(COLUMN_COURSE_NAME))
                .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                .setTeacherName(resultSet.getString(COLUMN_COURSE_TEACHER_NAME))
                .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
    }


    @Autowired
    public CourseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
