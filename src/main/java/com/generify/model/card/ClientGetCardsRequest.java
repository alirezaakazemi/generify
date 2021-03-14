package com.generify.model.card;

public class ClientGetCardsRequest implements IClientCardRequest {

    private String cif;
    private String cardStatus;
    private int offset;

    public ClientGetCardsRequest() {
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
