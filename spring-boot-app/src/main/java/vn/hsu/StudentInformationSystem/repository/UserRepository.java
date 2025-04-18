package vn.hsu.StudentInformationSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.hsu.StudentInformationSystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
