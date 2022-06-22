package com.java.zakhar.helpers;

import com.java.zakhar.ioservice.IIoService;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.PrintWriter;

public final class DataSetFileTestHelper {
    public static void assertFileContentIs(IIoService ioService, String fileName, String[] lines) throws Exception {
        int lineNo = 0;
        try (BufferedReader br = ioService.openFile(fileName)) {
            String line;
            while ((line = br.readLine()) != null) {
                Assertions.assertEquals(lines[lineNo++], line);
            }
        }
    }

    public static void createFile(IIoService ioService, String fileName, String header, String[] lines) throws Exception {
        try (PrintWriter writer = ioService.createFile(fileName)) {
            writer.println(header);
            for (String line : lines) {
                writer.println(line);
            }
        }

    }
}
