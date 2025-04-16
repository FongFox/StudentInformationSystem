package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.hsu.StudentInformationSystem.model.Student;

public interface IStudentRepository extends JpaRepository<Student, Integer> {
}
