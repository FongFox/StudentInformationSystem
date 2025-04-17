package vn.hsu.StudentInformationSystem.controller.api;

import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public String getStudentList() {
        return "Get Student List Feature!";
    }

    @GetMapping("{id}")
    public String getStudentDetail(@PathVariable long id) {
        return "Get Student Detail Feature!";
    }

    @PostMapping()
    public Student createStudent(@RequestBody Student newStudent) {
        return studentService.handleCreateStudent(newStudent);
    }

    @PutMapping("{id}")
    public String updateStudent(@PathVariable long id) {
        return "Update Student Feature!";
    }

    @DeleteMapping("{id}")
    public String deleteStudent(@PathVariable long id) {
        this.studentService.handleDeleteStudentById(id);
        return "Delete Student Complete!";
    }

}
