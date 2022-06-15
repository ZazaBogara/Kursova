package com.java.zakhar.DataStorage;

import com.java.zakhar.IOService.IIoService;

public class DataStorage implements IDataStorage {

    final private EquipmentsDataSet equipments;
    final private ProjectsDataSet projects;
    final private StudentsDataSet students;
    final private ProjectEquipmentsDataSet projectEquipments;
    final private StudentProjectsDataSet studentProjects;


    public DataStorage(IIoService ioService) {
        equipments = new EquipmentsDataSet(ioService);
        projects = new ProjectsDataSet(ioService);
        students = new StudentsDataSet(ioService);
        projectEquipments = new ProjectEquipmentsDataSet(ioService);
        studentProjects = new StudentProjectsDataSet(ioService);
    }

    public void loadAll() throws Exception {
        equipments.load();
        projects.load();
        students.load();
        projectEquipments.load();
        studentProjects.load();
    }

    @Override
    public EquipmentsDataSet getEquipments() {
        return equipments;
    }

    @Override
    public ProjectsDataSet getProjects() {
        return projects;
    }

    @Override
    public StudentsDataSet getStudents() {
        return students;
    }

    @Override
    public ProjectEquipmentsDataSet getProjectEquipments() {
        return projectEquipments;
    }

    @Override
    public StudentProjectsDataSet getStudentProjects() {
        return studentProjects;
    }
}
