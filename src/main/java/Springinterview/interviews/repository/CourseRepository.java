package Springinterview.interviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Springinterview.interviews.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    java.util.List<Course> findByStudentId(@Param("studentId") Long studentId);
}
