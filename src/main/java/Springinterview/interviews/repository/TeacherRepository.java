package Springinterview.interviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Springinterview.interviews.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
