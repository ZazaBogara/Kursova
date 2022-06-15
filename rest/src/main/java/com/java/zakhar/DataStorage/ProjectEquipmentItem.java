package com.java.zakhar.DataStorage;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectEquipmentItem extends DataItem {
    public static final String Header = "ID,ProjectID,EquipmentID,Amount";

    @Setter(AccessLevel.PROTECTED)
    private int projectID;

    @Setter(AccessLevel.PROTECTED)
    private int equipmentID;

    @Setter(AccessLevel.PROTECTED)
    private int amount;

    public ProjectEquipmentItem(int ID, int projectID, int equipmentID, int amount) {
        super(ID);
        setProjectID(projectID);
        setEquipmentID(equipmentID);
        setAmount(amount);
    }

    @Override
    public String getCSVHeader() {
        return Header;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%d,%d,%d", getId(), getProjectID(), getEquipmentID(), getAmount());
    }

    @Override
    public void fromCSVString(String s) throws Exception {
        String[] items = s.split(",");
        if (items.length != 4)
            throw new Exception("ProjectEquipmentItem: Cannot parse CSV string: " + s);
        int id = Integer.parseInt(items[0]);
        setId(id);

        int projectID = Integer.parseInt(items[1]);
        setProjectID(projectID);

        int equipmentID = Integer.parseInt(items[2]);
        setEquipmentID(equipmentID);

        int amount = Integer.parseInt(items[3]);
        setAmount(amount);
    }
}
