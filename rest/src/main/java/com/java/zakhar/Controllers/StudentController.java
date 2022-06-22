package com.java.zakhar.controllers;


import com.java.zakhar.services.IStudentService;
import com.java.zakhar.services.InvalidEntityIdException;
import com.java.zakhar.services.dataobject.Student;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final IStudentService service;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public StudentController(IStudentService service) {
        this.service = service;
    }

    @GetMapping("/students")
    public List<Student> all() {
        return service.getStudents();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student newStudent) throws Exception {
        int id = service.addStudent(newStudent);
        return service.getStudent(id);
    }

    @GetMapping("/students/{id}")
    public Student one(@PathVariable int id) throws InvalidEntityIdException {
        return service.getStudent(id);
    }

    @PutMapping("/students/{id}")
    public Student replaceStudent(@RequestBody Student newStudent, @PathVariable int id) throws Exception {
        newStudent.setID(id);
        service.updateStudent(newStudent);
        return service.getStudent(id);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id) throws Exception {
        service.deleteStudent(id);
    }
}
