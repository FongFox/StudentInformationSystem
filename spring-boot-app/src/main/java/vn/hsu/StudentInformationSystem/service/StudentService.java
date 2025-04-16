package vn.hsu.StudentInformationSystem.service;

import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.repository.IStudentRepository;

@Service
public class StudentService {
    private final IStudentRepository iStudentRepository;

    public StudentService(IStudentRepository iStudentRepository) {
        this.iStudentRepository = iStudentRepository;
    }

    public void handleCreateStudent(Student newStudent) {
        this.iStudentRepository.save(newStudent);
    }

    public long handleCheckStudentQuantity() {
        return iStudentRepository.count();
    }
}
