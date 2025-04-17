package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

public interface StudentService {
    public Student handleCreateStudent(Student newStudent);

    public long handleCheckStudentQuantity();

    public void initSampleData();

    public void handleDeleteStudentById(long id);

    public Student handleFetchStudentById(long id);

    public List<Student> handleFetchStudentList();
}
