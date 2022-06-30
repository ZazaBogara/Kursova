package com.java.zakhar.datastorage;

import com.java.zakhar.ioservice.IIoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public abstract class DataSet<T extends DataItem> {
    private final String entityName;
    private final IIoService ioService;
    private final HashMap<Integer, T> items = new HashMap<>();

    private String fileName;
    private int lastId;

    public DataSet(String entityName, IIoService ioService) {
        this.entityName = entityName;
        this.ioService = ioService;
    }

    public String getTodayFileName() {
        String currentDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        fileName = String.format("%s-%s.csv", entityName, currentDateStr);
        return fileName;
    }

    public void load() throws Exception {
        String currentYearMonthStr = new SimpleDateFormat("yyyy-MM-").format(new Date());
        String filePrefix = String.format("%s-%s", entityName, currentYearMonthStr);

        String[] files = ioService.listFiles();

        for (String file : files) {
            if (file.startsWith(filePrefix) && file.endsWith(".csv"))
                loadFile(file);
        }
    }

    private void loadFile(String fileName) throws Exception {
        try (BufferedReader br = ioService.openFile(fileName)) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                T item = createItem();
                item.fromCSVString(line);
                setItem(item);
                if (lastId < item.getId())
                    lastId = item.getId();
            }
        }

        this.fileName = fileName;
    }

    public void save() throws IOException {
        if (fileName == null) {
            fileName = getTodayFileName();
        }

        try (PrintWriter writer = ioService.createFile(fileName)) {
            String header = createItem().getCSVHeader();
            writer.println(header);

            for (T item : getItems()) {
                String s = item.toCSVString();
                writer.println(s);
            }
        }
    }

    protected abstract T createItem();

    public int getNewID() {
        return ++lastId;
    }

    public Iterable<T> getItems() {
        return items.values();
    }

    public T getItem(int ID) {
        return items.get(ID);
    }

    public void setItem(T item) {
        items.put(item.getId(), item);
    }

    public void deleteItem(int id) {
        items.remove(id);
    }
}
