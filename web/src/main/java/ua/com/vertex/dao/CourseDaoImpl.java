package ua.com.vertex.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.utils.DataNavigator;
import ua.com.vertex.utils.LogInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDaoInf {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LogInfo logInfo;
    private static final Logger LOGGER = LogManager.getLogger(CourseDaoImpl.class);

    @Override
    public List<Course> getAllCourses(DataNavigator dataNavigator) {
        List<Course> courses = new ArrayList<>();

        LOGGER.debug("Get all courses list");

        String query = "SELECT c.id, c.name, c.start, c.finished, c.price, c.teacher_name, c.schedule, c.notes " +
                "FROM Courses c LIMIT :from, :offset";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("from", (dataNavigator.getCurrentNumberPage() - 1) * dataNavigator.getCurrentRowPerPage());
        parameters.addValue("offset", dataNavigator.getCurrentRowPerPage());

        try {
            courses = jdbcTemplate.query(query, parameters, new CourseDaoImpl.CoursesRowMapping());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Something went wrong", e);
        }
        return courses;
    }

    private static final class CoursesRowMapping implements RowMapper<Course> {
        public Course mapRow(ResultSet resultSet, int i) throws SQLException {
            LobHandler handler = new DefaultLobHandler();
            Course course = new Course();
            course.setId(resultSet.getInt("id"));
            course.setName(resultSet.getString("name"));
            course.setStart(resultSet.getDate("start").toLocalDate());
            course.setFinished(resultSet.getInt("finished") != 0);
            course.setPrice(resultSet.getDouble("price"));
            course.setTeacherName(resultSet.getString("teacher_name"));
            course.setSchedule(resultSet.getString("schedule"));
            course.setNotes(resultSet.getString("notes"));
            return course;
        }
    }

    @Override
    public int getQuantityCourses() throws SQLException {
        LOGGER.debug("Get all courses list");
        String query = "SELECT count(*) FROM Courses";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), int.class);
    }

    @Override
    public int addCourse(Course course) throws SQLException {
        LOGGER.info("Adding a new course into database");

        String query = "INSERT INTO courses(name, start, finished, price, teacher_name, schedule, notes) " +
                "VALUES (:name, :start, :finished, :price, :teacher_name, :schedule, :notes)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, getCourseParameters(course), keyHolder);

        Number id = keyHolder.getKey();
        return id.intValue();
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


    @Autowired
    public CourseDaoImpl(DataSource dataSource, LogInfo logInfo) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.logInfo = logInfo;
    }
}
