package com.bigbig.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="s_person")
public class Person {

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Id
    private String id;
    private String username;
    private String age;


    //JoinTable的name是中间表的名字
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "s_person_card", joinColumns = {@JoinColumn(name = "p_id")}
            , inverseJoinColumns = {@JoinColumn(name = "c_id")})
    private List<Cards> cards;

    public List<Cards> getCards() {
        return cards;
    }

    public void setCards(List<Cards> cards) {
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}