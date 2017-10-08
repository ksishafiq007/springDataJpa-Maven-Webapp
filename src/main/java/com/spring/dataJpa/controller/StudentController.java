/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.dataJpa.controller;

import com.spring.dataJpa.dto.StudentDTO;
import com.spring.dataJpa.entity.Student;
import com.spring.dataJpa.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.spring.upload.util.Upload;
import com.spring.upload.util.UploadImpl;

@Controller
@ManagedBean
@Scope("session")
public class StudentController {

    @Autowired
    private StudentService studentService;
    // @Autowired
    // @Qualifier(value="upload")
    //  private UploadImpl uploadImpl;

    private StudentDTO studentDTO;
    private List<StudentDTO> studentDtoList;
    private UploadedFile uploadFile;

    public void saveStudent() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            String fileName = uploadFile.getFileName();
            Upload upload = new UploadImpl();
            upload.upload("Student", fileName, uploadFile);
            studentDTO.setPhotoPath(fileName);
            boolean b = studentService.isSaveStudent(studentDTO);
            if (b) {
                context.addMessage(null, new FacesMessage("Data saved Successfully!"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sorry! Data could not be saved Successfully!", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findAllStudents() {
        try {
            studentDtoList = new ArrayList<StudentDTO>();
            List<Student> studentList = new ArrayList<Student>();
            studentList = studentService.findAllStudents();
            for (Student s : studentList) {
                StudentDTO studentDto = new StudentDTO();
                BeanUtils.copyProperties(s, studentDto);
                studentDtoList.add(studentDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent() {
        try {
            studentService.deleteStudent(studentDTO.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public StudentDTO getStudentDTO() {
        if (studentDTO == null) {
            studentDTO = new StudentDTO();
        }
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public List<StudentDTO> getStudentDtoList() {
        findAllStudents();
        return studentDtoList;
    }

    public void setStudentDtoList(List<StudentDTO> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

}
