package org.akip.dashboard.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PieChartModel extends ChartModel {

    List<String> labels = new ArrayList<>();
    List<BigDecimal> series = new ArrayList<>();
    List<String> seriesHumanReadable = new ArrayList<>();

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<BigDecimal> getSeries() {
        return series;
    }

    public void setSeries(List<BigDecimal> series) {
        this.series = series;
    }

    public List<String> getSeriesHumanReadable() {
        return seriesHumanReadable;
    }

    public void setSeriesHumanReadable(List<String> seriesHumanReadable) {
        this.seriesHumanReadable = seriesHumanReadable;
    }
}
