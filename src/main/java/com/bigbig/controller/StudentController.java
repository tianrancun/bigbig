package com.bigbig.controller;

import com.bigbig.entity.Student;
import com.bigbig.exception.StudentNotFoundException;
import com.bigbig.service.StudentService;
import com.bigbig.util.common.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "student/{classNbr}")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getStudent" ,method = RequestMethod.GET)
    public ServiceResponse<Student> getStudentByClassNbrAndStudentNbr(@PathVariable("classNbr") final String classNbr, @RequestParam("studentNbr") final String studentNbr) {
        log.info("getStudent : classNbr - {}, studentNbr - {}", classNbr, studentNbr);

        Student studentEntity = null;
        try {
            studentEntity = studentService.findByStudentNbr(classNbr, studentNbr);
        }
        catch (StudentNotFoundException ex) {
            log.warn("ClaimNotFoundException in getClaimInfo: {}", ex);
            return new ServiceResponse<>(ex.getMessage());
        }
        return new ServiceResponse<Student>(studentEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = 10000)
    @PostMapping(value = "/addStudent")
    public ServiceResponse<Boolean> addStudent(@RequestParam(name = "studentName",required = false) String studentName, @RequestParam String studentNbr,
                                               @PathVariable String classNbr, @RequestParam String age){
        log.info("add student : studentName - {}, studentNbr - {}, classNbr - {}, age - {}", studentName, studentNbr, classNbr, age);
        Student student = Student.builder()
                .studentName(studentName)
                .classNbr(classNbr)
                .studentNbr(studentNbr)
                .age(age)
                .build();
        try {
            studentService.addStudent(student);
        } catch (Exception e) {
            return new ServiceResponse<>(e.getMessage());
        }
        return new ServiceResponse<>(true);
    }


    @GetMapping(value = "/hello")
    public String sayHelloWorld(@RequestParam("studentNbr") final String studentNbr){
        log.info("say hello world!! {}",studentNbr);
        return "hello world!";
    }
}