package com.java.zakhar.DataStorage;

import com.java.zakhar.IOService.IIoService;

public class StudentsDataSet extends DataSet<StudentItem> {
    public StudentsDataSet(IIoService ioService) {
        super("Student", ioService);
    }


    @Override
    protected StudentItem createItem() {
        return new StudentItem();
    }
}
