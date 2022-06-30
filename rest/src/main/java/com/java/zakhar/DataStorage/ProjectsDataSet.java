package com.java.zakhar.datastorage;

import com.java.zakhar.ioservice.IIoService;

public class ProjectsDataSet extends DataSet<ProjectItem> {

    public ProjectsDataSet(IIoService ioService) {
        super("Project", ioService);
    }


    @Override
    protected ProjectItem createItem() {
        return new ProjectItem();
    }
}