package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.repository.StudentRepository;
import vn.hsu.StudentInformationSystem.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student handleCreateStudent(Student student) {
        //Hash password for new user
        String hashPassword = this.passwordEncoder.encode(student.getPassword());
        student.setPassword(hashPassword);

        //Save the User temporarily to get an auto-generated ID
        Student savedStudent = this.studentRepository.save(student);

        //Save again with final username
        return this.studentRepository.save(savedStudent);
    }

    @Override
    public Student handleFetchStudentById(long id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);

        return studentOptional.orElseThrow(
                () -> new EntityNotFoundException("Student with ID " + id + " not found")
        );
    }

    @Override
    public Student handleFetchStudentByUsername(String username) {
        // TODO Auto-generated method stub
        Optional<Student> studentOptional = this.studentRepository.findByUsername(username);

        return studentOptional.orElseThrow(
                () -> new EntityNotFoundException("Student with username or password not found")
        );
    }

    @Override
    public List<Student> handleFetchStudentList() {
        return this.studentRepository.findAll();
    }

    @Override
    public void handleUpdateStudentPassword(long id, String password) {
    }

    @Override
    public long handleCheckUserQuantity() {
        return this.studentRepository.count();
    }

    @Override
    public void initSampleData() {
        handleCreateStudent(new Student(22002581, "Trần Gia Nguyên Phong", "phong.tgn02581s", "123456"));
        handleCreateStudent(new Student(22002582, "Student Trần", "student.t02582", "123456"));
    }
}
