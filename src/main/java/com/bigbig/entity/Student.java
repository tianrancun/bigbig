package com.bigbig.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Table(name = "student", schema = "dbo")
@ToString(exclude = {"email"})
@EqualsAndHashCode(exclude = { "studentName", "studentNbr" })
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false, columnDefinition = "integer")
    private Integer studentId;

    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;

    @Column(name = "student_nbr", length = 100)
    private String studentNbr;

    @Column(name = "class_nbr", length = 100)
    private String classNbr;

    @Column(name = "age")
    private String age;

    @Column(name = "email")
    private String email;


//    @JsonIgnore
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_nbr", nullable = false)
//    private StudentClass studentClass;
}
