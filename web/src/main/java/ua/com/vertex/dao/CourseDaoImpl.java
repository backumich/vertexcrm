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
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.utils.DataNavigator;
import ua.com.vertex.utils.LogInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private static final String COURSE_ID = "courseId";
    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String SEARCH_PARAM = "searchParam";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;
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

        List<Course> courses = jdbcTemplate.query(query, parameters, (resultSet, i) -> mapCourses(resultSet));

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
    public List<Course> searchCourseByNameAndStatus(String name, boolean isFinished) {
        LOGGER.debug(String.format("Call courseDaoInf.searchCourseByNameAndStatus(%s, $s)", name, isFinished));

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name, u.email FROM Courses c INNER JOIN Users u ON c.teacher_id = u.user_id " +
                "WHERE c.finished = :finished AND c.name LIKE :courseName";
        MapSqlParameterSource source = new MapSqlParameterSource(FINISHED, isFinished ? 1 : 0);
        source.addValue("courseName", "%" + name + "%");

        LOGGER.debug(String.format("Search course by name - (%s) and finished - (%s).", name, isFinished));
        return jdbcTemplate.query(query, source, (resultSet, i) -> mapCourses(resultSet));
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

    @Override
    public List<User> getUsersAssignedToCourse(int courseId) {
        LOGGER.debug(String.format(logInfo.getId() + " Retrieving users assigned to the course (id=%d)", courseId));

        List<User> users;
        String query = "SELECT user_id, email, first_name, last_name, phone FROM Course_users " +
                "WHERE course_id=:courseId";

        users = jdbcTemplate.query(query, new MapSqlParameterSource(COURSE_ID, courseId), this::mapUser);

        LOGGER.debug(logInfo.getId() + "Retrieved users with email=(" + userIdsToString(users) + ")");

        return users;
    }

    @Override
    public void removeUserFromCourse(CourseUserDto dto) {
        LOGGER.debug(String.format(logInfo.getId() + "Removing the user id=%d from the course id=%d",
                dto.getUserId(), dto.getCourseId()));

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COURSE_ID, dto.getCourseId());
        mapSqlParameterSource.addValue(USER_ID, dto.getUserId());

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
        mapSqlParameterSource.addValue(COURSE_ID, dto.getCourseId());
        mapSqlParameterSource.addValue(USER_ID, dto.getUserId());
        mapSqlParameterSource.addValue(EMAIL, dto.getEmail());
        mapSqlParameterSource.addValue(FIRST_NAME, dto.getFirstName());
        mapSqlParameterSource.addValue(LAST_NAME, dto.getLastName());
        mapSqlParameterSource.addValue(PHONE, dto.getPhone());

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
        mapSqlParameterSource.addValue(COURSE_ID, dto.getCourseId());

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

    private Course mapCourses(ResultSet resultSet) throws SQLException {
        return new Course.Builder().setId(resultSet.getInt(ID))
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
                .setNotes(resultSet.getString(NOTES)).getInstance();
    }

    @Autowired
    public CourseDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
