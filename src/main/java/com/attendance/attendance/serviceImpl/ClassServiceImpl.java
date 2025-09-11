package com.attendance.attendance.serviceImpl;

import com.attendance.attendance.dto.StudentAttDTO;
import com.attendance.attendance.dto.TeacherAttDTO;
import com.attendance.attendance.entity.ClassEntity;
import com.attendance.attendance.entity.Student;
import com.attendance.attendance.entity.Teacher;
import com.attendance.attendance.pojo.Course;
import com.attendance.attendance.pojo.Subject;
import com.attendance.attendance.repo.ClassRepo;
import com.attendance.attendance.repo.StudentRepo;
import com.attendance.attendance.repo.TeacherRepo;
import com.attendance.attendance.security.TeacherAuthenticationFilter;
import com.attendance.attendance.service.ClassService;
import com.attendance.attendance.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    StudentRepo studentrepo;

    @Autowired
    JwtUtil jwtUtil;
    @Override
    public String createClass(HttpServletRequest req) {
        Teacher teacher=teacherRepo.findByUserName(jwtUtil.extractUserNameFromRequest(req));
        if(teacher==null)
            throw new RuntimeException("Teacher Not Found");
        ClassEntity classEntity=new ClassEntity(UUID.randomUUID().toString(),teacher,new HashSet<>(),true);
        classRepo.save(classEntity);
        return classEntity.getClassId();
    }

    @Override
    public ClassEntity closeClass(HttpServletRequest req, String classId) {
        ClassEntity classEntity=classRepo.findByClassIdAndTeacher_UserName(classId,jwtUtil.extractUserNameFromRequest(req));
        if(classEntity==null)
            return null;
        classEntity.setOpen(false);
        classRepo.save(classEntity);
        classEntity.setTeacher(null);
        return classEntity;
    }
    @Override
    public boolean postAttendance(HttpServletRequest req, String classId) {
        String uname=jwtUtil.extractUserNameFromRequest(req);
        ClassEntity classEntity=classRepo.findByClassId(classId);
        if(classEntity!=null && classEntity.isOpen() && !classEntity.getAttendedStudents().contains(uname)) {
            Student student=studentrepo.findByUserNameAndCourse_SubjectList_SubjectId(uname,
                    classEntity.getTeacher().getSubject().getSubjectId());
            if(student!=null){
                classEntity.getAttendedStudents().add(student.getUserName());
                classRepo.save(classEntity);
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
    @Override
    public List<TeacherAttDTO> viewAttendanceTeacher(HttpServletRequest request) {
        List<TeacherAttDTO> attendance=new ArrayList<>();
        List<ClassEntity> classEntityList=classRepo.findAllByTeacher_UserName(jwtUtil.extractUserNameFromRequest(request));
        for(ClassEntity classEntity:classEntityList){
            Map<String,Boolean> attendanceMap=new HashMap<>();
            Set<Student> studentSet=studentrepo.findByCourse_CourseNo(classEntity.getTeacher().getCourseNo());
            for(Student student: studentSet) {
                if(classEntity.getAttendedStudents().contains(student.getUserName())) {
                    attendanceMap.put(student.getUserName(),true);
                }
                else{
                    attendanceMap.put(student.getUserName(),false);
                }
            }
            classEntity.setTeacher(null);
            attendance.add(new TeacherAttDTO(classEntity,attendanceMap));
        }
        return attendance;
    }

    @Override
    public List<StudentAttDTO> viewAttendanceStudent(HttpServletRequest request) {
        List<StudentAttDTO> attendance=new ArrayList<>();
        Student student=studentrepo.findByUserName(jwtUtil.extractUserNameFromRequest(request));
        for(Subject subject:student.getCourse().getSubjectList()){
            int attendanceCount=0,totalClasses=0;
            List<ClassEntity> classEntityList=classRepo.findAllByTeacher_Subject_SubjectId(subject.getSubjectId());
            totalClasses=classEntityList.size();
            for(ClassEntity classEntity:classEntityList){
                if(classEntity.getAttendedStudents().contains(student.getUserName())) {
                    attendanceCount++;
                }
            }
            attendance.add(new StudentAttDTO(subject,attendanceCount,totalClasses));
        }
        return attendance;
    }
}
