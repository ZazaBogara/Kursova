package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.IDataStorage;
import com.java.zakhar.DataStorage.ProjectEquipmentItem;
import com.java.zakhar.DataStorage.ProjectItem;
import com.java.zakhar.DataStorage.StudentProjectItem;
import com.java.zakhar.IOService.IIoService;
import com.java.zakhar.Services.DataObject.Project;
import com.java.zakhar.Services.DataObject.ProjectEquipment;
import com.java.zakhar.Services.DataObject.ProjectStudent;
import com.java.zakhar.helpers.DataSetFileTestHelper;
import com.java.zakhar.helpers.InMemIoService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectsTests {

    void assertProjectEquals(Project expected, Project actual) {
        assertEquals(expected.getName(), actual.getName());

        assertEquals(expected.getStudents().size(), actual.getStudents().size());
        for (int i = 0; i < expected.getStudents().size(); i++) {
            assertEquals(expected.getStudents().get(i).getStudentID(), actual.getStudents().get(i).getStudentID());
            assertEquals(expected.getStudents().get(i).getCourse(), actual.getStudents().get(i).getCourse());
            assertEquals(expected.getStudents().get(i).getFirstName(), actual.getStudents().get(i).getFirstName());
            assertEquals(expected.getStudents().get(i).getLastName(), actual.getStudents().get(i).getLastName());
        }

        assertEquals(expected.getEquipments().size(), actual.getEquipments().size());
        for (int i = 0; i < expected.getEquipments().size(); i++) {
            assertEquals(expected.getEquipments().get(i).getEquipmentID(), actual.getEquipments().get(i).getEquipmentID());
            assertEquals(expected.getEquipments().get(i).getEquipmentName(), actual.getEquipments().get(i).getEquipmentName());
            assertEquals(expected.getEquipments().get(i).getAmount(), actual.getEquipments().get(i).getAmount());
        }
    }

    @Test
    void test_LoadProject() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IProjectService service = new ProjectService(dataStorage);

        Project eq = service.getProject(2);

        List<ProjectEquipment> expectedEquipments = new ArrayList<>();
        expectedEquipments.add(new ProjectEquipment(2, 1, "Eq1", 2));
        expectedEquipments.add(new ProjectEquipment(4, 2, "Eq2", 1));
        List<ProjectStudent> expectedStudents = new ArrayList<>();
        expectedStudents.add(new ProjectStudent(2, 1, "John", "Smith", "Course1"));//"2,Eq2,50,2"
        Project expectedProject = new Project(2, "Proj2", expectedEquipments, expectedStudents);

        assertProjectEquals(expectedProject, eq);
    }

    @Test
    void test_AddProject() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IProjectService service = new ProjectService(dataStorage);

        List<ProjectStudent> projectStudents = new ArrayList<>();
        List<ProjectEquipment> projectEquipments = new ArrayList<>();
        projectStudents.add(new ProjectStudent(0, 3, "Zack", "Anderson", "Course2"));
        projectEquipments.add(new ProjectEquipment(0, 3, "Eq3", 10));
        Project project = new Project(0, "Proj4", projectEquipments, projectStudents);
        int id = service.addProject(project);

        Project eq = service.getProject(id);

        assertProjectEquals(project, eq);
    }

    @Test
    void test_UpdateExistingProject() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IProjectService service = new ProjectService(dataStorage);

        List<ProjectStudent> projectStudents = new ArrayList<>();
        List<ProjectEquipment> projectEquipments = new ArrayList<>();
        projectStudents.add(new ProjectStudent(0, 3, "Zack", "Anderson", "Course2"));
        projectEquipments.add(new ProjectEquipment(0, 2, "Eq2", 10));
        Project project = new Project(3, "Proj3", projectEquipments, projectStudents);
        service.updateProject(project);

        Project eq = service.getProject(project.getID());

        assertProjectEquals(project, eq);
    }

    @Test
    void test_UpdateNonExistingProject() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IProjectService service = new ProjectService(dataStorage);

        List<ProjectStudent> projectStudents = new ArrayList<>();
        List<ProjectEquipment> projectEquipments = new ArrayList<>();
        Project project = new Project(200, "Proj4", projectEquipments, projectStudents);
        assertThrows(InvalidEntityIdException.class, () -> service.updateProject(project));
    }

    @Test
    void test_DeleteExistingProject() throws Exception {
        IIoService ioService = new InMemIoService();
        IDataStorage dataStorage = ServicesTestDataHelper.CreateDataStorage(ioService);
        IProjectService service = new ProjectService(dataStorage);

        service.deleteProject(1);

        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getProjects().getTodayFileName(), new String[]{
                ProjectItem.Header,
                "2,Proj2",
                "3,Proj3"
        });

        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getStudentProjects().getTodayFileName(), new String[]{
                StudentProjectItem.Header,
                "2,2,1",
                "3,3,2"
        });
        DataSetFileTestHelper.assertFileContentIs(ioService, dataStorage.getProjectEquipments().getTodayFileName(), new String[]{
                ProjectEquipmentItem.Header,
                "2,2,1,2",
                "3,3,1,1",
                "4,2,2,1",
                "5,3,2,2",
                "6,3,3,3"
        });
    }
}
