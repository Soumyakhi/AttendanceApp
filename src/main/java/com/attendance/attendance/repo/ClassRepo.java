package com.attendance.attendance.repo;
import com.attendance.attendance.entity.ClassEntity;
import com.attendance.attendance.entity.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface ClassRepo extends MongoRepository<ClassEntity, String> {
    ClassEntity findByClassIdAndTeacher_UserName(String classId, String username);
}
