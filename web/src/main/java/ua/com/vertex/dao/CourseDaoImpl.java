package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.com.vertex.dao.UserDaoImpl.*;


@Repository
public class CourseDaoImpl implements CourseDaoInf {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String START = "start";
    private static final String FINISHED = "finished";
    private static final String PRICE = "price";
    private static final String TEACHER_ID = "teacher_id";
    private static final String SCHEDULE = "schedule";
    private static final String NOTES = "notes";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    @Override
    public List<Course> getAllCourses(DataNavigator dataNavigator) {

        LOGGER.debug("Get all courses list");

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name, u.email FROM Courses c  INNER JOIN Users u ON u.user_id = c.teacher_id " +
                "LIMIT :from, :offset";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("from", (dataNavigator.getCurrentNumberPage() - 1) * dataNavigator.getRowPerPage());
        parameters.addValue("offset", dataNavigator.getRowPerPage());

        List<Course> courses = jdbcTemplate.query(query, parameters, (resultSet, i) -> new Course.Builder()
                .setId(resultSet.getInt(ID))
                .setName(resultSet.getString(NAME))
                .setStart(resultSet.getDate(START).toLocalDate())
                .setFinished((resultSet.getInt(FINISHED) == 1))
                .setPrice(resultSet.getBigDecimal(PRICE))
                .setTeacher(new User.Builder().setUserId(resultSet.getInt(TEACHER_ID))
                        .setEmail(resultSet.getString(EMAIL))
                        .setFirstName(resultSet.getString(FIRST_NAME))
                        .setLastName(resultSet.getString(LAST_NAME))
                        .getInstance())
                .setNotes(resultSet.getString(NOTES)).getInstance());

        String allCourses = courses.stream().map(Course::getName).collect(Collectors.joining("|"));
        LOGGER.debug("Quantity courses -" + courses.size());
        LOGGER.debug("All courses list -" + allCourses);

        return courses;
    }

    @Override
    public int getQuantityCourses() {
        LOGGER.debug("Get count courses");
        String query = "SELECT count(*) FROM Courses";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), int.class);
    }

    @Override
    public int addCourse(Course course) {
        LOGGER.debug(String.format("Call - CourseDaoImpl.addCourse(%s)", course));

        String query = "INSERT INTO Courses(name, start, finished, price, teacher_id, schedule, notes) " +
                "VALUES (:name, :start, :finished, :price, :teacher_id, :schedule, :notes)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        LOGGER.debug("Try adding a new course into database");
        jdbcTemplate.update(query, getCourseParameters(course), keyHolder);

        Number id = keyHolder.getKey();
        return id.intValue();
    }

    private MapSqlParameterSource getCourseParameters(Course course) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(NAME, course.getName());
        namedParameters.addValue(START, course.getStart());
        namedParameters.addValue(FINISHED, course.isFinished());
        namedParameters.addValue(PRICE, course.getPrice());
        namedParameters.addValue(TEACHER_ID, course.getTeacher().getUserId());
        namedParameters.addValue(SCHEDULE, course.getSchedule());
        namedParameters.addValue(NOTES, course.getNotes());
        return namedParameters;
    }

    @Override
    public List<Course> getAllCoursesWithDept() {
        LOGGER.debug("Call - courseDaoInf.getAllCoursesWithDept()");

        String query = "SELECT DISTINCT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.notes, u.first_name," +
                " u.last_name, u.email FROM Courses c  INNER JOIN Users u ON u.user_id = c.teacher_id " +
                "INNER JOIN Accounting a ON  a.course_id = c.id WHERE a.debt > 0";

        LOGGER.debug("Try select all courses where user has dept.");
        return jdbcTemplate.query(query, (resultSet, i) -> new Course.Builder()
                .setId(resultSet.getInt(ID))
                .setName(resultSet.getString(NAME))
                .setStart(resultSet.getDate(START).toLocalDate())
                .setFinished((resultSet.getInt(FINISHED) == 1))
                .setPrice(resultSet.getBigDecimal(PRICE))
                .setTeacher(new User.Builder().setUserId(resultSet.getInt(TEACHER_ID))
                        .setEmail(resultSet.getString(EMAIL))
                        .setFirstName(resultSet.getString(FIRST_NAME))
                        .setLastName(resultSet.getString(LAST_NAME))
                        .getInstance())
                .setNotes(resultSet.getString(NOTES)).getInstance());
    }

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) {
        LOGGER.debug(String.format("Call courseDaoInf.searchCourseByNameAndStatus(%s)", course));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name, u.email FROM Courses c INNER JOIN Users u  ON c.teacher_id = u.user_id " +
                "WHERE name LIKE  '%" + course.getName() + "%' AND finished=:finished";

        LOGGER.debug(String.format("Search course by name - (%s) and finished - (%s).", course.getName(), course.isFinished()));
        return jdbcTemplate.query(query, new MapSqlParameterSource(FINISHED, course.isFinished() ? 1 : 0),
                (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(ID))
                        .setName(resultSet.getString(NAME))
                        .setStart(resultSet.getDate(START).toLocalDate())
                        .setFinished((resultSet.getInt(FINISHED) == 1))
                        .setPrice(resultSet.getBigDecimal(PRICE))
                        .setTeacher(new User.Builder().setUserId(resultSet.getInt(TEACHER_ID))
                                .setEmail(resultSet.getString(EMAIL))
                                .setFirstName(resultSet.getString(FIRST_NAME))
                                .setLastName(resultSet.getString(LAST_NAME))
                                .getInstance())
                        .setSchedule(resultSet.getString(SCHEDULE))
                        .setNotes(resultSet.getString(NOTES)).getInstance());
    }

    @Override
    public int updateCourseExceptPrice(Course course) {
        LOGGER.debug(String.format("Call courseDaoInf.updateCourseExceptPrice(%s)", course));

        String query = "UPDATE Courses SET name=:name, start=:start, finished=:finished, teacher_id=:teacher_id," +
                "schedule=:schedule, notes=:notes WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(ID, course.getId());
        source.addValue(NAME, course.getName());
        source.addValue(START, course.getStart());
        source.addValue(FINISHED, course.isFinished());
        source.addValue(TEACHER_ID, course.getTeacher().getUserId());
        source.addValue(SCHEDULE, course.getSchedule());
        source.addValue(NOTES, course.getNotes());

        LOGGER.debug(String.format("Try update course except price by id -(%s)", course.getId()));
        return jdbcTemplate.update(query, source);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) {
        LOGGER.debug(String.format("Call courseDaoInf.getCourseById(%s)", courseId));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes " +
                "FROM Courses c WHERE id=:id";
        Course course = null;

        LOGGER.debug(String.format("Try get course by id -(%s)", courseId));
        try {
            course = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(ID, courseId),
                    (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(ID))
                            .setName(resultSet.getString(NAME))
                            .setStart(resultSet.getDate(START).toLocalDate())
                            .setFinished((resultSet.getInt(FINISHED) == 1))
                            .setPrice(resultSet.getBigDecimal(PRICE))
                            .setTeacher(new User.Builder().setUserId(resultSet.getInt(TEACHER_ID))
                                    .getInstance())
                            .setSchedule(resultSet.getString(SCHEDULE))
                            .setNotes(resultSet.getString(NOTES)).getInstance());
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
