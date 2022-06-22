package com.java.zakhar.services.dataobject;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@SuppressFBWarnings({"EI_EXPOSE_REP2", "EI_EXPOSE_REP"})
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
