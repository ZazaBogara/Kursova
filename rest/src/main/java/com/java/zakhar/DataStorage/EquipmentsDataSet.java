package com.java.zakhar.datastorage;


import com.java.zakhar.ioservice.IIoService;

public class EquipmentsDataSet extends DataSet<EquipmentItem> {

    EquipmentsDataSet(IIoService ioService) {
        super("Equipment", ioService);
    }


    @Override
    protected EquipmentItem createItem() {
        return new EquipmentItem();
    }

}
