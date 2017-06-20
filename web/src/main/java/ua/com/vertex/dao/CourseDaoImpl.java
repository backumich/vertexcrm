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
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class CourseDaoImpl implements CourseDaoInf {

    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_NAME = "name";
    private static final String COLUMN_COURSE_START = "start";
    private static final String COLUMN_COURSE_FINISHED = "finished";
    private static final String COLUMN_COURSE_PRICE = "price";
    private static final String COLUMN_COURSE_TEACHER_NAME = "teacher_name";
    private static final String COLUMN_COURSE_SCHEDULE = "schedule";
    private static final String COLUMN_COURSE_NOTES = "notes";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    private MapSqlParameterSource addParamsToMapSqlParameterSourceFromCourse(Course course) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_COURSE_NAME, course.getName());
        source.addValue(COLUMN_COURSE_START, course.getStart());
        source.addValue(COLUMN_COURSE_FINISHED, course.isFinished());
        source.addValue(COLUMN_COURSE_PRICE, course.getPrice());
        source.addValue(COLUMN_COURSE_TEACHER_NAME, course.getTeacherName());
        source.addValue(COLUMN_COURSE_SCHEDULE, course.getSchedule());
        source.addValue(COLUMN_COURSE_NOTES, course.getNotes());
        return source;
    }

    @Override
    public int createCourse(Course course) throws DataAccessException {
        LOGGER.debug(String.format("Try insert course -(%s)", course));
        String query = "INSERT INTO Courses (name, start, finished, price, teacher_name, schedule, notes) " +
                "VALUES(:name, :start, :finished, :price, :teacher_name, :schedule, :notes)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, addParamsToMapSqlParameterSourceFromCourse(course), keyHolder);

        LOGGER.debug(String.format("User added, user id -(%s) ;", keyHolder.getKey().toString()));
        return keyHolder.getKey().intValue();
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

    @Override
    public List<Course> searchCourseByNameAndStatus(Course course) throws DataAccessException {
        LOGGER.debug(String.format("Search user by name - (%s) and finished - (%s).", course.getName(), course.isFinished()));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.schedule, c.notes " +
                "FROM Courses c WHERE name LIKE  '%" + course.getName() + "%' AND finished=:finished";

        return jdbcTemplate.query(query, new MapSqlParameterSource(COLUMN_COURSE_FINISHED, course.isFinished() ? 1 : 0),
                (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(COLUMN_COURSE_ID))
                        .setName(resultSet.getString(COLUMN_COURSE_NAME))
                        .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                        .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                        .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                        .setTeacherName(resultSet.getString(COLUMN_COURSE_TEACHER_NAME))
                        .setShedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                        .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
    }

    @Override
    public int updateCourseExceptPrice(Course course) throws DataAccessException {
        LOGGER.debug(String.format("Try update course except price by id -(%s)", course.getId()));

        String query = "UPDATE Courses SET name=:name, start=:start, finished=:finished, teacher_name=:teacher_name," +
                "schedule=:schedule, notes=:notes WHERE id=:id";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_COURSE_ID, course.getId());
        source.addValue(COLUMN_COURSE_NAME, course.getName());
        source.addValue(COLUMN_COURSE_START, course.getStart());
        source.addValue(COLUMN_COURSE_FINISHED, course.isFinished());
        source.addValue(COLUMN_COURSE_TEACHER_NAME, course.getTeacherName());
        source.addValue(COLUMN_COURSE_SCHEDULE, course.getSchedule());
        source.addValue(COLUMN_COURSE_NOTES, course.getNotes());

        return jdbcTemplate.update(query, source);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws DataAccessException {
        LOGGER.debug(String.format("Try get course by id -(%s)", courseId));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.schedule, c.notes " +
                "FROM Courses c WHERE id=:id";

        Course course = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(COLUMN_COURSE_ID, courseId),
                (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(COLUMN_COURSE_ID))
                        .setName(resultSet.getString(COLUMN_COURSE_NAME))
                        .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                        .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                        .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                        .setTeacherName(resultSet.getString(COLUMN_COURSE_TEACHER_NAME))
                        .setShedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                        .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());

        return Optional.ofNullable(course);
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

}
