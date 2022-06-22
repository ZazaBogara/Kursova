package com.java.zakhar.datastorage;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EquipmentItem extends DataItem {
    public static final String Header = "ID,Name,Price,TotalAmount";

    @Setter(AccessLevel.PROTECTED)
    private String name;

    @Setter(AccessLevel.PROTECTED)
    private float price;

    @Setter(AccessLevel.PROTECTED)
    private int totalAmount;

    public EquipmentItem(int ID, String name, float price, int totalAmount) {
        super(ID);
        setName(name);
        setPrice(price);
        setTotalAmount(totalAmount);
    }

    @Override
    public String getCSVHeader() {
        return Header;
    }

    @Override
    public String toCSVString() {
        return String.format("%d,%s,%f,%d", getId(), getName(), getPrice(), getTotalAmount());
    }

    @Override
    public void fromCSVString(String s) throws Exception {
        String[] items = s.split(",");
        if (items.length != 4)
            throw new Exception("EquipmentItem: Cannot parse CSV string: " + s);
        int id = Integer.parseInt(items[0]);
        setId(id);

        String name = items[1];
        setName(name);

        float price = Float.parseFloat(items[2]);
        setPrice(price);

        int totalAmount = Integer.parseInt(items[3]);
        setTotalAmount(totalAmount);
    }
}
