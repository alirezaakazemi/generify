package com.generify.model.card;

public class ClientCardBean {
    private String cardStatus;
    private String cardStatusCause;
    private String cardType;
    private Long expireDate;
    private Long issueDate;
    private String pan;

    public ClientCardBean() {
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatusCause() {
        return cardStatusCause;
    }

    public void setCardStatusCause(String cardStatusCause) {
        this.cardStatusCause = cardStatusCause;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
