package com.java.zakhar.services;


import com.java.zakhar.services.dataobject.Equipment;

import java.util.List;

public interface IEquipmentService {
    List<Equipment> getEquipments();

    Equipment getEquipment(int ID) throws InvalidEntityIdException;

    int addEquipment(Equipment equipment) throws Exception;

    void updateEquipment(Equipment equipment) throws Exception;

    void deleteEquipment(int id) throws Exception;
}
