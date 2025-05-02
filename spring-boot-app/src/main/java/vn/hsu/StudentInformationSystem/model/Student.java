package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long code;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "photocopy_balance")
    private long photocopyBalance;

    @Column(name = "user_name")
    private String username;

    private String password;

    @Column(name = "refresh_token",columnDefinition = "TEXT")
    private String refreshToken;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Course> courseList;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<PhotocopyTransaction> photocopyTransactionList;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Tuition> tuitionList;

    public Student() {
    }

    public Student(long code, String fullName, String username, String password) {
        this.code = code;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getPhotocopyBalance() {
        return photocopyBalance;
    }

    public void setPhotocopyBalance(long photocopyBalance) {
        this.photocopyBalance = photocopyBalance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<PhotocopyTransaction> getPhotocopyTransactionList() {
        return photocopyTransactionList;
    }

    public void setPhotocopyTransactionList(List<PhotocopyTransaction> photocopyTransactionList) {
        this.photocopyTransactionList = photocopyTransactionList;
    }

    public List<Tuition> getTuitionList() {
        return tuitionList;
    }

    public void setTuitionList(List<Tuition> tuitionList) {
        this.tuitionList = tuitionList;
    }
}
