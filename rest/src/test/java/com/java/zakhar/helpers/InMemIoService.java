package com.java.zakhar.helpers;

import com.java.zakhar.ioservice.IIoService;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;

public class InMemIoService implements IIoService {
    final private HashMap<String, byte[]> files = new HashMap<>();


    @Override
    public String[] listFiles() {
        return files.keySet().stream().sorted().toArray(String[]::new);
    }

    @Override
    public BufferedReader openFile(String fileName) throws FileNotFoundException {
        byte[] data = files.get(fileName);
        if (data == null)
            throw new FileNotFoundException(String.format("File %s is not found", fileName));
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        return new BufferedReader(new InputStreamReader(input, Charset.defaultCharset()));
    }

    @Override
    public PrintWriter createFile(String fileName) {
        ByteArrayOutputStream output = new MyByteArrayOutputStream(files, fileName);
        return new PrintWriter(output, false, Charset.defaultCharset());
    }

    private static class MyByteArrayOutputStream extends ByteArrayOutputStream {
        final private HashMap<String, byte[]> files;
        final private String fileName;

        public MyByteArrayOutputStream(HashMap<String, byte[]> files, String fileName) {
            this.files = files;
            this.fileName = fileName;
        }

        @Override
        public void close() {
            byte[] data = toByteArray();
            files.put(fileName, data);

//            String filePath = Path.of("D:\\KursData", fileName).toString();
//            try(FileOutputStream fw = new FileOutputStream(filePath)) {
//                fw.write(data);
//            }
        }
    }
}
