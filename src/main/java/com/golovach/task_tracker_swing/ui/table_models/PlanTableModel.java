package com.golovach.task_tracker_swing.ui.table_models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PlanTableModel extends AbstractTableModel {
    private int columnCount = 32;

    private List<List<String>> dataList = new ArrayList<>();
    public PlanTableModel() {
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }
    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) return "Задача";
        return String.valueOf(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            return dataList.get(rowIndex).get(columnIndex);
        }

        return dataList.get(rowIndex).get(columnIndex);
    }

    public void addData(List<String> row) {
        dataList.add(row);
    }


    public void removeAllRow() {
        dataList = new ArrayList<>();
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount + 1;
    }
}
