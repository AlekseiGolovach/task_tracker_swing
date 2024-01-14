package com.golovach.task_tracker_swing.ui.table_models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private int columnCount = 5;
    private List<List<String>> dataList = new ArrayList<>();

    public TaskTableModel() {

    }

    public void removeAllRow() {
        dataList = new ArrayList<>();
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
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0) return Boolean.class;
        return String.class;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0) return "Выполнена";
        if (columnIndex == 1) return "Задача";
        if (columnIndex == 2) return "Дата начала";
        if (columnIndex == 3) return "Дата окончания";
        if (columnIndex == 4) return "Дата выполнения";
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object s = dataList.get(rowIndex).get(columnIndex);
        if(columnIndex == 0 && s instanceof String) return Boolean.valueOf((String) s);
        return s;
    }

    public void addData(List<String> row) {
        if (columnCount == row.size()) {
            dataList.add(row);
        }
    }


}
