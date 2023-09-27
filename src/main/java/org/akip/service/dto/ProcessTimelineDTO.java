package org.akip.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ProcessTimelineDTO {

    private String title;

    private List<ProcessTimelineItemDTO> items = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProcessTimelineItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ProcessTimelineItemDTO> items) {
        this.items = items;
    }
}
