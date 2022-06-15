package com.java.zakhar.Services.DataObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class Equipment {

    private int ID;
    @Setter(AccessLevel.PRIVATE)
    private String name;
    @Setter(AccessLevel.PRIVATE)
    private float price;
    @Setter(AccessLevel.PRIVATE)
    private int totalAmount;
    @Setter(AccessLevel.PRIVATE)
    private List<EquipmentProject> Projects;
}
