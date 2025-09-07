package com.attendance.attendance.repo;

import com.attendance.attendance.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends MongoRepository<Student, Integer> {
    Student findByUserName(String username);
    Student findByUserNameAndPassword(String username, String password);
}
