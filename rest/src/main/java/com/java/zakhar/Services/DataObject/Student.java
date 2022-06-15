package com.java.zakhar.Services.DataObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class Student {
    private int ID;
    @Setter(AccessLevel.PRIVATE)
    private String firstName;
    @Setter(AccessLevel.PRIVATE)
    private String lastName;
    @Setter(AccessLevel.PRIVATE)
    private String course;
    @Setter(AccessLevel.PRIVATE)
    private List<StudentProject> Projects;
}
