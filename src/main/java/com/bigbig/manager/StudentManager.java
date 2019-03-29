package com.bigbig.manager;

import com.bigbig.entity.Student;
import com.bigbig.exception.StudentNotFoundException;
import com.bigbig.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class StudentManager {
    @Autowired
    private StudentRepository studentRepository;

    public Student findByStudentNbr(String classNbr, String studentNbr) throws StudentNotFoundException {
        Optional<Student> claim = studentRepository.findByClassNbrAndStudentNbr(classNbr,studentNbr);
        return claim.orElseThrow(() -> new StudentNotFoundException("student not found with studentNbr " + studentNbr));
    }
}