package com.java.zakhar.datastorage;

public interface IDataStorage {
    EquipmentsDataSet getEquipments();

    ProjectsDataSet getProjects();

    StudentsDataSet getStudents();

    ProjectEquipmentsDataSet getProjectEquipments();

    StudentProjectsDataSet getStudentProjects();
}
