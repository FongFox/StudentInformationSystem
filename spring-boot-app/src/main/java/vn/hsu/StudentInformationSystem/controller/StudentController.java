package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.model.Tuition;
import vn.hsu.StudentInformationSystem.service.CourseService;
import vn.hsu.StudentInformationSystem.service.StudentService;
import vn.hsu.StudentInformationSystem.service.TuitionService;
import vn.hsu.StudentInformationSystem.service.dto.*;
import vn.hsu.StudentInformationSystem.service.mapper.CourseExamMapper;
import vn.hsu.StudentInformationSystem.service.mapper.CourseMapper;
import vn.hsu.StudentInformationSystem.service.mapper.StudentMapper;
import vn.hsu.StudentInformationSystem.service.mapper.TuitionMapper;
import vn.hsu.StudentInformationSystem.util.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for student-related operations:
 * - Retrieve own profile
 * - Change password
 * - View grades for a specific semester
 * - View exam schedule for a specific semester
 * - (TODO) View tuition details
 */
@RestController
@RequestMapping("api/v1/students/me")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final TuitionService tuitionService;

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final CourseExamMapper courseExamMapper;
    private final TuitionMapper tuitionMapper;

    public StudentController(StudentService studentService, CourseService courseService, TuitionService tuitionService, StudentMapper studentMapper, CourseMapper courseMapper, CourseExamMapper courseExamMapper, TuitionMapper tuitionMapper) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.tuitionService = tuitionService;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.courseExamMapper = courseExamMapper;
        this.tuitionMapper = tuitionMapper;
    }

    /**
     * GET  /api/v1/students/me
     * <p>
     * Return the profile of the currently authenticated student.
     *
     * @return StudentProfileResponse wrapped in HTTP 200
     */
    @GetMapping("")
    public ResponseEntity<StudentProfileResponse> fetchAccount() {
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);
        StudentProfileResponse studentProfileResponse = this.studentMapper.toProfile(dbStudent);

        return ResponseEntity.status(HttpStatus.OK).body(studentProfileResponse);
    }

    /**
     * PATCH  /api/v1/students/me/pwd
     * <p>
     * Change the password of the currently authenticated student.
     *
     * @param request JSON body containing the new password
     * @return Plain text confirmation with HTTP 200
     */
    @PatchMapping("pwd")
    public ResponseEntity<String> updateStudentPassword(@RequestBody PasswordChangeRequest request) {
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);

        this.studentService.handleUpdateStudentPassword(dbStudent.getId(), request.getNewPassword());

        return ResponseEntity.status(HttpStatus.OK).body("Password Updated!");
    }

    /**
     * GET  /api/v1/students/me/grades/{semesterCode}
     * <p>
     * Retrieve the list of courses and corresponding grades for the
     * authenticated student in the specified semester.
     *
     * @param semesterCode business key identifying the semester
     * @return List of CourseGradeResponse wrapped in HTTP 200
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

    /**
     * GET  /api/v1/students/me/exam/{semesterCode}
     * <p>
     * Retrieve the exam schedule (date and time) for the authenticated student
     * in the specified semester.
     *
     * @param semesterCode business key identifying the semester
     * @return List of CourseExamResponse wrapped in HTTP 200
     */
    @GetMapping("exam/{semesterCode}")
    public ResponseEntity<List<CourseExamResponse>> fetchStudentCourseExam(@PathVariable("semesterCode") long semesterCode) {
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

    /**
     * GET  /api/v1/students/me/tuition
     * <p>
     *
     * @return Retrieve the tuition details for the authenticated student.
     */
    @GetMapping("tuition")
    public ResponseEntity<List<TuitionResponse>> fetchStudentTuition() {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        Student me = studentService.handleFetchStudentByUsername(username);

        List<Tuition> tuitionList = this.tuitionService.handleFetchAllTuitionByStudent(me);

        List<TuitionResponse> tuitionResponseList = new ArrayList<>();
        for (Tuition tuition : tuitionList) {
            tuitionResponseList.add(this.tuitionMapper.toDto(tuition));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tuitionResponseList);
    }
}
