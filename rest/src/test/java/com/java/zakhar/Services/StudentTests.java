package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.EquipmentItem;
import com.java.zakhar.DataStorage.IDataStorage;
import com.java.zakhar.DataStorage.StudentProjectItem;
import com.java.zakhar.IOService.IIoService;
import com.java.zakhar.Services.DataObject.Student;
import com.java.zakhar.Services.DataObject.StudentProject;
import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentTests {

    void assertStudentEquals(Student expected, Student actual) {
        assertEquals(expected.getCourse(), actual.getCourse());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());

        assertEquals(expected.getProjects().size(), actual.getProjects().size());
        for (int i = 0; i < expected.getProjects().size(); i++) {
            assertEquals(expected.getProjects().get(i).getProjectID(), actual.getProjects().get(i).getProjectID());
            assertEquals(expected.getProjects().get(i).getProjectName(), actual.getProjects().get(i).getProjectName());
        }
    }

    @Test
    void test_LoadStudent() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IStudentService service = new StudentService(dataStorage);

        Student eq = service.getStudent(2);

        List<StudentProject> expectedProjects = new ArrayList<>();
        expectedProjects.add(new StudentProject(3, 3, "Proj3"));
        Student expectedStudent = new Student(0, "Kelly", "Gordon", "Course1", expectedProjects);

        assertStudentEquals(expectedStudent, eq);
    }

    @Test
    void test_AddStudent() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IStudentService service = new StudentService(dataStorage);

        List<StudentProject> projects = new ArrayList<>();
        projects.add(new StudentProject(0, 3, "Proj3"));
        projects.add(new StudentProject(0, 2, "Proj2"));
        Student student = new Student(0, "Jenny", "Grad", "Course1", projects);
        int id = service.addStudent(student);

        Student eq = service.getStudent(id);

        assertStudentEquals(student, eq);
    }

    @Test
    void test_UpdateExistingStudent() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IStudentService service = new StudentService(dataStorage);

        List<StudentProject> projects = new ArrayList<>();
        projects.add(new StudentProject(0, 3, "Proj3"));
        projects.add(new StudentProject(0, 2, "Proj2"));
        Student student = new Student(1, "John", "Smith", "Course1", projects);
        service.updateStudent(student);

        Student eq = service.getStudent(student.getID());

        assertStudentEquals(student, eq);
    }

    @Test
    void test_UpdateNonExistingStudent() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IStudentService service = new StudentService(dataStorage);

        List<StudentProject> projects = new ArrayList<>();
        Student student = new Student(0, "John", "Smith", "Course1", projects);
        assertThrows(InvalidEntityIdException.class, () -> service.updateStudent(student));
    }

    @Test
    void test_DeleteExistingStudent() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IStudentService service = new StudentService(dataStorage);

        service.deleteStudent(1);

        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getEquipments().getTodayFileName(), new String[]{
                EquipmentItem.Header,
                "1,Eq1,12,10",
                "2,Eq2,50,2",
                "3,Eq3,3,8"
        });

        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getStudentProjects().getTodayFileName(), new String[]{
                StudentProjectItem.Header,
                "3,3,2"
        });

    }
}
