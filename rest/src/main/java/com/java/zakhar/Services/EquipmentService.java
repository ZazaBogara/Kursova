package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.EquipmentItem;
import com.java.zakhar.DataStorage.IDataStorage;
import com.java.zakhar.DataStorage.ProjectEquipmentItem;
import com.java.zakhar.DataStorage.ProjectItem;
import com.java.zakhar.Services.DataObject.Equipment;
import com.java.zakhar.Services.DataObject.EquipmentProject;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class EquipmentService implements IEquipmentService {
    private IDataStorage dataStorage;

    private List<EquipmentProject> loadEquipmentProjects(int equipmentID) {
        List<EquipmentProject> equipmentProjects = new LinkedList<>();
        for (ProjectEquipmentItem projectEquipmentItem : dataStorage.getProjectEquipments().getItems()) {
            if (projectEquipmentItem.getEquipmentID() == equipmentID) {
                ProjectItem projectItem = dataStorage.getProjects().getItem(projectEquipmentItem.getProjectID());
                EquipmentProject equipmentProject = new EquipmentProject(projectEquipmentItem.getId(), projectItem.getId(), projectItem.getName(), projectEquipmentItem.getAmount());
                equipmentProjects.add(equipmentProject);
            }
        }
        return equipmentProjects;
    }

    private Equipment loadEquipment(EquipmentItem equipmentItem) {
        List<EquipmentProject> equipmentProjects = loadEquipmentProjects(equipmentItem.getId());
        return new Equipment(equipmentItem.getId(), equipmentItem.getName(), equipmentItem.getPrice(), equipmentItem.getTotalAmount(), equipmentProjects);
    }

    public List<Equipment> getEquipments() {
        List<Equipment> result = new LinkedList<>();
        for (EquipmentItem equipmentItem : dataStorage.getEquipments().getItems()) {
            Equipment equipment = loadEquipment(equipmentItem);
            result.add(equipment);
        }
        return result;
    }

    public Equipment getEquipment(int ID) throws InvalidEntityIdException {
        EquipmentItem equipmentItem = dataStorage.getEquipments().getItem(ID);
        if (equipmentItem == null)
            throw new InvalidEntityIdException(String.format("Equipment with ID %d is not found", ID));

        return loadEquipment(equipmentItem);
    }

    private void updateEquipmentProjects(int equipmentID, List<EquipmentProject> projects) throws IOException {
        // get existing projects
        HashMap<Integer, ProjectEquipmentItem> existingProjects = new HashMap<>();
        for (ProjectEquipmentItem projectEquipmentItem : dataStorage.getProjectEquipments().getItems()) {
            if (projectEquipmentItem.getEquipmentID() == equipmentID) {
                existingProjects.put(projectEquipmentItem.getId(), projectEquipmentItem);
            }
        }
        boolean wasModified = false;
        // update projects
        for (EquipmentProject proj : projects) {
            ProjectEquipmentItem existingItem = existingProjects.get(proj.getID());
            if (existingItem != null) {
                existingProjects.remove(proj.getID());
                if (existingItem.getProjectID() != proj.getProjectID() || existingItem.getAmount() != proj.getAmount()) {
                    dataStorage.getProjectEquipments().setItem(new ProjectEquipmentItem(existingItem.getId(), proj.getProjectID(), equipmentID, proj.getAmount()));
                    wasModified = true;
                }
            } else {
                dataStorage.getProjectEquipments().setItem(new ProjectEquipmentItem(dataStorage.getProjectEquipments().getNewID(), proj.getProjectID(), equipmentID, proj.getAmount()));
                wasModified = true;
            }
        }
        // remove unused
        for (ProjectEquipmentItem projectEquipmentItem : existingProjects.values()) {
            dataStorage.getProjectEquipments().deleteItem(projectEquipmentItem.getId());
            wasModified = true;
        }
        //
        if (wasModified)
            dataStorage.getProjectEquipments().save();
    }

    public int addEquipment(Equipment equipment) throws Exception {
        if (equipment.getID() != 0)
            throw new InvalidEntityIdException("EquipmentID must be 0 for adding new equipment");

        int id = dataStorage.getEquipments().getNewID();
        EquipmentItem equipmentItem = new EquipmentItem(id, equipment.getName(), equipment.getPrice(), equipment.getTotalAmount());
        dataStorage.getEquipments().setItem(equipmentItem);
        dataStorage.getEquipments().save();

        if (equipment.getProjects() != null) {
            updateEquipmentProjects(id, equipment.getProjects());
        }
        return id;
    }

    public void updateEquipment(Equipment equipment) throws Exception {
        EquipmentItem existingEquipmentItem = dataStorage.getEquipments().getItem(equipment.getID());
        if (existingEquipmentItem == null)
            throw new InvalidEntityIdException(String.format("Equipment with ID %d is not found", equipment.getID()));

        EquipmentItem equipmentItem = new EquipmentItem(equipment.getID(), equipment.getName(), equipment.getPrice(), equipment.getTotalAmount());
        dataStorage.getEquipments().setItem(equipmentItem);
        dataStorage.getEquipments().save();

        if (equipment.getProjects() != null) {
            updateEquipmentProjects(equipment.getID(), equipment.getProjects());
        }
    }

    public void deleteEquipment(int id) throws Exception {
        EquipmentItem existingEquipmentItem = dataStorage.getEquipments().getItem(id);
        if (existingEquipmentItem == null)
            throw new InvalidEntityIdException(String.format("Equipment with ID %d is not found", id));

        updateEquipmentProjects(id, new ArrayList<>());

        dataStorage.getEquipments().deleteItem(id);
        dataStorage.getEquipments().save();
    }
}
