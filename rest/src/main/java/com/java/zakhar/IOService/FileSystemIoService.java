package com.java.zakhar.ioservice;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystemException;
import java.nio.file.Path;

public class FileSystemIoService implements IIoService {
    private final String storageDirectory;

    public FileSystemIoService(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    @Override
    public String[] listFiles() {
        File dir = new File(storageDirectory);
        File[] files = dir.listFiles();
        String[] fileNames;
        if (files != null) {
            fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++)
                fileNames[i] = files[i].getName();
        } else {
            fileNames = new String[0];
        }

        return fileNames;
    }

    @Override
    public BufferedReader openFile(String fileName) throws IOException {
        String filePath = Path.of(storageDirectory, fileName).toString();
        return new BufferedReader(new FileReader(filePath, Charset.defaultCharset()));
    }

    @Override
    public PrintWriter createFile(String fileName) throws IOException {
        String filePath = Path.of(storageDirectory, fileName).toString();
        return new PrintWriter(new FileWriter(filePath, Charset.defaultCharset()));
    }

    public void ensureFolderExist() throws FileSystemException {
        File f = new File(storageDirectory);
        if (f.exists() && f.isDirectory())
            return;
        if (!f.mkdir())
            throw new FileSystemException(storageDirectory, "Directory cannot be created", "unknown");
    }
}
