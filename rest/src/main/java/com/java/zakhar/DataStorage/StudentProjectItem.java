package com.java.zakhar.datastorage;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentProjectItem extends DataItem {
    public static final String Header = "ID,ProjectID,StudentID";

    @Setter(AccessLevel.PROTECTED)
    private int studentID;

    @Setter(AccessLevel.PROTECTED)
    private int projectID;

    public StudentProjectItem(int ID, int studentID, int projectID) {
        super(ID);
        setProjectID(projectID);
        setStudentID(studentID);
    }

    @Override
    public String getCSVHeader() {
        return Header;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%d,%d", getId(), getProjectID(), getStudentID());
    }

    @Override
    public void fromCSVString(String s) throws Exception {
        String[] items = s.split(",");
        if (items.length != 3)
            throw new Exception("StudentProjectItem: Cannot parse CSV string: " + s);
        int id = Integer.parseInt(items[0]);
        setId(id);

        int projectID = Integer.parseInt(items[1]);
        setProjectID(projectID);

        int studentID = Integer.parseInt(items[2]);
        setStudentID(studentID);

    }
}
