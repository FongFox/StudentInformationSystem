package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private String name;

    private double grade;

    private int semester;

    @Column(name = "final_exam_date")
    private LocalDate finalExamDate;

    @Column(name = "final_exam_time")
    private LocalTime finalExamTime;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "fk_course_student")
    )
    private Student student;

    public Course() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public LocalDate getFinalExamDate() {
        return finalExamDate;
    }

    public void setFinalExamDate(LocalDate finalExamDate) {
        this.finalExamDate = finalExamDate;
    }

    public LocalTime getFinalExamTime() {
        return finalExamTime;
    }

    public void setFinalExamTime(LocalTime finalExamTime) {
        this.finalExamTime = finalExamTime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
