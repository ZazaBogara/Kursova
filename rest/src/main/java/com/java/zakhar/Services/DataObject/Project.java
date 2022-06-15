package com.java.zakhar.Services.DataObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class Project {
    private int ID;
    @Setter(AccessLevel.PRIVATE)
    private String name;
    @Setter(AccessLevel.PRIVATE)
    private List<ProjectEquipment> Equipments;
    @Setter(AccessLevel.PRIVATE)
    private List<ProjectStudent> Students;
}
