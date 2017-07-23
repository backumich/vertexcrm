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

import static ua.com.vertex.dao.UserDaoImpl.COLUMN_FIRST_NAME;
import static ua.com.vertex.dao.UserDaoImpl.COLUMN_LAST_NAME;
import static ua.com.vertex.dao.UserDaoImpl.COLUMN_USER_EMAIL;


@Repository
public class CourseDaoImpl implements CourseDaoInf {

    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_START = "start";
    private static final String COLUMN_COURSE_FINISHED = "finished";
    private static final String COLUMN_COURSE_PRICE = "price";
    private static final String COLUMN_COURSE_TEACHER_ID = "teacher_id";
    private static final String COLUMN_COURSE_SCHEDULE = "schedule";
    private static final String COLUMN_COURSE_NOTES = "notes";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    @Override
    public List<Course> getAllCourses(DataNavigator dataNavigator) {

        LOGGER.debug("Get all courses list");

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.schedule, c.notes " +
                "FROM Courses c LIMIT :from, :offset";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("from", (dataNavigator.getCurrentNumberPage() - 1) * dataNavigator.getRowPerPage());
        parameters.addValue("offset", dataNavigator.getRowPerPage());

        List<Course> courses = jdbcTemplate.query(query, parameters, this::mapCourses);

        String allCourses = courses.stream().map(Course::getName).collect(Collectors.joining("|"));
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

        String query = "INSERT INTO Courses(name, start, finished, price, teacher_name, schedule, notes) " +
                "VALUES (:name, :start, :finished, :price, :teacher_name, :schedule, :notes)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, getCourseParameters(course), keyHolder);

        Number id = keyHolder.getKey();
        return id.intValue();
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws DataAccessException {
        LOGGER.debug(String.format("Try get course by id -(%s)", courseId));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.schedule, c.notes " +
                "FROM Courses c WHERE id=:id";
        Course course = null;
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", courseId);
            course = jdbcTemplate.queryForObject(query, parameters, this::mapCourses);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("The course with id - %s was not found.", courseId));
        }
        return Optional.ofNullable(course);
    }

    public Course mapCourses(ResultSet resultSet, int i) throws SQLException {
        return new Course.Builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setStart(resultSet.getDate("start").toLocalDate())
                .setFinished(resultSet.getInt("finished") != 0)
                .setPrice(resultSet.getBigDecimal("price"))
                .setTeacherName(resultSet.getString("teacher_name"))
                .setSchedule(resultSet.getString("schedule"))
                .setNotes(resultSet.getString("notes"))
                .getInstance();
    }

    private MapSqlParameterSource getCourseParameters(Course course) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", course.getName());
        namedParameters.addValue("start", course.getStart());
        namedParameters.addValue("finished", course.getFinished());
        namedParameters.addValue("price", course.getPrice());
        namedParameters.addValue("teacher_name", course.getTeacherName());
        namedParameters.addValue("schedule", course.getSchedule());
        namedParameters.addValue("notes", course.getNotes());
        return namedParameters;
    }

    @Override
    public List<Course> getAllCoursesWithDept() throws DataAccessException {
        LOGGER.debug("Call - courseDaoInf.getAllCoursesWithDept()");

        String query = "SELECT DISTINCT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.notes, u.first_name," +
                " u.last_name, u.email FROM Courses c  INNER JOIN Users u ON u.user_id = c.teacher_id " +
                "INNER JOIN Accounting a ON  a.course_id = c.id WHERE a.debt > 0";

        LOGGER.debug("Try select all courses where user has dept.");
        return jdbcTemplate.query(query, (resultSet, i) -> new Course.Builder()
                .setId(resultSet.getInt(COLUMN_COURSE_ID))
                .setName(resultSet.getString(COLUMN_COURSE_NAME))
                .setStart(resultSet.getDate(COLUMN_COURSE_START).toLocalDate())
                .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                .setTeacher(new User.Builder().setUserId(resultSet.getInt(COLUMN_COURSE_TEACHER_ID))
                        .setEmail(resultSet.getString(COLUMN_USER_EMAIL))
                        .setFirstName(resultSet.getString(COLUMN_FIRST_NAME))
                        .setLastName(resultSet.getString(COLUMN_LAST_NAME))
                        .getInstance())
                .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
    }

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) throws DataAccessException {
        LOGGER.debug(String.format("Call courseDaoInf.searchCourseByNameAndStatus(%s)", course));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name, u.email FROM Courses c INNER JOIN Users u  ON c.teacher_id = u.user_id " +
                "WHERE name LIKE  '%" + course.getName() + "%' AND finished=:finished";

        LOGGER.debug(String.format("Search course by name - (%s) and finished - (%s).", course.getName(), course.isFinished()));
        return jdbcTemplate.query(query, new MapSqlParameterSource(COLUMN_COURSE_FINISHED, course.isFinished() ? 1 : 0),
                (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(COLUMN_COURSE_ID))
                        .setName(resultSet.getString(COLUMN_COURSE_NAME))
                        .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                        .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                        .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                        .setTeacher(new User.Builder().setUserId(resultSet.getInt(COLUMN_COURSE_TEACHER_ID))
                                .setEmail(resultSet.getString(COLUMN_USER_EMAIL))
                                .setFirstName(resultSet.getString(COLUMN_FIRST_NAME))
                                .setLastName(resultSet.getString(COLUMN_LAST_NAME))
                                .getInstance())
                        .setShedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                        .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
    }

    @Override
    public int updateCourseExceptPrice(Course course) throws DataAccessException {
        LOGGER.debug(String.format("Call courseDaoInf.updateCourseExceptPrice(%s)", course));

        String query = "UPDATE Courses SET name=:name, start=:start, finished=:finished, teacher_id=:teacher_id," +
                "schedule=:schedule, notes=:notes WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_COURSE_ID, course.getId());
        source.addValue(COLUMN_COURSE_NAME, course.getName());
        source.addValue(COLUMN_COURSE_START, course.getStart());
        source.addValue(COLUMN_COURSE_FINISHED, course.isFinished());
        source.addValue(COLUMN_COURSE_TEACHER_ID, course.getTeacher().getUserId());
        source.addValue(COLUMN_COURSE_SCHEDULE, course.getSchedule());
        source.addValue(COLUMN_COURSE_NOTES, course.getNotes());

        LOGGER.debug(String.format("Try update course except price by id -(%s)", course.getId()));
        return jdbcTemplate.update(query, source);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws DataAccessException {
        LOGGER.debug(String.format("Call courseDaoInf.getCourseById(%s)", courseId));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes " +
                "FROM Courses c WHERE id=:id";
        Course course = null;

        LOGGER.debug(String.format("Try get course by id -(%s)", courseId));
        try {
            course = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(COLUMN_COURSE_ID, courseId),
                    (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(COLUMN_COURSE_ID))
                            .setName(resultSet.getString(COLUMN_COURSE_NAME))
                            .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                            .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                            .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                            .setTeacher(new User.Builder().setUserId(resultSet.getInt(COLUMN_COURSE_TEACHER_ID))
                                    .getInstance())
                            .setShedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                            .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("The course with id - %s was not found.", courseId));
        }

        return Optional.ofNullable(course);
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
