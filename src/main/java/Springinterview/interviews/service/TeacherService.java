package Springinterview.interviews.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Springinterview.interviews.entity.Student;
import Springinterview.interviews.entity.Teacher;
import Springinterview.interviews.repository.StudentRepository;
import Springinterview.interviews.repository.TeacherRepository;

@Service
@Transactional
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public Teacher create(Teacher t) {
        return teacherRepository.save(t);
    }

    public org.springframework.data.domain.Page<Teacher> findAll(org.springframework.data.domain.Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public Teacher update(Long id, Teacher payload) {
        Teacher t = teacherRepository.findById(id).orElseThrow();
        t.setName(payload.getName());
        t.setEmail(payload.getEmail());
        return teacherRepository.save(t);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

    public Page<Student> studentsByTeacher(Long teacherId, Pageable pageable) {
        return studentRepository.findByTeacherId(teacherId, pageable);
    }
}
