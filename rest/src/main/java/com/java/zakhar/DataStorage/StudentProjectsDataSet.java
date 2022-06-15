package com.java.zakhar.DataStorage;

import com.java.zakhar.IOService.IIoService;

public class StudentProjectsDataSet extends DataSet<StudentProjectItem> {
    public StudentProjectsDataSet(IIoService ioService) {
        super("StudentProject", ioService);
    }


    @Override
    protected StudentProjectItem createItem() {
        return new StudentProjectItem();
    }


}
