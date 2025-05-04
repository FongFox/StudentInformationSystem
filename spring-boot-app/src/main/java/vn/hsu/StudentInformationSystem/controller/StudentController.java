package vn.hsu.StudentInformationSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;
import vn.hsu.StudentInformationSystem.service.dto.StudentProfileResponse;
import vn.hsu.StudentInformationSystem.service.mapper.StudentMapper;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentProfileResponse> fetchStudentById(@PathVariable long id) {
        Student dbStudent = this.studentService.handleFetchStudentById(id);
        StudentProfileResponse studentProfileResponse = this.studentMapper.toProfile(dbStudent);

        return ResponseEntity.status(HttpStatus.OK).body(studentProfileResponse);
    }
}
