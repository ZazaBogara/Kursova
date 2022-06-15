package com.java.zakhar.DataStorage;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
abstract class DataItem {
    @Setter(AccessLevel.PROTECTED)
    private int id;

    protected DataItem(final int id) {
        setId(id);
    }

    public abstract String getCSVHeader();

    public abstract String toCSVString();

    public abstract void fromCSVString(String s) throws Exception;
}
