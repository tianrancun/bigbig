package com.bigbig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "user1", schema = "dbo")
@ToString(exclude = {"name","sex"})
@EqualsAndHashCode(exclude = { "name", "sex" })
@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, columnDefinition = "integer")
    private Integer userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "sex", nullable = false, length = 100)
    private String sex;

    @Column(name = "email", length = 100)
    private String email;

    public User() {
    }
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
