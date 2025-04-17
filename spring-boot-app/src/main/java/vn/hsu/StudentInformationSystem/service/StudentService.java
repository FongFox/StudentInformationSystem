package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.PasswordDto;
import vn.hsu.StudentInformationSystem.model.Student;

import java.util.List;

public interface StudentService {
    public Student handleCreateStudent(Student newStudent);

    public long handleCheckStudentQuantity();

    public void initSampleData();

    public void handleDeleteStudentById(long id);

    public Student handleFetchStudentById(long id);

    public List<Student> handleFetchStudentList();

    public Student handleUpdateStudent(long id, Student student);

    public Student handleUpdateStudentPassword(long id, String password);

    public String handleConvertPasswordDtoToPassword(PasswordDto passwordDto);
}
