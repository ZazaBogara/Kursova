package com.java.zakhar.DataStorage;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentItem extends DataItem {
    public static final String Header = "ID,FirstName,LastName,Course";

    @Setter(AccessLevel.PROTECTED)
    private String firstName;

    @Setter(AccessLevel.PROTECTED)
    private String lastName;

    @Setter(AccessLevel.PROTECTED)
    private String course;


    public StudentItem(int ID, String firstName, String lastName, String course) {
        super(ID);
        setFirstName(firstName);
        setLastName(lastName);
        setCourse(course);
    }

    @Override
    public String getCSVHeader() {
        return Header;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s,%s,%s", getId(), getFirstName(), getLastName(), getCourse());
    }

    @Override
    public void fromCSVString(String s) throws Exception {
        String[] items = s.split(",");
        if (items.length != 4)
            throw new Exception("StudentItem: Cannot parse CSV string: " + s);
        int id = Integer.parseInt(items[0]);
        setId(id);

        String firstName = items[1];
        setFirstName(firstName);

        String lastName = items[2];
        setLastName(lastName);

        String course = items[3];
        setCourse(course);

    }
}
