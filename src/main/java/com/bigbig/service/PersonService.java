package com.bigbig.service;

import com.bigbig.entity.Cards;
import com.bigbig.entity.Person;
import com.bigbig.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/person")
    public Person person(@RequestParam("pid") final String pid){
        Person person=personRepository.findById(pid).get();
        List<Cards> cards = person.getCards();
        for (Cards card : cards) {
            System.out.println(card.getId());
            System.out.println(card.getNum());
        }
        return person;
    }

    @PostMapping(value = "/add_person")
    public Person addPerson(@RequestParam("userName") final String userName){
        Person person= new Person();
        String uid = System.currentTimeMillis()+"";
        log.info("uid {}",uid);
        person.setId(uid);
        person.setUsername(userName);
        person.setAge("10");

        List<Cards> cards = new ArrayList<>();
        Cards c1 = new Cards();
        c1.setId(uid);
        c1.setNum("111111111111");
        cards.add(c1);
        Cards c2 = new Cards();
        c2.setId(uid);
        c2.setNum("111111111112");
        cards.add(c1);
        cards.add(c2);
        person.setCards(cards);

        personRepository.save(person);

        return person;
    }
}