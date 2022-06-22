package com.java.zakhar.services.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectEquipment {
    private int ID;
    private int equipmentID;
    private String equipmentName;
    private int amount;
}
