package com.attendance.attendance.repo;

import com.attendance.attendance.entity.FormEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FormRepo extends MongoRepository<FormEntity, String> {
    Optional<FormEntity> findById(String name);
    FormEntity findByCourseNo(String courseNo);
}
