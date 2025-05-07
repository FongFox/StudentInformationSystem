package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.CourseService;
import vn.hsu.StudentInformationSystem.service.StudentService;
import vn.hsu.StudentInformationSystem.service.dto.CourseExamResponse;
import vn.hsu.StudentInformationSystem.service.dto.CourseGradeResponse;
import vn.hsu.StudentInformationSystem.service.dto.PasswordChangeRequest;
import vn.hsu.StudentInformationSystem.service.dto.StudentProfileResponse;
import vn.hsu.StudentInformationSystem.service.mapper.CourseExamMapper;
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
    private final CourseExamMapper courseExamMapper;

    public StudentController(StudentService studentService, CourseService courseService, StudentMapper studentMapper, CourseMapper courseMapper, CourseExamMapper courseExamMapper) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.courseExamMapper = courseExamMapper;
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
     * GET /api/v1/students/me/grades/{semesterCode}
     * Return the grades for the authenticated student in the given semester.
     */
    @GetMapping("grades/{semesterCode}")
    public ResponseEntity<List<CourseGradeResponse>> fetchStudentCourseGrade(@PathVariable("semesterCode") long semesterCode) {
        // 1. Get username from token
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("User not authenticated!"));

        // 2. Fetch Student entity by username
        Student student = this.studentService.handleFetchStudentByUsername(username);

        // 3. Fetch courses with grades for this student and semesterCode
        List<Course> courseList = this.courseService.handleFetchCoursesByStudentAndSemesterCode(student.getId(), semesterCode);

        // 4. Map each Course to CourseGradeResponse DTOs
        List<CourseGradeResponse> courseGradeResponseList = new ArrayList<>();
        for (Course course : courseList) {
            // map entity to DTO
            courseGradeResponseList.add(this.courseMapper.toDto(course));
        }

        // 5. Return 200 OK with the DTO list
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseGradeResponseList);
    }

    
    @GetMapping("exam/{semesterCode}")
    public ResponseEntity<List<CourseExamResponse>> fetchStudentExam(@PathVariable("semesterCode") long semesterCode) {
        // 1. Lấy username từ token
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        // 2. Lấy Student
        Student me = studentService.handleFetchStudentByUsername(username);

        // 3. Lấy tất cả course đã enroll và có lịch thi
        List<Course> courseList = courseService.handleFetchExamScheduleByStudentAndSemesterCode(me.getId(), semesterCode);

        // 4. Chuyển từng đối tượng Course thành CourseExamResponse DTO
        List<CourseExamResponse> courseExamResponseList = new ArrayList<>();
        for (Course course : courseList) {
            // Map Course entity to CourseExamResponse DTO
            CourseExamResponse courseExamResponse = this.courseExamMapper.toDto(course);
            courseExamResponseList.add(courseExamResponse);
        }

        // 5. Trả về
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseExamResponseList);
    }

    // Todo
    @GetMapping("tuition")
    public ResponseEntity<Void> fetchStudentTuition() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
