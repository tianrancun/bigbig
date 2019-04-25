package com.bigbig.service;

import com.bigbig.entity.Student;
import com.bigbig.exception.StudentNotFoundException;
import com.bigbig.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class StudentService {

    private boolean flag = false;
    @Autowired
    private StudentRepository studentRepository;

    public Student findByStudentNbr(String classNbr, String studentNbr) throws StudentNotFoundException {
        Optional<Student> claim = studentRepository.findByClassNbrAndStudentNbr(classNbr,studentNbr);
        return claim.orElseThrow(() -> new StudentNotFoundException("student not found with studentNbr " + studentNbr));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addStudent(Student student) throws Exception {
        log.info("service add stu...");
        if(student.getAge().equals("18")){
            Thread.sleep(20000);
        }

        try {
            studentRepository.save(student);
        }catch (Exception e){
            log.error("add student error {}",e);
            throw  new Exception("student add filed");
        }

    }
}