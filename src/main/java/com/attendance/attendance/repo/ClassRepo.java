package com.attendance.attendance.repo;
import com.attendance.attendance.entity.ClassEntity;
import com.attendance.attendance.entity.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClassRepo extends MongoRepository<ClassEntity, String> {
    ClassEntity findByClassIdAndTeacher_UserName(String classId, String username);
    ClassEntity findByClassId(String classId);
    List<ClassEntity> findAllByTeacher_UserName(String username);
    List<ClassEntity> findAllByTeacher_Subject_SubjectId(String subjectId);
}
