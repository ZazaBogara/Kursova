package com.java.zakhar.datastorage;

import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EquipmentTests {

    @Test
    void test_SaveItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        EquipmentsDataSet equipments = new EquipmentsDataSet(ioService);
        EquipmentItem equipment = new EquipmentItem(1, "TestEquipment", 100.50f, 2);
        equipments.setItem(equipment);
        equipments.save();

        String fileName = equipments.getTodayFileName();
        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                EquipmentItem.Header,
                "1,TestEquipment,100.500000,2"
        });
    }

    @Test
    void test_LoadItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new EquipmentsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, EquipmentItem.Header, new String[]{
                "2,TestEquipment2,200.500000,2"
        });

        EquipmentsDataSet equipments = new EquipmentsDataSet(ioService);
        equipments.load();
        EquipmentItem equipment = equipments.getItem(2);
        assertNotNull(equipment);
        assertEquals(equipment.getName(), "TestEquipment2");
        assertEquals(equipment.getPrice(), 200.5f);
        assertEquals(equipment.getTotalAmount(), 2);
    }

    @Test
    void test_DeleteItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new EquipmentsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, EquipmentItem.Header, new String[]{
                "3,TestEquipment3,300.500000,3"
        });

        EquipmentsDataSet equipments = new EquipmentsDataSet(ioService);
        equipments.load();

        equipments.deleteItem(3);
        equipments.save();

        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                EquipmentItem.Header
        });
    }

    @Test
    void test_CreateThenRead() throws Exception {
        InMemIoService ioService = new InMemIoService();
        int id;
        EquipmentItem equipment1;
        {
            EquipmentsDataSet equipments = new EquipmentsDataSet(ioService);
            id = equipments.getNewID();
            equipment1 = new EquipmentItem(id, "TestEquipment", 100.50f, 2);
            equipments.setItem(equipment1);
            equipments.save();
        }
        {
            EquipmentsDataSet equipments = new EquipmentsDataSet(ioService);
            equipments.load();
            EquipmentItem equipment2 = equipments.getItem(id);
            assertNotNull(equipment2);
            assertEquals(equipment1, equipment2);
        }
    }
}
