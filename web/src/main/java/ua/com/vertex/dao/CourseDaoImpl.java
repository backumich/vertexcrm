package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.utils.DataNavigator;
import ua.com.vertex.utils.LogInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CourseDaoImpl implements CourseDaoInf {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String START = "start";
    private static final String FINISHED = "finished";
    private static final String PRICE = "price";
    private static final String TEACHER_ID = "teacher_id";
    private static final String TEACHER_FIRST_NAME = "first_name";
    private static final String TEACHER_LAST_NAME = "last_name";
    private static final String SCHEDULE = "schedule";
    private static final String NOTES = "notes";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    @Override
    public List<Course> getCoursesPerPages(DataNavigator dataNavigator) {

        LOGGER.debug("Get all courses list");

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name " +
                "FROM Courses c " +
                "LEFT JOIN Users u ON c.teacher_id = u.user_id " +
                "LIMIT :from, :offset ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("from", (dataNavigator.getCurrentNumberPage() - 1) * dataNavigator.getRowPerPage());
        parameters.addValue("offset", dataNavigator.getRowPerPage());

        List<Course> courses = jdbcTemplate.query(query, parameters, new CourseRowMapper());

        String allCourses = courses.stream()
                .map(Course::getName)
                .collect(Collectors.joining("|"));
        LOGGER.debug("Quantity courses -" + courses.size());
        LOGGER.debug("All courses list -" + allCourses);

        return courses;
    }

    @Override
    public int getQuantityCourses() throws SQLException {
        LOGGER.debug("Get count courses");
        String query = "SELECT count(*) FROM Courses";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), int.class);
    }

    @Override
    public int addCourse(Course course) throws SQLException {
        LOGGER.info("Adding a new course into database");

        String query = "INSERT INTO Courses(name, start, finished, price, teacher_id, schedule, notes) " +
                "VALUES (:name, :start, :finished, :price, :teacher_id, :schedule, :notes)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, getCourseParameters(course), keyHolder);

        Number id = keyHolder.getKey();
        return id.intValue();
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws DataAccessException {
        LOGGER.debug(String.format("Try get course by id -(%s)", courseId));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name " +
                "FROM Courses c " +
                "LEFT JOIN Users u ON c.teacher_id = u.user_id " +
                "WHERE id=:id ";
        Course course = null;
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", courseId);
            course = jdbcTemplate.queryForObject(query, parameters, new CourseRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("The course with id - %s was not found.", courseId));
        }
        return Optional.ofNullable(course);
    }

    private static final class CourseRowMapper implements RowMapper<Course> {
        public Course mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Course.Builder()
                    .setId(resultSet.getInt(ID))
                    .setName(resultSet.getString(NAME))
                    .setStart(resultSet.getDate(START).toLocalDate())
                    .setFinished(resultSet.getInt(FINISHED) != 0)
                    .setPrice(resultSet.getBigDecimal(PRICE))
                    .setTeacher(new User.Builder()
                            .setUserId(resultSet.getInt(TEACHER_ID))
                            .setFirstName(resultSet.getString(TEACHER_FIRST_NAME))
                            .setLastName(resultSet.getString(TEACHER_LAST_NAME))
                            .getInstance())
                    .setSchedule(resultSet.getString(SCHEDULE))
                    .setNotes(resultSet.getString(NOTES))
                    .getInstance();
        }
    }

    private MapSqlParameterSource getCourseParameters(Course course) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", course.getName());
        namedParameters.addValue("start", course.getStart());
        namedParameters.addValue("finished", course.getFinished());
        namedParameters.addValue("price", course.getPrice());
        namedParameters.addValue("teacher_id", course.getTeacher().getUserId());
        namedParameters.addValue("schedule", course.getSchedule());
        namedParameters.addValue("notes", course.getNotes());
        return namedParameters;
    }

    @Override
    public List<Course> getAllCoursesWithDept() throws DataAccessException {
        LOGGER.debug("Try select all courses where user has dept.");

        String query = "SELECT DISTINCT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name " +
                "FROM Courses c " +
                "LEFT JOIN Users u ON c.teacher_id = u.user_id " +
                "INNER JOIN Accounting a ON c.id = a.course_id " +
                "WHERE a.debt > 0 ";

        return jdbcTemplate.query(query, new CourseRowMapper());
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
