package com.java.zakhar.datastorage;

import com.java.zakhar.ioservice.IIoService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP")
public class DataStorage implements IDataStorage {

    private final EquipmentsDataSet equipments;
    private final ProjectsDataSet projects;
    private final StudentsDataSet students;
    private final ProjectEquipmentsDataSet projectEquipments;
    private final StudentProjectsDataSet studentProjects;


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
