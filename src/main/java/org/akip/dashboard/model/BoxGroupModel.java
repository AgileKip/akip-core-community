package org.akip.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class BoxGroupModel extends GroupModel {

    private String type = "boxGroup";
    private List<BoxModel> boxes = new ArrayList<>();

    private boolean opened = true;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BoxGroupModel addBox(BoxModel boxModel) {
        boxes.add(boxModel);
        return this;
    }

    public List<BoxModel> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxModel> boxes) {
        this.boxes = boxes;
    }

    @Override
    public BoxGroupModel title(String title) {
        return (BoxGroupModel) super.title(title);
    }

    @Override
    public BoxGroupModel subtitle(String subtitle) {
        return (BoxGroupModel) super.subtitle(subtitle);
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
