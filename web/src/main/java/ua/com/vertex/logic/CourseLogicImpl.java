package ua.com.vertex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.vertex.beans.Course;
import ua.com.vertex.dao.interfaces.CourseDaoInf;
import ua.com.vertex.logic.interfaces.CourseLogic;

import java.util.List;

@Service
public class CourseLogicImpl implements CourseLogic {

    private final CourseDaoInf courseDaoInf;

    @Override
    public List<Course> activeCourses() {
        return courseDaoInf.getAllActiveCourses();
    }

    @Autowired
    public CourseLogicImpl(CourseDaoInf courseDaoInf) {
        this.courseDaoInf = courseDaoInf;
    }
}
