package com.java.zakhar.DataStorage;

import com.java.zakhar.IOService.IIoService;


public class ProjectEquipmentsDataSet extends DataSet<ProjectEquipmentItem> {
    public ProjectEquipmentsDataSet(IIoService ioService) {
        super("ProjectEquipment", ioService);
    }

    @Override
    protected ProjectEquipmentItem createItem() {
        return new ProjectEquipmentItem();
    }
}
