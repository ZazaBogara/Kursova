package com.java.zakhar.services.dataobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class StudentProject {
    @Setter(AccessLevel.PRIVATE)
    private int ID;
    @Setter(AccessLevel.PRIVATE)
    private int projectID;
    @Setter(AccessLevel.PRIVATE)
    private String projectName;
}
