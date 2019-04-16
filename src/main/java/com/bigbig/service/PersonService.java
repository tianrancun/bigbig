package com.bigbig.service;

import com.bigbig.entity.Cards;
import com.bigbig.entity.Person;
import com.bigbig.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
}