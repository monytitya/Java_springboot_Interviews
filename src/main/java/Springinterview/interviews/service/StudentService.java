package Springinterview.interviews.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Springinterview.interviews.dto.StudentDto;
import Springinterview.interviews.entity.Course;
import Springinterview.interviews.entity.Student;
import Springinterview.interviews.repository.CourseRepository;
import Springinterview.interviews.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Student update(Long id, StudentDto dto) {
        Student s = studentRepository.findById(id).orElseThrow();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        return studentRepository.save(s);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public void enrollCourse(Long studentId, Long courseId) {
        Student s = studentRepository.findById(studentId).orElseThrow();
        Course c = courseRepository.findById(courseId).orElseThrow();
        s.addCourse(c);
        studentRepository.save(s);
    }

    public void removeCourse(Long studentId, Long courseId) {
        Student s = studentRepository.findById(studentId).orElseThrow();
        Course c = courseRepository.findById(courseId).orElseThrow();
        s.removeCourse(c);
        studentRepository.save(s);
    }

    public List<Long> courseIdsOfStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .map(st -> st.getCourses().stream().map(Course::getId).collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<Student> findStudentsByCourse(Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }
}
