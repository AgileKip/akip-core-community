package org.akip.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class CardModel {

    private String title;
    private String subtitle;
    private TableModel tableModel = new TableModel();
    private List<CardChartModel> cardChartModels = new ArrayList<>();

    private boolean opened = true;

    public CardModel title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CardModel subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public CardModel addCardChartModel(CardChartModel cardChartModel) {
        cardChartModels.add(cardChartModel);
        return this;
    }

    public List<CardChartModel> getCardChartModels() {
        return cardChartModels;
    }

    public void setCardChartModels(List<CardChartModel> cardChartModels) {
        this.cardChartModels = cardChartModels;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
