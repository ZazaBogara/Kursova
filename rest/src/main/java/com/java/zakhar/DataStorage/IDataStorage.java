package com.java.zakhar.DataStorage;

public interface IDataStorage {
    EquipmentsDataSet getEquipments();

    ProjectsDataSet getProjects();

    StudentsDataSet getStudents();

    ProjectEquipmentsDataSet getProjectEquipments();

    StudentProjectsDataSet getStudentProjects();
}
