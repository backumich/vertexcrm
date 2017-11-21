package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.DtoCourseUser;
import ua.com.vertex.beans.User;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.dao.interfaces.DaoUtilInf;
import ua.com.vertex.utils.DataNavigator;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String SEARCH_PARAM = "searchParam";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    private DaoUtilInf daoUtil;

    @Override
    public List<Course> getCoursesPerPage(DataNavigator dataNavigator) {

        LOGGER.debug("Get all courses list");

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name, u.email " +
                "FROM Courses c  INNER JOIN Users u ON u.user_id = c.teacher_id " +
                "LIMIT :from, :offset";

        MapSqlParameterSource parameters = daoUtil.getPagingSQLParameters(dataNavigator);

        List<Course> courses = jdbcTemplate.query(query, parameters, (resultSet, i) -> mapCourses(resultSet));

        LOGGER.debug("Quantity courses: {}; Courses list: {}",
                () -> courses.size(), () -> courses.stream().map(Course::getName).collect(Collectors.joining("|")));

        return courses;
    }

    @Override
    public List<Course> getCoursesPerPage(DataNavigator dataNavigator, User teacher) {

        LOGGER.debug("Get courses list, where teacher is {}", teacher);

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_id, c.schedule, c.notes, " +
                "u.first_name, u.last_name, u.email " +
                "FROM Courses c INNER JOIN Users u ON c.teacher_id = :teacher_id AND u.user_id = :teacher_id " +
                "LIMIT :from, :offset";

        MapSqlParameterSource parameters = daoUtil.getPagingSQLParameters(dataNavigator);
        parameters.addValue("teacher_id", teacher.getUserId());

        List<Course> courses = jdbcTemplate.query(query, parameters, (resultSet, i) -> mapCourses(resultSet));

        LOGGER.debug("Quantity courses: {}; Courses list: {}",
                () -> courses.size(), () -> courses.stream().map(Course::getName).collect(Collectors.joining("|")));

        return courses;
    }

    @Override
    public int getQuantityCourses() {
        LOGGER.debug("Get count courses");
        String query = "SELECT count(*) FROM Courses";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), int.class);
    }

    @Override
    public int getQuantityCourses(User teacher) {
        LOGGER.debug("Get count courses, where teacher is {}", teacher);
        String query = "SELECT count(*) FROM Courses WHERE teacher_id = :teacher_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("teacher_id", teacher.getUserId());

        return jdbcTemplate.queryForObject(query, parameters, int.class);
    }

    @Override
    public int addCourse(Course course) {
        LOGGER.debug("Call - CourseDaoImpl.addCourse({})", course);

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
        LOGGER.debug("Call courseDaoInf.searchCourseByNameAndStatus({}, {})", name, isFinished);

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
        LOGGER.debug("Call courseDaoInf.updateCourseExceptPrice({})", course);

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

        LOGGER.debug("Try update course except price by id -({})", course.getId());
        return jdbcTemplate.update(query, source);
    }

    @Override
    public Optional<Course> getCourseById(int courseId) {
        LOGGER.debug("Call courseDaoInf.getCourseById({})", courseId);

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
            LOGGER.warn("The course with id - {} was not found.", courseId);
        }

        return Optional.ofNullable(course);
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

    @Override
    public List<User> getUsersAssignedToCourse(int courseId) {
        LOGGER.debug("Retrieving users assigned to the course (id={})", courseId);

        List<User> users;
        String query = "SELECT cu.user_id, u.email, u.first_name, u.last_name, u.phone FROM Course_users cu " +
                "INNER JOIN Users u ON cu.user_id=u.user_id WHERE course_id=:courseId";

        users = jdbcTemplate.query(query, new MapSqlParameterSource(COURSE_ID, courseId), this::mapUser);
        LOGGER.debug("Users retrieved with email=({})", userIdsToString(users));

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

    @Override
    public void removeUserFromCourse(DtoCourseUser dto) {
        LOGGER.debug("Removing the user id={} from the course id={}",
                dto.getUserId(), dto.getCourseId());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COURSE_ID, dto.getCourseId());
        mapSqlParameterSource.addValue(USER_ID, dto.getUserId());

        String query = "DELETE FROM Course_users WHERE user_id=:userId AND course_id=:courseId";
        jdbcTemplate.update(query, mapSqlParameterSource);

        LOGGER.debug("User was removed");
    }

    @Override
    public void assignUserToCourse(DtoCourseUser dto) {
        LOGGER.debug("Assigning the user id=%d to the course id={}",
                dto.getUserId(), dto.getCourseId());

        String query = "INSERT INTO Course_users (course_id, user_id) VALUES (:courseId, :userId)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(COURSE_ID, dto.getCourseId());
        mapSqlParameterSource.addValue(USER_ID, dto.getUserId());

        jdbcTemplate.update(query, mapSqlParameterSource);

        LOGGER.debug("User was assigned to the course");
    }

    @Override
    public List<User> searchForUsersToAssign(DtoCourseUser dto) {
        LOGGER.debug("Searching for users to assign to the course by search param={}",
                dto.getSearchParam());

        List<User> users;
        String query = "";
        switch (dto.getSearchType()) {
            case "First Name":
                query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                        "WHERE u.first_name LIKE :searchParam AND " +
                        "(SELECT count(*) FROM Course_users cu " +
                        "WHERE cu.course_id=:courseId AND cu.user_id=u.user_id) = 0";
                break;
            case "Last Name":
                query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                        "WHERE u.last_name LIKE :searchParam AND " +
                        "(SELECT count(*) FROM Course_users cu " +
                        "WHERE cu.course_id=:courseId AND cu.user_id=u.user_id) = 0";
                break;
            case "Email":
                query = "SELECT u.user_id, u.email, u.first_name, u.last_name, u.phone FROM Users u " +
                        "WHERE u.email LIKE :searchParam AND " +
                        "(SELECT count(*) FROM Course_users cu " +
                        "WHERE cu.course_id=:courseId AND cu.user_id=u.user_id) = 0";
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(SEARCH_PARAM, "%" + dto.getSearchParam() + "%");
        mapSqlParameterSource.addValue(COURSE_ID, dto.getCourseId());

        users = jdbcTemplate.query(query, mapSqlParameterSource, this::mapUser);
        LOGGER.debug("Users retrieved with email=({})", userIdsToString(users));

        return users;
    }

    @Autowired
    public CourseDaoImpl(@Qualifier(value = "DS") DataSource dataSource, DaoUtilInf daoUtil) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.daoUtil = daoUtil;
    }
}
