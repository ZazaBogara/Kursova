package com.java.zakhar.datastorage;

import com.java.zakhar.ioservice.IIoService;


public class ProjectEquipmentsDataSet extends DataSet<ProjectEquipmentItem> {
    public ProjectEquipmentsDataSet(IIoService ioService) {
        super("ProjectEquipment", ioService);
    }

    @Override
    protected ProjectEquipmentItem createItem() {
        return new ProjectEquipmentItem();
    }
}
