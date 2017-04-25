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
    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_TEACHER_NAME = "teacher_name";
    private static final String COLUMN_COURSE_START = "start";
    private static final String COLUMN_COURSE_FINISHED = "finished";
    private static final String COLUMN_COURSE_PRICE = "price";
    private static final String COLUMN_COURSE_NOTES = "notes";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Course> getAllCoursesWithDept() throws DataAccessException {
        LOGGER.debug("Try select all courses where user has dept.");

        String query = "SELECT DISTINCT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.notes FROM Courses c " +
                "INNER JOIN Accounting a ON  a.course_id = c.id WHERE a.debt > 0";

        return jdbcTemplate.query(query, (resultSet, i) -> new Course.Builder()
                .setId(resultSet.getInt(COLUMN_COURSE_ID))
                .setName(resultSet.getString(COLUMN_COURSE_NAME))
                .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                .setTeacherName(resultSet.getString(COLUMN_COURSE_TEACHER_NAME))
                .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
    }
}
