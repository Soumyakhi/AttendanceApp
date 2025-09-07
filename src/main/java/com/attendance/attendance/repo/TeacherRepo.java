package com.attendance.attendance.repo;

import com.attendance.attendance.entity.FormEntity;
import com.attendance.attendance.entity.Student;
import com.attendance.attendance.entity.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TeacherRepo extends MongoRepository<Teacher, String> {
    Teacher findByUserName(String username);
    Teacher findByUserNameAndPassword(String username, String password);
}
