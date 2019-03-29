package com.bigbig.service;

import com.bigbig.entity.Student;
import com.bigbig.exception.StudentNotFoundException;
import com.bigbig.manager.StudentManager;
import com.bigbig.util.common.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "student/{classNbr}")
@Slf4j
public class StudentService {

    @Autowired
    private StudentManager studentManager;

    @RequestMapping(value = "/getStudent")
    public ServiceResponse<Student> getStudentByClassNbrAndStudentNbr(@PathVariable("classNbr") final String classNbr, @RequestParam("studentNbr") final String studentNbr) {
        log.info("getStudent : classNbr - {}, studentNbr - {}", classNbr, studentNbr);

        Student studentEntity = null;
        try {
            studentEntity = studentManager.findByStudentNbr(classNbr, studentNbr);
        }
        catch (StudentNotFoundException ex) {
            log.warn("ClaimNotFoundException in getClaimInfo: {}", ex);
            return new ServiceResponse<>(ex.getMessage());
        }
        return new ServiceResponse<Student>(studentEntity);
    }

    @RequestMapping(value = "/hello")
    public String sayHelloWorld(@RequestParam("studentNbr") final String studentNbr){
        log.info("say hello world!! {}",studentNbr);
        return "hello world!";
    }
}