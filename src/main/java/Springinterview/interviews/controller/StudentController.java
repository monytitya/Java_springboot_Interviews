package Springinterview.interviews.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Springinterview.interviews.dto.StudentDto;
import Springinterview.interviews.entity.Course;
import Springinterview.interviews.entity.Student;
import Springinterview.interviews.service.CourseService;
import Springinterview.interviews.service.StudentService;
import Springinterview.interviews.service.TeacherService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;

    public StudentController(StudentService studentService, CourseService courseService,
            TeacherService teacherService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}/courses")
    public java.util.List<Springinterview.interviews.dto.CourseDto> courses(@PathVariable Long id) {
        return courseService.findCoursesByStudent(id).stream().map(c -> {
            Springinterview.interviews.dto.CourseDto d = new Springinterview.interviews.dto.CourseDto();
            d.setId(c.getId());
            d.setTitle(c.getTitle());
            d.setDescription(c.getDescription());
            return d;
        }).collect(java.util.stream.Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto dto) {
        Student s = new Student();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        if (dto.getTeacherId() != null) {
            s.setTeacher(teacherService.findById(dto.getTeacherId()));
        }
        Student created = studentService.create(s);
        dto.setId(created.getId());
        return ResponseEntity.created(URI.create("/api/students/" + created.getId())).body(dto);
    }

    @GetMapping
    public List<StudentDto> list() {
        return studentService.findAll().stream().map(s -> {
            StudentDto d = new StudentDto();
            d.setId(s.getId());
            d.setName(s.getName());
            d.setEmail(s.getEmail());
            d.setCourseIds(s.getCourses().stream().map(Course::getId).collect(Collectors.toSet()));
            return d;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> get(@PathVariable Long id) {
        return studentService.findById(id)
                .map(s -> {
                    StudentDto d = new StudentDto();
                    d.setId(s.getId());
                    d.setName(s.getName());
                    d.setEmail(s.getEmail());
                    d.setCourseIds(s.getCourses().stream().map(Course::getId).collect(Collectors.toSet()));
                    return ResponseEntity.ok(d);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto dto) {
        if (dto.getTeacherId() != null) {
            Student s = studentService.findById(id).orElseThrow();
            s.setTeacher(teacherService.findById(dto.getTeacherId()));
            studentService.create(s);
        }
        Student updated = studentService.update(id, dto);
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/courses/{courseId}")
    public ResponseEntity<Void> enroll(@PathVariable Long id, @PathVariable Long courseId) {
        studentService.enrollCourse(id, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    public ResponseEntity<Void> unenroll(@PathVariable Long id, @PathVariable Long courseId) {
        studentService.removeCourse(id, courseId);
        return ResponseEntity.noContent().build();
    }
}
