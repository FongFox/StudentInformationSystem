package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Semester;

public interface SemesterService {
    void handleCreateSemester(Semester semester);

    Semester handleFetchSemesterByCode(long code);

    long handleCheckSemesterQuantity();
}
