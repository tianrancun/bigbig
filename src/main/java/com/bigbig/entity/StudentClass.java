package com.bigbig.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Table(name = "student_class", schema = "dbo")
@ToString
@Entity
public class StudentClass {

    @Id
    @Column(name = "class_nbr")
    public Integer classNbr;
    @Column(name = "grade")
    public String grade;

//    @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL) //mappedBy，将维护权交由多的一方来维护con
//    public Set<Student> students;
}