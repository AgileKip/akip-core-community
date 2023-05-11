package org.akip.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class TableModel {

    private List<String> heads = new ArrayList<>();
    private List<TableRowModel> rows = new ArrayList<>();

    public TableModel addHead(String head) {
        getHeads().add(head);
        return this;
    }

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(List<String> heads) {
        this.heads = heads;
    }

    public TableModel addRow(TableRowModel row) {
        getRows().add(row);
        return this;
    }

    public List<TableRowModel> getRows() {
        return rows;
    }

    public void setRows(List<TableRowModel> rows) {
        this.rows = rows;
    }
}
