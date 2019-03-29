package com.bigbig.repository;

import com.bigbig.entity.Student;
import com.bigbig.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//@Repository
//public interface UserRepository extends CrudRepository<User, Integer> {
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
     Optional<Student> findByClassNbrAndStudentNbr(String classNbr, String studentNbr);

}