package com.java.zakhar.datastorage;

import com.java.zakhar.ioservice.IIoService;

public class StudentProjectsDataSet extends DataSet<StudentProjectItem> {
    public StudentProjectsDataSet(IIoService ioService) {
        super("StudentProject", ioService);
    }


    @Override
    protected StudentProjectItem createItem() {
        return new StudentProjectItem();
    }


}
