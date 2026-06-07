package Springinterview.interviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Springinterview.interviews.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    java.util.List<Student> findByCourseId(@Param("courseId") Long courseId);

    org.springframework.data.domain.Page<Student> findByTeacherId(Long teacherId,
            org.springframework.data.domain.Pageable pageable);
}
