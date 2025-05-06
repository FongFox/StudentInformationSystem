package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tuition")
@Setter
@Getter
public class Tuition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private long total;

    @Column(nullable = true)
    private long paid;

    @Column(nullable = true)
    private long refund;

    @Column(nullable = true)
    private long balance;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne()
    @JoinColumn(
            name = "semester_id",
            foreignKey = @ForeignKey(name = "fk_tuition_semester")
    )
    private Semester semester;

    @ManyToOne()
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "fk_tuition_student")
    )
    private Student student;

    public Tuition() {
    }
}
