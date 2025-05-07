package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.model.Semester;
import vn.hsu.StudentInformationSystem.repository.CourseRepository;
import vn.hsu.StudentInformationSystem.service.CourseService;
import vn.hsu.StudentInformationSystem.service.SemesterService;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final SemesterService semesterService;

    public CourseServiceImpl(CourseRepository courseRepository, SemesterService semesterService) {
        this.courseRepository = courseRepository;
        this.semesterService = semesterService;
    }

    @Override
    public void handleCreateCourse(Course course) {
        this.courseRepository.save(course);
    }

    @Override
    public Course handleFetchCourseById(long id) {
        Optional<Course> optionalCourse = this.courseRepository.findById(id);

        return optionalCourse.orElseThrow(
                () -> new EntityNotFoundException("Course with ID " + id + " not found")
        );
    }

    @Override
    public long handleCheckCourseQuantity() {
        return this.courseRepository.count();
    }

    @Override
    public List<Course> handleFetchCoursesByStudentAndSemesterCode(Long studentId, Long semesterCode) {
        // 1. Resolve Semester entity từ code
        Semester semester = semesterService.handleFetchSemesterByCode(semesterCode);
        // 2. Trả về list enrollment
        return courseRepository.findAllByStudentIdAndSemesterId(studentId, semester.getId());
    }

}
