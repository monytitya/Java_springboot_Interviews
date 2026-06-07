package Springinterview.interviews.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Springinterview.interviews.entity.Course;
import Springinterview.interviews.repository.CourseRepository;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course create(Course c) {
        return courseRepository.save(c);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    public Course update(Long id, Course payload) {
        Course c = courseRepository.findById(id).orElseThrow();
        c.setTitle(payload.getTitle());
        c.setDescription(payload.getDescription());
        return courseRepository.save(c);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    public java.util.List<Springinterview.interviews.entity.Course> findCoursesByStudent(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }
}
