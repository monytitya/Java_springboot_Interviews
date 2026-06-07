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

import Springinterview.interviews.dto.CourseDto;
import Springinterview.interviews.entity.Course;
import Springinterview.interviews.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final Springinterview.interviews.service.StudentService studentService;

    public CourseController(CourseService courseService,
            Springinterview.interviews.service.StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody CourseDto dto) {
        Course c = new Course();
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        Course created = courseService.create(c);
        dto.setId(created.getId());
        return ResponseEntity.created(URI.create("/api/courses/" + created.getId())).body(dto);
    }

    @GetMapping
    public List<CourseDto> list() {
        return courseService.findAll().stream().map(c -> {
            CourseDto d = new CourseDto();
            d.setId(c.getId());
            d.setTitle(c.getTitle());
            d.setDescription(c.getDescription());
            return d;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> get(@PathVariable Long id) {
        try {
            Course c = courseService.findById(id);
            CourseDto d = new CourseDto();
            d.setId(c.getId());
            d.setTitle(c.getTitle());
            d.setDescription(c.getDescription());
            return ResponseEntity.ok(d);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@PathVariable Long id, @RequestBody CourseDto dto) {
        Course payload = new Course();
        payload.setTitle(dto.getTitle());
        payload.setDescription(dto.getDescription());
        Course updated = courseService.update(id, payload);
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    public java.util.List<Springinterview.interviews.dto.StudentDto> students(@PathVariable Long id) {
        return studentService.findStudentsByCourse(id).stream().map(s -> {
            Springinterview.interviews.dto.StudentDto d = new Springinterview.interviews.dto.StudentDto();
            d.setId(s.getId());
            d.setName(s.getName());
            d.setEmail(s.getEmail());
            d.setCourseIds(s.getCourses().stream().map(Springinterview.interviews.entity.Course::getId)
                    .collect(java.util.stream.Collectors.toSet()));
            return d;
        }).collect(java.util.stream.Collectors.toList());
    }
}
