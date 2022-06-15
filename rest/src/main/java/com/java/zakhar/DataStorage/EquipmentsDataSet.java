package com.java.zakhar.DataStorage;


import com.java.zakhar.IOService.IIoService;

public class EquipmentsDataSet extends DataSet<EquipmentItem> {

    EquipmentsDataSet(IIoService ioService) {
        super("Equipment", ioService);
    }


    @Override
    protected EquipmentItem createItem() {
        return new EquipmentItem();
    }

}
