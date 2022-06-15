package com.java.zakhar.IOService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public interface IIoService {
    String[] listFiles();

    BufferedReader openFile(String fileName) throws IOException;

    PrintWriter createFile(String fileName) throws IOException;
}
