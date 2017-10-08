/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.dataJpa.service;

import com.spring.dataJpa.dto.StudentDTO;
import com.spring.dataJpa.entity.Student;
import com.spring.dataJpa.repo.StudentRepo;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public boolean isSaveStudent(StudentDTO dTO) throws Exception {
        Student student = new Student();
        BeanUtils.copyProperties(dTO, student);
        studentRepo.save(student);
        return true;
    }
    
    public List<Student> findAllStudents(){          
        return studentRepo.findAll();
    }

    public void deleteStudent(Long studentID) throws Exception {
        studentRepo.delete(studentID); 
    }

}
