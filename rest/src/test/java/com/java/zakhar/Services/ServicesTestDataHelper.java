package com.java.zakhar.Services;

import com.java.zakhar.DataStorage.*;
import com.java.zakhar.IOService.IIoService;
import com.java.zakhar.helpers.DataSetFileTestHelper;

public class ServicesTestDataHelper {

    public static IDataStorage CreateDataStorage(IIoService ioService) throws Exception {
        DataStorage result = new DataStorage(ioService);
        CreateEquipmentsTestFile(ioService, result.getEquipments());
        CreateProjectsTestFile(ioService, result.getProjects());
        CreateStudentsTestFile(ioService, result.getStudents());
        CreateProjectEquipmentsTestFile(ioService, result.getProjectEquipments());
        CreateStudentProjectsTestFile(ioService, result.getStudentProjects());

        result.loadAll();
        return result;
    }

    private static void CreateEquipmentsTestFile(IIoService ioService, EquipmentsDataSet dataSet) throws Exception {
        DataSetFileTestHelper.createFile(ioService, dataSet.getTodayFileName(), EquipmentItem.Header, new String[]{
                "1,Eq1,12,10",
                "2,Eq2,50,2",
                "3,Eq3,3,8"
        });
    }

    private static void CreateProjectsTestFile(IIoService ioService, ProjectsDataSet dataSet) throws Exception {
        DataSetFileTestHelper.createFile(ioService, dataSet.getTodayFileName(), ProjectItem.Header, new String[]{
                "1,Proj1",
                "2,Proj2",
                "3,Proj3"
        });
    }

    private static void CreateStudentsTestFile(IIoService ioService, StudentsDataSet dataSet) throws Exception {
        DataSetFileTestHelper.createFile(ioService, dataSet.getTodayFileName(), StudentItem.Header, new String[]{
                "1,John,Smith,Course1",
                "2,Kelly,Gordon,Course1",
                "3,Zack,Anderson,Course2"
        });
    }

    private static void CreateProjectEquipmentsTestFile(IIoService ioService, ProjectEquipmentsDataSet dataSet) throws Exception {
        DataSetFileTestHelper.createFile(ioService, dataSet.getTodayFileName(), ProjectEquipmentItem.Header, new String[]{
                "1,1,1,1",
                "2,2,1,2",
                "3,3,1,1",
                "4,2,2,1",
                "5,3,2,2",
                "6,3,3,3"
        });
    }

    private static void CreateStudentProjectsTestFile(IIoService ioService, StudentProjectsDataSet dataSet) throws Exception {
        DataSetFileTestHelper.createFile(ioService, dataSet.getTodayFileName(), StudentProjectItem.Header, new String[]{
                "1,1,1",
                "2,2,1",
                "3,3,2",
        });
    }


}
