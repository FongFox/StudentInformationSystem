package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.CourseService;
import vn.hsu.StudentInformationSystem.service.StudentService;
import vn.hsu.StudentInformationSystem.service.dto.CourseGradeResponse;
import vn.hsu.StudentInformationSystem.service.dto.GradeQueryRequest;
import vn.hsu.StudentInformationSystem.service.dto.PasswordChangeRequest;
import vn.hsu.StudentInformationSystem.service.dto.StudentProfileResponse;
import vn.hsu.StudentInformationSystem.service.mapper.CourseMapper;
import vn.hsu.StudentInformationSystem.service.mapper.StudentMapper;
import vn.hsu.StudentInformationSystem.util.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public StudentController(StudentService studentService, CourseService courseService, StudentMapper studentMapper, CourseMapper courseMapper) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
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

    /**
     * POST /api/v1/students/me/grades
     * Body: { "semesterCode": 2431 }
     */
    @GetMapping("grades")
    public ResponseEntity<List<CourseGradeResponse>> fetchStudentCourseGrade(
            @RequestBody GradeQueryRequest gradeQueryRequest
    ) {
        //1. Lấy username từ token
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("User not authenticated"));
        //2. Lấy student từ db
        Student student = this.studentService.handleFetchStudentByUsername(username);
        //3. Lấy course + grade
        List<Course> courseList = this.courseService.handleFetchCoursesByStudentAndSemesterCode(
                student.getId(), gradeQueryRequest.getSemesterCode()
        );
        //4. Map sang DTO
        List<CourseGradeResponse> courseGradeResponseList = new ArrayList<>();
        for (Course course : courseList) {
            courseGradeResponseList.add(this.courseMapper.toDto(course));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseGradeResponseList);
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
