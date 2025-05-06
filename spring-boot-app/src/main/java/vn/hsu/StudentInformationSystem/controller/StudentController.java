package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;
import vn.hsu.StudentInformationSystem.service.dto.PasswordChangeRequest;
import vn.hsu.StudentInformationSystem.service.dto.StudentProfileResponse;
import vn.hsu.StudentInformationSystem.service.mapper.StudentMapper;
import vn.hsu.StudentInformationSystem.util.SecurityUtils;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("me")
    public ResponseEntity<StudentProfileResponse> fetchAccount() {
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);
        StudentProfileResponse studentProfileResponse = this.studentMapper.toProfile(dbStudent);

        return ResponseEntity.status(HttpStatus.OK).body(studentProfileResponse);
    }

    @PatchMapping("me/pwd")
    public ResponseEntity<String> updateStudentPassword(@RequestBody PasswordChangeRequest request) {
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);

        this.studentService.handleUpdateStudentPassword(dbStudent.getId(), request.getNewPassword());

        return ResponseEntity.status(HttpStatus.OK).body("Password Updated!");
    }

    // Todo
    @GetMapping("grades")
    public ResponseEntity<Void> fetchStudentCourseGrade() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Todo
    @GetMapping("exam")
    public ResponseEntity<Void> fetchStudentExam() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Todo
    @GetMapping("tuition")
    public ResponseEntity<Void> fetchStudentTuition() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
