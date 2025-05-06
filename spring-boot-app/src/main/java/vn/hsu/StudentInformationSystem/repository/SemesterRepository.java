package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hsu.StudentInformationSystem.model.Semester;

import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    Optional<Semester> findSemesterByCode(long code);
}
