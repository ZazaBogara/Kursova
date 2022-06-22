package com.java.zakhar.datastorage;

import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectTests {
    @Test
    void test_SaveItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        ProjectsDataSet projects = new ProjectsDataSet(ioService);
        ProjectItem project = new ProjectItem(1, "TestName");
        projects.setItem(project);
        projects.save();

        String fileName = projects.getTodayFileName();
        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                ProjectItem.HEADER,
                "1,TestName"
        });
    }

    @Test
    void test_LoadItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new ProjectsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, ProjectItem.HEADER, new String[]{
                "2,TestName"
        });

        ProjectsDataSet projects = new ProjectsDataSet(ioService);
        projects.load();
        ProjectItem project = projects.getItem(2);
        assertNotNull(project);
        assertEquals(project.getName(), "TestName");
    }

    @Test
    void test_DeleteItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new ProjectsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, ProjectItem.HEADER, new String[]{
                "3,TestName"
        });

        ProjectsDataSet projects = new ProjectsDataSet(ioService);
        projects.load();

        projects.deleteItem(3);
        projects.save();

        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                ProjectItem.HEADER
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
