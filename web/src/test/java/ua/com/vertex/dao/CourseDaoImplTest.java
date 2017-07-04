package ua.com.vertex.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.beans.Course;
import ua.com.vertex.beans.CourseUserDto;
import ua.com.vertex.beans.User;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.CourseDaoInf;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class CourseDaoImplTest {

    private final String MSG = "Maybe method was changed";

    @Autowired
    private CourseDaoInf courseDaoInf;

    private Course course;
    private User user1;
    private User user3;

    @Before
    public void setUp() throws Exception {
        course = new Course.Builder().setId(3).setName("JavaPro").setFinished(false)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(4000)).setTeacherName("Test").setNotes("Test").getInstance();
        user1 = new User.Builder()
                .setUserId(401)
                .setEmail("user1@email.com")
                .setFirstName("Name1")
                .setLastName("Surname1")
                .setPhone("+38050 111 1111")
                .getInstance();
        user3 = new User.Builder()
                .setUserId(403)
                .setEmail("user3@email.com")
                .setFirstName("Name3")
                .setLastName("Surname3")
                .setPhone("+38050 333 3333")
                .getInstance();
    }

    @Test
    public void createCourseCorrectInsert() throws Exception {
        int courseId = courseDaoInf.createCourse(course);
        course.setId(courseId);
        assertEquals(MSG, courseDaoInf.getCourseById(courseId).orElse(new Course()), course);
    }

    @Test
    public void getAllCoursesWithDeptReturnCorrectData() throws Exception {
        List<Course> courses = courseDaoInf.getAllCoursesWithDept();
        assertFalse(MSG, courses.isEmpty());
        courses.forEach(course1 -> assertTrue(course1.getPrice().intValue() > 0));
    }

    @Test
    public void searchCourseByNameAndStatusReturnCorrectData() throws Exception {
        Course courseForSearch = new Course.Builder().setName("java").setFinished(true).getInstance();
        List<Course> courses = courseDaoInf.searchCourseByNameAndStatus(courseForSearch);
        courses.forEach(course -> assertTrue(course.getName().contains(courseForSearch.getName())
                && course.isFinished()));
    }

    @Test
    public void searchCourseByNameAndStatusReturnEmptyList() throws Exception {
        Course courseForSearch = new Course.Builder().setName("wwwwwwwwwwww").setFinished(false).getInstance();
        assertTrue(courseDaoInf.searchCourseByNameAndStatus(courseForSearch).isEmpty());
    }

    @Test
    public void updateCourseExceptPriceCorrectUpdate() throws Exception {
        Course courseForUpdate = new Course.Builder().setId(1).setName("JavaStart").setFinished(true)
                .setStart(LocalDateTime.of(2017, 2, 1, 10, 10, 10))
                .setPrice(BigDecimal.valueOf(8000)).setTeacherName("After update").setNotes("After update").getInstance();

        courseDaoInf.updateCourseExceptPrice(courseForUpdate);
        assertEquals(MSG, courseForUpdate, courseDaoInf.getCourseById(courseForUpdate.getId()).orElse(new Course()));

    }

    @Test
    public void getCourseByIdReturnCorrectData() throws Exception {
        int courseId = courseDaoInf.createCourse(course);
        course.setId(courseId);
        assertEquals(MSG, courseDaoInf.getCourseById(courseId).orElse(new Course()), course);
    }

    @Test
    public void getCourseByIdReturnEmptyOptional() throws Exception {
        assertFalse(MSG, courseDaoInf.getCourseById(33333).isPresent());
    }

    @Test
    @WithAnonymousUser
    public void getUsersAssignedToCourse1ReturnsNotEmptyListOfUsers() {
        List<User> users = courseDaoInf.getUsersAssignedToCourse(1);
        assertEquals(users.size(), 2);
        assertThat(users, hasItem(user1));
        assertThat(users, not(hasItem(user3)));
    }

    @Test
    @WithAnonymousUser
    public void getUsersAssignedToCourse5ReturnsEmptyListOfUsers() {
        List<User> users = courseDaoInf.getUsersAssignedToCourse(5);
        assertEquals(users.size(), 0);
    }

    @Test
    @WithAnonymousUser
    public void removeUserFromCourse() {
        CourseUserDto dto = new CourseUserDto();
        dto.setCourseId(1);
        dto.setUserId(user1.getUserId());

        assertThat(courseDaoInf.getUsersAssignedToCourse(1), hasItem(user1));
        courseDaoInf.removeUserFromCourse(dto);
        assertThat(courseDaoInf.getUsersAssignedToCourse(1), not(hasItem(user1)));
    }

    @Test
    @WithAnonymousUser
    public void assignUserToCourse() {
        CourseUserDto dto = new CourseUserDto();
        dto.setCourseId(1);
        dto.setUserId(user3.getUserId());
        dto.setEmail(user3.getEmail());
        dto.setFirstName(user3.getFirstName());
        dto.setLastName(user3.getLastName());
        dto.setPhone(user3.getPhone());

        assertThat(courseDaoInf.getUsersAssignedToCourse(1), not(hasItem(user3)));
        courseDaoInf.assignUserToCourse(dto);
        assertThat(courseDaoInf.getUsersAssignedToCourse(1), hasItem(user3));
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByFirstNameFullMatch() {
        CourseUserDto dto = new CourseUserDto();
        String fullMatchingName = "FirstName";
        dto.setSearchType("first_name");
        dto.setSearchParam(fullMatchingName);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() > 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByFirstNamePartialMatch() {
        CourseUserDto dto = new CourseUserDto();
        String partialMatchingName = "F";
        dto.setSearchType("first_name");
        dto.setSearchParam(partialMatchingName);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() > 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByFirstNameNonMatch() {
        CourseUserDto dto = new CourseUserDto();
        String nonMatchingName = "notExistingFirstName";
        dto.setSearchType("first_name");
        dto.setSearchParam(nonMatchingName);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() == 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByLastNameFullMatch() {
        CourseUserDto dto = new CourseUserDto();
        String fullMatchingName = "LastName";
        dto.setSearchType("last_name");
        dto.setSearchParam(fullMatchingName);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() > 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByLastNamePartialMatch() {
        CourseUserDto dto = new CourseUserDto();
        String partialMatchingName = "L";
        dto.setSearchType("last_name");
        dto.setSearchParam(partialMatchingName);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() > 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByLastNameNonMatch() {
        CourseUserDto dto = new CourseUserDto();
        String nonMatchingName = "notExistingLastName";
        dto.setSearchType("last_name");
        dto.setSearchParam(nonMatchingName);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() == 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByEmailFullMatch() {
        CourseUserDto dto = new CourseUserDto();
        String fullMatchingEmail = "22@test.com";
        dto.setSearchType("email");
        dto.setSearchParam(fullMatchingEmail);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() > 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByEmailPartialMatch() {
        CourseUserDto dto = new CourseUserDto();
        String partialMatchingEmail = "@";
        dto.setSearchType("email");
        dto.setSearchParam(partialMatchingEmail);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() > 0);
    }

    @Test
    @WithAnonymousUser
    public void searchUsersByEmailNonMatch() {
        CourseUserDto dto = new CourseUserDto();
        String nonMatchingEmail = "notExistingEmail";
        dto.setSearchType("email");
        dto.setSearchParam(nonMatchingEmail);

        List<User> users = courseDaoInf.searchForUsersToAssign(dto);
        assertTrue(users.size() == 0);
    }
}
