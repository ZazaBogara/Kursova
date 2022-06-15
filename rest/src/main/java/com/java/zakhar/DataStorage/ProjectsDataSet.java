package com.java.zakhar.DataStorage;

import com.java.zakhar.IOService.IIoService;

public class ProjectsDataSet extends DataSet<ProjectItem> {

    public ProjectsDataSet(IIoService ioService) {
        super("Project", ioService);
    }


    @Override
    protected ProjectItem createItem() {
        return new ProjectItem();
    }
}