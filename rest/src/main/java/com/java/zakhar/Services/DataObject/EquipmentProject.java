package com.java.zakhar.Services.DataObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class EquipmentProject {
    @Setter(AccessLevel.PRIVATE)
    private int ID;
    @Setter(AccessLevel.PRIVATE)
    private int projectID;
    @Setter(AccessLevel.PRIVATE)
    private String projectName;
    @Setter(AccessLevel.PRIVATE)
    private int amount;
}
