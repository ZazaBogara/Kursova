package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.EquipmentItem;
import com.java.zakhar.DataStorage.IDataStorage;
import com.java.zakhar.DataStorage.ProjectEquipmentItem;
import com.java.zakhar.IOService.IIoService;
import com.java.zakhar.Services.DataObject.Equipment;
import com.java.zakhar.Services.DataObject.EquipmentProject;
import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EquipmentTests {

    void assertEquipmentEquals(Equipment expected, Equipment actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getTotalAmount(), actual.getTotalAmount());

        assertEquals(expected.getProjects().size(), actual.getProjects().size());
        for (int i = 0; i < expected.getProjects().size(); i++) {
            assertEquals(expected.getProjects().get(i).getProjectID(), actual.getProjects().get(i).getProjectID());
            assertEquals(expected.getProjects().get(i).getProjectName(), actual.getProjects().get(i).getProjectName());
            assertEquals(expected.getProjects().get(i).getAmount(), actual.getProjects().get(i).getAmount());
        }
    }

    @Test
    void test_LoadEquipment() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IEquipmentService service = new EquipmentService(dataStorage);

        Equipment eq = service.getEquipment(2);

        List<EquipmentProject> expectedProjects = new ArrayList<>();
        expectedProjects.add(new EquipmentProject(0, 2, "Proj2", 1));
        expectedProjects.add(new EquipmentProject(0, 3, "Proj3", 2));
        Equipment expectedEquipment = new Equipment(0, "Eq2", 50, 2, expectedProjects);

        assertEquipmentEquals(expectedEquipment, eq);
    }

    @Test
    void test_AddEquipment() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IEquipmentService service = new EquipmentService(dataStorage);

        List<EquipmentProject> projects = new ArrayList<>();
        projects.add(new EquipmentProject(0, 1, "Proj1", 3));
        projects.add(new EquipmentProject(0, 3, "Proj3", 1));
        Equipment equipment = new Equipment(0, "Eq4", 70, 10, projects);
        int id = service.addEquipment(equipment);

        Equipment eq = service.getEquipment(id);

        assertEquipmentEquals(equipment, eq);
    }

    @Test
    void test_UpdateExistingEquipment() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IEquipmentService service = new EquipmentService(dataStorage);

        List<EquipmentProject> projects = new ArrayList<>();
        projects.add(new EquipmentProject(0, 1, "Proj1", 3));
        projects.add(new EquipmentProject(0, 3, "Proj3", 1));
        Equipment equipment = new Equipment(2, "Eq2x", 11, 100, projects);
        service.updateEquipment(equipment);

        Equipment eq = service.getEquipment(equipment.getID());

        assertEquipmentEquals(equipment, eq);
    }

    @Test
    void test_UpdateNonExistingEquipment() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IEquipmentService service = new EquipmentService(dataStorage);

        List<EquipmentProject> projects = new ArrayList<>();
        Equipment equipment = new Equipment(20, "Eq2x", 11, 100, projects);
        assertThrows(InvalidEntityIdException.class, () -> service.updateEquipment(equipment));
    }

    @Test
    void test_DeleteExistingEquipment() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IEquipmentService service = new EquipmentService(dataStorage);

        service.deleteEquipment(1);

        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getEquipments().getTodayFileName(), new String[]{
                EquipmentItem.Header,
                "2,Eq2,50.000000,2",
                "3,Eq3,3.000000,8"
        });

        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getProjectEquipments().getTodayFileName(), new String[]{
                ProjectEquipmentItem.Header,
                "4,2,2,1",
                "5,3,2,2",
                "6,3,3,3"
        });

    }

}
