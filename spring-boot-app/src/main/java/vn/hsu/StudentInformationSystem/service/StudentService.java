package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

public interface StudentService {
    Student handleCreateStudent(Student student);

    Student handleFetchStudentById(long id);

    Student handleFetchStudentByUsername(String username);

    Student handleFetchStudentByUsernameAndRefreshToken(String username, String token);

    List<Student> handleFetchStudentList();

    void handleUpdateStudentPassword(long id, String password);

    long handleCheckUserQuantity();

    void handleUpdateStudentToken(String token, String username);

    void initSampleData();
}
