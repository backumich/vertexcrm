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
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.utils.DataNavigator;
import ua.com.vertex.utils.LogInfo;
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
    private static final String COLUMN_COURSE_TEACHER_NAME = "teacher_name";
    private static final String COLUMN_COURSE_SCHEDULE = "schedule";
    private static final String COLUMN_COURSE_NOTES = "notes";
    private static final String COLUMN_COURSE_ID2 = "courseId";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_PHONE = "phone";
    private static final String SEARCH_PARAM = "searchParam";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    @Override
    public int createCourse(Course course) throws DataAccessException {
        LOGGER.debug(String.format("Try insert course -(%s)", course));
        String query = "INSERT INTO Courses (name, start, finished, price, teacher_name, schedule, notes) " +
                "VALUES(:name, :start, :finished, :price, :teacher_name, :schedule, :notes)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(COLUMN_COURSE_NAME, course.getName());
        source.addValue(COLUMN_COURSE_START, course.getStart());
        source.addValue(COLUMN_COURSE_FINISHED, course.isFinished());
        source.addValue(COLUMN_COURSE_PRICE, course.getPrice());
        source.addValue(COLUMN_COURSE_TEACHER_NAME, course.getTeacherName());
        source.addValue(COLUMN_COURSE_SCHEDULE, course.getSchedule());
        source.addValue(COLUMN_COURSE_NOTES, course.getNotes());

        jdbcTemplate.update(query, source, keyHolder);
        LOGGER.debug(String.format("User added, user id -(%s) ;", keyHolder.getKey().toString()));
        return keyHolder.getKey().intValue();
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
        LOGGER.debug("Try select all courses where user has dept.");
        String query = "SELECT DISTINCT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.notes FROM Courses c " +
                "INNER JOIN Accounting a ON  a.course_id = c.id WHERE a.debt > 0";

        return jdbcTemplate.query(query, (resultSet, i) -> new Course.Builder()
                .setId(resultSet.getInt(COLUMN_COURSE_ID))
                .setName(resultSet.getString(COLUMN_COURSE_NAME))
                .setStart(resultSet.getDate(COLUMN_COURSE_START).toLocalDate())
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
        Course course = null;
        try {
            course = jdbcTemplate.queryForObject(query, new MapSqlParameterSource(COLUMN_COURSE_ID, courseId),
                    (resultSet, i) -> new Course.Builder().setId(resultSet.getInt(COLUMN_COURSE_ID))
                            .setName(resultSet.getString(COLUMN_COURSE_NAME))
                            .setStart(resultSet.getTimestamp(COLUMN_COURSE_START).toLocalDateTime())
                            .setFinished((resultSet.getInt(COLUMN_COURSE_FINISHED) == 1))
                            .setPrice(resultSet.getBigDecimal(COLUMN_COURSE_PRICE))
                            .setTeacherName(resultSet.getString(COLUMN_COURSE_TEACHER_NAME))
                            .setShedule(resultSet.getString(COLUMN_COURSE_SCHEDULE))
                            .setNotes(resultSet.getString(COLUMN_COURSE_NOTES)).getInstance());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("The course with id - %s was not found.", courseId));
        }

        return Optional.ofNullable(course);
    }

    @Override
    public List<User> getUsersAssignedToCourse(int courseId) {
        LOGGER.debug(String.format(logInfo.getId() + " Retrieving users assigned to the course (id=%d)", courseId));

        List<User> users;
        String query = "SELECT user_id, email, first_name, last_name, phone FROM Course_users " +
                "WHERE course_id=:courseId";

        users = jdbcTemplate.query(query, new MapSqlParameterSource(COLUMN_COURSE_ID2, courseId), this::mapUser);

        LOGGER.debug(logInfo.getId() + "Retrieved users with email=(" + userIdsToString(users) + ")");

        return users;
    }

    @Override
    public void removeUserFromCourse(CourseUserDto dto) {
        LOGGER.debug(String.format(logInfo.getId() + "Removing the user id=%d from the course id=%d",
                dto.getUserId(), dto.getCourseId()));

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COLUMN_COURSE_ID2, dto.getCourseId());
        mapSqlParameterSource.addValue(COLUMN_USER_ID, dto.getUserId());

        String query = "DELETE FROM Course_users WHERE user_id=:userId AND course_id=:courseId";
        jdbcTemplate.update(query, mapSqlParameterSource);

        LOGGER.debug(logInfo.getId() + "User was removed");
    }

    @Override
    public void assignUserToCourse(CourseUserDto dto) {
        LOGGER.debug(String.format("Assigning the user id=%d to the course id=%d",
                dto.getUserId(), dto.getCourseId()));

        String query = "INSERT INTO Course_users (course_id, user_id, email, first_name, last_name, phone) " +
                "VALUES (:courseId, :userId, :email, :firstName, :lastName, :phone)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COLUMN_COURSE_ID2, dto.getCourseId());
        mapSqlParameterSource.addValue(COLUMN_USER_ID, dto.getUserId());
        mapSqlParameterSource.addValue(COLUMN_EMAIL, dto.getEmail());
        mapSqlParameterSource.addValue(COLUMN_FIRST_NAME, dto.getFirstName());
        mapSqlParameterSource.addValue(COLUMN_LAST_NAME, dto.getLastName());
        mapSqlParameterSource.addValue(COLUMN_PHONE, dto.getPhone());

        jdbcTemplate.update(query, mapSqlParameterSource);

        LOGGER.debug("User was assigned to the course");
    }

    @Override
    public List<User> searchForUsersToAssign(CourseUserDto dto) {
        LOGGER.debug(String.format(logInfo.getId() + "Searching for users to assign to the course by search param=%s",
                dto.getSearchParam()));

        List<User> users;
        String query = "";
        switch (dto.getSearchType()) {
            case "first_name":
                query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                        "WHERE u.first_name LIKE '%" + dto.getSearchParam() + "%' AND " +
                        "(SELECT count(*) FROM Course_users c WHERE c.email=u.email AND c.course_id=:courseId) = 0";
                break;
            case "last_name":
                query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                        "WHERE u.last_name LIKE '%" + dto.getSearchParam() + "%' AND " +
                        "(SELECT count(*) FROM Course_users c WHERE c.email=u.email AND c.course_id=:courseId) = 0";
                break;
            case "email":
                query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                        "WHERE u.email LIKE '%" + dto.getSearchParam() + "%' AND " +
                        "(SELECT count(*) FROM Course_users c WHERE c.email=u.email AND c.course_id=:courseId) = 0";
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(SEARCH_PARAM, dto.getSearchParam());
        mapSqlParameterSource.addValue(COLUMN_COURSE_ID2, dto.getCourseId());

        users = jdbcTemplate.query(query, mapSqlParameterSource, this::mapUser);

        LOGGER.debug(logInfo.getId() + "Retrieved users id=(" + userIdsToString(users) + ")");

        return users;
    }

    private User mapUser(ResultSet resultSet, int i) throws SQLException {
        return new User.Builder()
                .setUserId(resultSet.getInt("user_id"))
                .setEmail(resultSet.getString("email"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPhone(resultSet.getString("phone"))
                .getInstance();
    }

    private String userIdsToString(List<User> users) {
        return users.stream().map(User::getUserId).map(String::valueOf).collect(Collectors.joining(", "));
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
