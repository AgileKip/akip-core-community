package org.akip.dashboard.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SerieModel {

    private String name;
    private List<BigDecimal> data = new ArrayList<>();
    private List<String> dataHumanReadable = new ArrayList<>();

    public SerieModel name(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SerieModel data(List<BigDecimal> data) {
        this.data = data;
        return this;
    }

    public List<BigDecimal> getData() {
        return data;
    }

    public void setData(List<BigDecimal> data) {
        this.data = data;
    }

    public SerieModel dataHumanReadable(List<String> dataHumanReadable) {
        this.dataHumanReadable = dataHumanReadable;
        return this;
    }

    public List<String> getDataHumanReadable() {
        return dataHumanReadable;
    }

    public void setDataHumanReadable(List<String> dataHumanReadable) {
        this.dataHumanReadable = dataHumanReadable;
    }
}
