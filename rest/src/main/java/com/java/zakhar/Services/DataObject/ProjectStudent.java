package com.java.zakhar.services.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectStudent {
    private int ID;
    private int studentID;
    private String firstName;
    private String lastName;
    private String course;
}
