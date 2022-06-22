package com.java.zakhar.datastorage;

import com.java.zakhar.ioservice.IIoService;

public class StudentsDataSet extends DataSet<StudentItem> {
    public StudentsDataSet(IIoService ioService) {
        super("Student", ioService);
    }


    @Override
    protected StudentItem createItem() {
        return new StudentItem();
    }
}
