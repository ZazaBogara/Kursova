package com.java.zakhar.datastorage;

import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentTests {
    @Test
    void test_SaveItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        StudentsDataSet students = new StudentsDataSet(ioService);
        StudentItem student = new StudentItem(1, "TestName", "TestSurname", "2");
        students.setItem(student);
        students.save();

        String fileName = students.getTodayFileName();
        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                StudentItem.Header,
                "1,TestName,TestSurname,2"
        });
    }

    @Test
    void test_LoadItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new StudentsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, StudentItem.Header, new String[]{
                "2,TestName,TestSurname,2"
        });

        StudentsDataSet students = new StudentsDataSet(ioService);
        students.load();
        StudentItem student = students.getItem(2);
        assertNotNull(student);
        assertEquals(student.getFirstName(), "TestName");
        assertEquals(student.getLastName(), "TestSurname");
        assertEquals(student.getCourse(), "2");
    }

    @Test
    void test_DeleteItem() throws Exception {
        InMemIoService ioService = new InMemIoService();
        String fileName = new StudentsDataSet(ioService).getTodayFileName();
        DataSetFileTestHelper.createFile(ioService, fileName, StudentItem.Header, new String[]{
                "3,TestName,TestSurname,2"
        });

        StudentsDataSet students = new StudentsDataSet(ioService);
        students.load();

        students.deleteItem(3);
        students.save();

        DataSetFileTestHelper.assertFileContentIs(ioService, fileName, new String[]{
                StudentItem.Header
        });
    }

    @Test
    void test_CreateThenRead() throws Exception {
        InMemIoService ioService = new InMemIoService();
        int id;
        StudentItem student1;
        {
            StudentsDataSet students = new StudentsDataSet(ioService);
            id = students.getNewID();
            student1 = new StudentItem(1, "TestName", "TestSurname", "2");
            students.setItem(student1);
            students.save();
        }
        {
            StudentsDataSet students = new StudentsDataSet(ioService);
            students.load();
            StudentItem student2 = students.getItem(id);
            assertNotNull(student2);
            assertEquals(student1, student2);
        }
    }
}
