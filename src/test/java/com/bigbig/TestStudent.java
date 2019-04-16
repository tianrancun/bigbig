package com.bigbig;


import com.bigbig.entity.Student;
import com.bigbig.entity.StudentClass;
import com.bigbig.entity.User;
import com.bigbig.repository.StudentRepository;
import com.bigbig.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@SpringBootTest
public class TestStudent {
    @Autowired
    private StudentRepository studentRepository;
    @Test
    public void testStudent() throws Exception {
//        Student sdut = new Student();
//        sdut.setStudentName("zhang s");
//        Student sdut2 = new Student();
//        sdut2.setStudentName("li s");
//
//        StudentClass sclass = new StudentClass();
//        sclass.setClassNbr("001");
//        sclass.setGrade("grade1");
//        sclass.getStudents().add(sdut);
//        studentRepository.save(sdut);
    }

}