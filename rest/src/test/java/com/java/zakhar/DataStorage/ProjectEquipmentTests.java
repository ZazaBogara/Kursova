package com.java.zakhar.datastorage;

import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectEquipmentTests {
    @Test
    void test_SaveItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        ProjectEquipmentsDataSet projectEquipments = new ProjectEquipmentsDataSet(ioService);
        ProjectEquipmentItem projectEquipment = new ProjectEquipmentItem(1, 1, 1, 3);
        projectEquipments.setItem(projectEquipment);
        projectEquipments.save();

        String fileName = projectEquipments.getTodayFileName();
        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                ProjectEquipmentItem.Header,
                "1,1,1,3"
        });
    }

    @Test
    void test_LoadItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new ProjectEquipmentsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, ProjectEquipmentItem.Header, new String[]{
                "2,1,1,1"
        });

        ProjectEquipmentsDataSet projectEquipments = new ProjectEquipmentsDataSet(ioService);
        projectEquipments.load();
        ProjectEquipmentItem projectEquipment = projectEquipments.getItem(2);
        assertNotNull(projectEquipment);
        assertEquals(projectEquipment.getProjectID(), 1);
        assertEquals(projectEquipment.getEquipmentID(), 1);
        assertEquals(projectEquipment.getAmount(), 1);
    }

    @Test
    void test_DeleteItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new ProjectEquipmentsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, ProjectEquipmentItem.Header, new String[]{
                "3,1,1,1"
        });

        ProjectEquipmentsDataSet projects = new ProjectEquipmentsDataSet(ioService);
        projects.load();

        projects.deleteItem(3);
        projects.save();

        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                ProjectEquipmentItem.Header
        });
    }

    @Test
    void test_CreateThenRead() throws Exception {
        InMemIoService ioService = new InMemIoService();
        int id;
        ProjectItem project1;
        {
            ProjectsDataSet projects = new ProjectsDataSet(ioService);
            id = projects.getNewID();
            project1 = new ProjectItem(1, "TestName");
            projects.setItem(project1);
            projects.save();
        }
        {
            ProjectsDataSet projects = new ProjectsDataSet(ioService);
            projects.load();
            ProjectItem project2 = projects.getItem(id);
            assertNotNull(project2);
            assertEquals(project1, project2);
        }
    }
}
