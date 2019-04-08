package com.bigbig.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Table(name = "class", schema = "dbo")
@ToString
@Entity
public class StudentClass {

    @Column(name = "class_nbr")
    public String classNbr;
    @Column(name = "grade")
    public String grade;

    @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL) //mappedBy，将维护权交由多的一方来维护con
    public Set<Student> students;
}