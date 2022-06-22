package com.java.zakhar.datastorage;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectItem extends DataItem {
    public static final String HEADER = "ID,Name";

    @Setter(AccessLevel.PROTECTED)
    private String name;

    public ProjectItem(int ID, String name) {
        super(ID);
        setName(name);
    }


    @Override
    public String getCSVHeader() {
        return HEADER;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s", getId(), getName());
    }

    @Override
    public void fromCSVString(String s) throws Exception {
        String[] items = s.split(",");
        if (items.length != 2)
            throw new Exception("ProjectItem: Cannot parse CSV string: " + s);
        int id = Integer.parseInt(items[0]);
        setId(id);

        String name = items[1];
        setName(name);
    }

}
