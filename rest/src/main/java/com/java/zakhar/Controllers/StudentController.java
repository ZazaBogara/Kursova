package com.java.zakhar.Controllers;


import com.java.zakhar.Services.DataObject.Student;
import com.java.zakhar.Services.IStudentService;
import com.java.zakhar.Services.InvalidEntityIdException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final IStudentService service;

    public StudentController(IStudentService service) {
        this.service = service;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/students")
    List<Student> all() {
        return service.getStudents();
    }
    // end::get-aggregate-root[]

    @PostMapping("/students")
    Student addStudent(@RequestBody Student newStudent) throws Exception {
        int id = service.addStudent(newStudent);
        return service.getStudent(id);
    }

    // Single item

    @GetMapping("/students/{id}")
    Student one(@PathVariable int id) throws InvalidEntityIdException {
        return service.getStudent(id);
    }

    @PutMapping("/students/{id}")
    Student replaceStudent(@RequestBody Student newStudent, @PathVariable int id) throws Exception {
        newStudent.setID(id);
        service.updateStudent(newStudent);
        return service.getStudent(id);
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable int id) throws Exception {
        service.deleteStudent(id);
    }
}
