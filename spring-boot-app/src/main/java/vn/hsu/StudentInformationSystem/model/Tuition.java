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

    private int semester;

    private long total;

    private long paid;

    private long refund;

    private long balance;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne()
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "fk_tuition_student")
    )
    private Student student;

    public Tuition() {
    }
}
