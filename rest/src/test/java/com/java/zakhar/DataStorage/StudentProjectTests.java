package com.java.zakhar.datastorage;

import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentProjectTests {
    @Test
    void test_SaveItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        StudentProjectsDataSet studentProjects = new StudentProjectsDataSet(ioService);
        StudentProjectItem studentProject = new StudentProjectItem(1, 1, 1);
        studentProjects.setItem(studentProject);
        studentProjects.save();

        String fileName = studentProjects.getTodayFileName();
        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                StudentProjectItem.Header,
                "1,1,1"
        });
    }

    @Test
    void test_LoadItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new StudentProjectsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, StudentProjectItem.Header, new String[]{
                "2,1,1"
        });

        StudentProjectsDataSet studentProjects = new StudentProjectsDataSet(ioService);
        studentProjects.load();
        StudentProjectItem studentProject = studentProjects.getItem(2);
        assertNotNull(studentProject);
        assertEquals(studentProject.getProjectID(), 1);
        assertEquals(studentProject.getStudentID(), 1);
    }

    @Test
    void test_DeleteItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new StudentProjectsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, StudentProjectItem.Header, new String[]{
                "3,1,1"
        });

        StudentProjectsDataSet studentProjects = new StudentProjectsDataSet(ioService);
        studentProjects.load();

        studentProjects.deleteItem(3);
        studentProjects.save();

        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                StudentProjectItem.Header
        });
    }

    @Test
    void test_CreateThenRead() throws Exception {
        InMemIoService ioService = new InMemIoService();
        int id;
        StudentProjectItem studentProject1;
        {
            StudentProjectsDataSet studentProjects = new StudentProjectsDataSet(ioService);
            id = studentProjects.getNewID();
            studentProject1 = new StudentProjectItem(id, 1, 1);
            studentProjects.setItem(studentProject1);
            studentProjects.save();
        }
        {
            StudentProjectsDataSet studentProjects = new StudentProjectsDataSet(ioService);
            studentProjects.load();
            StudentProjectItem studentProject2 = studentProjects.getItem(id);
            assertNotNull(studentProject2);
            assertEquals(studentProject1, studentProject2);
        }
    }

}
