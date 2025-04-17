package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Student;

public interface StudentService {
    public Student handleCreateStudent(Student newStudent);

    public long handleCheckStudentQuantity();

    public void initSampleData();

    public void handleDeleteStudentById(long id);
}
