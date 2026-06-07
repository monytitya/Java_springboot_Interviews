package Springinterview.interviews.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import Springinterview.interviews.entity.Student;
import Springinterview.interviews.entity.Teacher;
import Springinterview.interviews.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody Teacher t) {
        Teacher created = teacherService.create(t);
        return ResponseEntity.created(URI.create("/api/teachers/" + created.getId())).body(created);
    }

    @GetMapping
    public java.util.List<Teacher> list() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> get(@PathVariable Long id) {
        try {
            Teacher t = teacherService.findById(id);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher payload) {
        Teacher updated = teacherService.update(id, payload);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    public Page<StudentDto> students(@PathVariable Long id, Pageable pageable) {
        Page<Student> page = teacherService.studentsByTeacher(id, pageable);
        return page.map(s -> {
            StudentDto d = new StudentDto();
            d.setId(s.getId());
            d.setName(s.getName());
            d.setEmail(s.getEmail());
            d.setTeacherId(s.getTeacher() != null ? s.getTeacher().getId() : null);
            d.setCourseIds(s.getCourses().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toSet()));
            return d;
        });
    }
}
