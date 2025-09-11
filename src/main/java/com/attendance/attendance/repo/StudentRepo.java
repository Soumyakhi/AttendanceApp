package com.attendance.attendance.repo;

import com.attendance.attendance.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepo extends MongoRepository<Student, Integer> {
    Student findByUserName(String username);
    Student findByUserNameAndPassword(String username, String password);
    Student findByUserNameAndCourse_SubjectList_SubjectId(String userName, String subjectId);
    Set<Student> findByCourse_CourseNo(String courseId);
}
