package org.akip.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class BarChartModel extends ChartModel {

    private List<String> categories = new ArrayList<>();
    private List<SerieModel> series = new ArrayList<>();

    public BarChartModel categories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public BarChartModel addSerie(SerieModel serie) {
        this.series.add(serie);
        return this;
    }

    public BarChartModel series(List<SerieModel> series) {
        this.series = series;
        return this;
    }

    public List<SerieModel> getSeries() {
        return series;
    }

    public void setSeries(List<SerieModel> series) {
        this.series = series;
    }
}
