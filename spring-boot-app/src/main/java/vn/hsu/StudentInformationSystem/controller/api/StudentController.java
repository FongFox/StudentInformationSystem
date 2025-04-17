package vn.hsu.StudentInformationSystem.controller.api;

import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.PasswordDto;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> getStudentList() {
        return this.studentService.handleFetchStudentList();
    }

    @GetMapping("{id}")
    public Student getStudentDetail(@PathVariable long id) {
        return this.studentService.handleFetchStudentById(id);
    }

    @PostMapping()
    public Student createStudent(@RequestBody Student newStudent) {
        return studentService.handleCreateStudent(newStudent);
    }

    @PutMapping("{id}")
    public String updateStudent(@PathVariable long id, @RequestBody Student student) {
        return "Update student feature!";
    }

    @PatchMapping("{id}/pwd")
    public Student updateStudentPassword(@PathVariable long id, @RequestBody PasswordDto passwordDto) {
        return this.studentService.handleUpdateStudentPassword(
                id,
                this.studentService.handleConvertPasswordDtoToPassword(passwordDto)
        );
    }

    @DeleteMapping("{id}")
    public String deleteStudent(@PathVariable long id) {
        this.studentService.handleDeleteStudentById(id);
        return "Delete Student Complete!";
    }

}
