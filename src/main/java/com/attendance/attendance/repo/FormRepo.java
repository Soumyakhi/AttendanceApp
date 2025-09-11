package com.attendance.attendance.repo;

import com.attendance.attendance.entity.FormEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FormRepo extends MongoRepository<FormEntity, String> {

    FormEntity findByCourseNo(String courseNo);
    void deleteByCourseNo(String courseNo);
}
