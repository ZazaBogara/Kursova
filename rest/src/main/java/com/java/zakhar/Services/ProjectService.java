package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.*;
import com.java.zakhar.Services.DataObject.Project;
import com.java.zakhar.Services.DataObject.ProjectEquipment;
import com.java.zakhar.Services.DataObject.ProjectStudent;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class ProjectService implements IProjectService {
    IDataStorage dataStorage;


    private List<ProjectEquipment> loadProjectEquipments(int projectID) {
        List<ProjectEquipment> projectEquipments = new LinkedList<>();
        for (ProjectEquipmentItem projectEquipmentItem : dataStorage.getProjectEquipments().getItems()) {
            if (projectEquipmentItem.getProjectID() == projectID) {
                EquipmentItem equipmentItem = dataStorage.getEquipments().getItem(projectEquipmentItem.getEquipmentID());
                ProjectEquipment projectEquipment = new ProjectEquipment(projectEquipmentItem.getId(), equipmentItem.getId(), equipmentItem.getName(), projectEquipmentItem.getAmount());
                projectEquipments.add(projectEquipment);
            }
        }
        return projectEquipments;
    }

    private List<ProjectStudent> loadProjectStudents(int projectID) {
        List<ProjectStudent> projectStudents = new LinkedList<>();
        for (StudentProjectItem studentProjectItem : dataStorage.getStudentProjects().getItems()) {
            if (studentProjectItem.getProjectID() == projectID) {
                StudentItem studentItem = dataStorage.getStudents().getItem(studentProjectItem.getStudentID());
                ProjectStudent projectStudent = new ProjectStudent(studentProjectItem.getId(), studentItem.getId(), studentItem.getFirstName(), studentItem.getLastName(), studentItem.getCourse());
                projectStudents.add(projectStudent);
            }
        }
        return projectStudents;
    }

    private Project loadProject(ProjectItem projectItem) {
        List<ProjectEquipment> projectEquipments = loadProjectEquipments(projectItem.getId());
        List<ProjectStudent> projectStudents = loadProjectStudents(projectItem.getId());
        return new Project(projectItem.getId(), projectItem.getName(), projectEquipments, projectStudents);
    }

    public List<Project> getProjects() {
        List<Project> result = new LinkedList<>();
        for (ProjectItem projectItem : dataStorage.getProjects().getItems()) {
            Project project = loadProject(projectItem);
            result.add(project);
        }
        return result;
    }

    public Project getProject(int ID) throws InvalidEntityIdException {
        ProjectItem projectItem = dataStorage.getProjects().getItem(ID);
        if (projectItem == null)
            throw new InvalidEntityIdException(String.format("Project with ID %d is not found", ID));

        return loadProject(projectItem);
    }

    private void updateProjectEquipments(int projectID, List<ProjectEquipment> equipments) throws IOException {
        // get existing projects
        HashMap<Integer, ProjectEquipmentItem> existingProjects = new HashMap<>();
        for (ProjectEquipmentItem projectEquipmentItem : dataStorage.getProjectEquipments().getItems()) {
            if (projectEquipmentItem.getProjectID() == projectID) {
                existingProjects.put(projectEquipmentItem.getId(), projectEquipmentItem);
            }
        }
        boolean wasModified = false;
        // update projects
        for (ProjectEquipment equip : equipments) {
            ProjectEquipmentItem existingItem = existingProjects.get(equip.getID());
            if (existingItem != null) {
                existingProjects.remove(equip.getID());
                if (existingItem.getEquipmentID() != equip.getEquipmentID() || existingItem.getAmount() != equip.getAmount()) {
                    dataStorage.getProjectEquipments().setItem(new ProjectEquipmentItem(existingItem.getId(), projectID, equip.getEquipmentID(), equip.getAmount()));
                    wasModified = true;
                }
            } else {
                dataStorage.getProjectEquipments().setItem(new ProjectEquipmentItem(dataStorage.getProjectEquipments().getNewID(), projectID, equip.getEquipmentID(), equip.getAmount()));
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

    private void updateProjectStudents(int projectID, List<ProjectStudent> students) throws IOException {
        // get existing projects
        HashMap<Integer, StudentProjectItem> existingProjects = new HashMap<>();
        for (StudentProjectItem studentProjectItem : dataStorage.getStudentProjects().getItems()) {
            if (studentProjectItem.getProjectID() == projectID) {
                existingProjects.put(studentProjectItem.getId(), studentProjectItem);
            }
        }
        boolean wasModified = false;
        // update projects
        for (ProjectStudent stud : students) {
            StudentProjectItem existingItem = existingProjects.get(stud.getID());
            if (existingItem != null) {
                existingProjects.remove(stud.getID());
                if (existingItem.getStudentID() != stud.getStudentID()) {
                    dataStorage.getStudentProjects().setItem(new StudentProjectItem(dataStorage.getStudentProjects().getNewID(), stud.getStudentID(), projectID));
                    wasModified = true;
                }
            } else {
                dataStorage.getStudentProjects().setItem(new StudentProjectItem(dataStorage.getStudentProjects().getNewID(), stud.getStudentID(), projectID));
                wasModified = true;
            }
        }
        // remove unused
        for (StudentProjectItem studentProjectItem : existingProjects.values()) {
            dataStorage.getStudentProjects().deleteItem(studentProjectItem.getId());
            wasModified = true;
        }
        //
        if (wasModified)
            dataStorage.getStudentProjects().save();
    }

    public int addProject(Project project) throws Exception {
        if (project.getID() != 0)
            throw new InvalidEntityIdException("ProjectID must be 0 for adding new equipment");

        int id = dataStorage.getProjects().getNewID();
        ProjectItem projectItem = new ProjectItem(id, project.getName());
        dataStorage.getProjects().setItem(projectItem);
        dataStorage.getProjects().save();

        if (project.getEquipments() != null) {
            updateProjectEquipments(id, project.getEquipments());
        }

        if (project.getStudents() != null) {
            updateProjectStudents(id, project.getStudents());
        }

        return id;
    }

    public void updateProject(Project project) throws Exception {
        ProjectItem existingProjectItem = dataStorage.getProjects().getItem(project.getID());
        if (existingProjectItem == null)
            throw new InvalidEntityIdException(String.format("Project with ID %d is not found", project.getID()));

        ProjectItem projectItem = new ProjectItem(project.getID(), project.getName());
        dataStorage.getProjects().setItem(projectItem);
        dataStorage.getProjects().save();

        if (project.getEquipments() != null) {
            updateProjectEquipments(project.getID(), project.getEquipments());
        }

        if (project.getStudents() != null) {
            updateProjectStudents(project.getID(), project.getStudents());
        }
    }

    public void deleteProject(int id) throws Exception {
        ProjectItem existingProjectItem = dataStorage.getProjects().getItem(id);
        if (existingProjectItem == null)
            throw new InvalidEntityIdException(String.format("Project with ID %d is not found", id));

        updateProjectEquipments(id, new ArrayList<>());
        updateProjectStudents(id, new ArrayList<>());

        dataStorage.getProjects().deleteItem(id);
        dataStorage.getProjects().save();
    }
}
