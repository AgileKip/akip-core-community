package org.akip.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class CardGroupModel extends GroupModel {

    private String type = "cardGroup";
    private List<CardModel> cards = new ArrayList<>();

    @Override
    public CardGroupModel title(String title) {
        return (CardGroupModel) super.title(title);
    }

    @Override
    public CardGroupModel subtitle(String subtitle) {
        return (CardGroupModel) super.subtitle(subtitle);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CardGroupModel addCard(CardModel cardModel) {
        getCards().add(cardModel);
        return this;
    }

    public List<CardModel> getCards() {
        return cards;
    }

    public void setCards(List<CardModel> cards) {
        this.cards = cards;
    }
}
