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
import ua.com.vertex.beans.CourseForOutput;
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
    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_START = "start";
    private static final String COLUMN_COURSE_FINISHED = "finished";
    private static final String COLUMN_COURSE_PRICE = "price";
    private static final String COLUMN_COURSE_TEACHER_ID = "teacher_id";
    private static final String COLUMN_COURSE_FOR_OUTPUT_TEACHER_FIRST_NAME = "first_name";
    private static final String COLUMN_COURSE_FOR_OUTPUT_TEACHER_LAST_NAME = "last_name";
    private static final String COLUMN_COURSE_SCHEDULE = "schedule";
    private static final String COLUMN_COURSE_NOTES = "notes";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    @Override
    public List<CourseForOutput> getCoursesForOutputPerPages(DataNavigator dataNavigator) {

        LOGGER.debug("Get all courses list");

        String query = "SELECT " +
                "c." + COLUMN_COURSE_ID + ", " +
                "c." + COLUMN_COURSE_NAME + ", " +
                "c." + COLUMN_COURSE_START + ", " +
                "c." + COLUMN_COURSE_FINISHED + ", " +
                "c." + COLUMN_COURSE_PRICE + ", " +
                "c." + COLUMN_COURSE_TEACHER_ID + ", " +
                "u." + COLUMN_COURSE_FOR_OUTPUT_TEACHER_FIRST_NAME + ", " +
                "u." + COLUMN_COURSE_FOR_OUTPUT_TEACHER_LAST_NAME + ", " +
                "c." + COLUMN_COURSE_SCHEDULE + ", " +
                "c." + COLUMN_COURSE_NOTES + " " +
                "FROM Courses c " +
                "LEFT JOIN Users u ON c." + COLUMN_COURSE_TEACHER_ID + " = u.user_id " +
                "LIMIT :from, :offset ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("from", (dataNavigator.getCurrentNumberPage() - 1) * dataNavigator.getRowPerPage());
        parameters.addValue("offset", dataNavigator.getRowPerPage());

        List<CourseForOutput> courses = jdbcTemplate.query(query, parameters, new CourseForOutputRowMapper());

        String allCourses = courses.stream()
                .map((CourseForOutput c) -> c.getCourse().getName())
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

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes " +
                "FROM Courses c WHERE id=:id";
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
                    .setId(resultSet.getInt(COLUMN_COURSE_ID))
                    .setName(resultSet.getString(COLUMN_COURSE_NAME))
                    .setStart(resultSet.getDate(COLUMN_COURSE_START).toLocalDate())
                    .setFinished(resultSet.getInt(COLUMN_COURSE_FINISHED) != 0)
                    .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                    .setTeacherID(resultSet.getInt(COLUMN_COURSE_TEACHER_ID))
                    .setSchedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                    .setNotes(resultSet.getString(COLUMN_COURSE_NOTES))
                    .getInstance();
        }
    }

    private static final class CourseForOutputRowMapper implements RowMapper<CourseForOutput> {
        public CourseForOutput mapRow(ResultSet resultSet, int i) throws SQLException {
            return new CourseForOutput(
                    resultSet.getString(COLUMN_COURSE_FOR_OUTPUT_TEACHER_FIRST_NAME),
                    resultSet.getString(COLUMN_COURSE_FOR_OUTPUT_TEACHER_LAST_NAME),
                    new Course.Builder()
                            .setId(resultSet.getInt(COLUMN_COURSE_ID))
                            .setName(resultSet.getString(COLUMN_COURSE_NAME))
                            .setStart(resultSet.getDate(COLUMN_COURSE_START).toLocalDate())
                            .setFinished(resultSet.getInt(COLUMN_COURSE_FINISHED) != 0)
                            .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                            .setTeacherID(resultSet.getInt(COLUMN_COURSE_TEACHER_ID))
                            .setSchedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                            .setNotes(resultSet.getString(COLUMN_COURSE_NOTES))
                            .getInstance());
        }
    }

    private MapSqlParameterSource getCourseParameters(Course course) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", course.getName());
        namedParameters.addValue("start", course.getStart());
        namedParameters.addValue("finished", course.getFinished());
        namedParameters.addValue("price", course.getPrice());
        namedParameters.addValue("teacher_id", course.getTeacherID());
        namedParameters.addValue("schedule", course.getSchedule());
        namedParameters.addValue("notes", course.getNotes());
        return namedParameters;
    }

    @Override
    public List<CourseForOutput> getAllCoursesForOutputWithDept() throws DataAccessException {
        LOGGER.debug("Try select all courses where user has dept.");

        String query = "SELECT DISTINCT " +
                "c." + COLUMN_COURSE_ID + ", " +
                "c." + COLUMN_COURSE_NAME + ", " +
                "c." + COLUMN_COURSE_START + ", " +
                "c." + COLUMN_COURSE_FINISHED + ", " +
                "c." + COLUMN_COURSE_PRICE + ", " +
                "c." + COLUMN_COURSE_TEACHER_ID + ", " +
                "u." + COLUMN_COURSE_FOR_OUTPUT_TEACHER_FIRST_NAME + ", " +
                "u." + COLUMN_COURSE_FOR_OUTPUT_TEACHER_LAST_NAME + ", " +
                "c." + COLUMN_COURSE_SCHEDULE + ", " +
                "c." + COLUMN_COURSE_NOTES + " " +
                "FROM Courses c " +
                "LEFT JOIN Users u ON c." + COLUMN_COURSE_TEACHER_ID + " = u.user_id " +
                "INNER JOIN Accounting a ON c." + COLUMN_COURSE_ID + " = a.course_id " +
                "WHERE a.debt > 0 ";

        return jdbcTemplate.query(query, new CourseForOutputRowMapper());
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
