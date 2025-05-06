package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Course;

public interface CourseService {
    void handleCreateCourse(Course course);

    Course handleFetchCourseById(long id);

    long handleCheckCourseQuantity();
}
