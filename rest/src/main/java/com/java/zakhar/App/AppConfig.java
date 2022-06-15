package com.java.zakhar.App;

import com.java.zakhar.DataStorage.DataStorage;
import com.java.zakhar.DataStorage.IDataStorage;
import com.java.zakhar.IOService.FileSystemIoService;
import com.java.zakhar.IOService.IIoService;
import com.java.zakhar.Services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystemException;

@Configuration
public class AppConfig {

    @Bean
    IIoService getIoService() throws FileSystemException {
        String folderPath = "D:\\KursData";
        FileSystemIoService ioService = new FileSystemIoService(folderPath);
        ioService.ensureFolderExist();
        return ioService;
    }

    @Bean
    IDataStorage getDataStorage(final IIoService ioService) throws Exception {
        DataStorage dataStorage = new DataStorage(ioService);
        dataStorage.loadAll();
        return dataStorage;
    }

    @Bean
    IEquipmentService getEquipmentService(final IDataStorage dataStorage) {
        return new EquipmentService(dataStorage);
    }

    @Bean
    IProjectService getProjectService(final IDataStorage dataStorage) {
        return new ProjectService(dataStorage);
    }

    @Bean
    IStudentService getStudentService(final IDataStorage dataStorage) {
        return new StudentService(dataStorage);
    }

}
