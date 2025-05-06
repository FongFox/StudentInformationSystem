package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Course;
import vn.hsu.StudentInformationSystem.repository.CourseRepository;
import vn.hsu.StudentInformationSystem.service.CourseService;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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

}
