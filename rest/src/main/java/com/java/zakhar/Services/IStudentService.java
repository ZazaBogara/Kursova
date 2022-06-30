package com.java.zakhar.services;

import com.java.zakhar.services.dataobject.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getStudents();

    Student getStudent(int ID) throws InvalidEntityIdException;

    int addStudent(Student student) throws Exception;

    void updateStudent(Student student) throws Exception;

    void deleteStudent(int id) throws Exception;
}
