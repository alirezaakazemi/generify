package com.ada.parsian.parsianmobilebank.model.card;

import com.ada.parsian.parsianmobilebank.Util.ParsianUtil;
import com.ada.parsian.parsianmobilebank.client.card.model.CardBean;

import java.util.ArrayList;
import java.util.List;

public class ClientGetCardsResponse implements IClientCardResponse {

    private List<ClientCardBean> cardBeans;

    public ClientGetCardsResponse() {
    }


    public List<ClientCardBean> getCardBeans() {
        return cardBeans;
    }

    public void setCardBeans(List<CardBean> cardBeans) {

        this.cardBeans = new ArrayList<>(cardBeans.size());

        for (CardBean cardBean : cardBeans) {
            ClientCardBean clientCardBean = new ClientCardBean();
            clientCardBean.setCardStatus(cardBean.getCardStatus());
            clientCardBean.setCardStatusCause(cardBean.getCardStatusCause());
            clientCardBean.setCardType(cardBean.getCardType());
            clientCardBean.setPan(cardBean.getPan());
            clientCardBean.setExpireDate(ParsianUtil.getDateTimeFromPattern(cardBean.getExpireDate()).getTime());
            clientCardBean.setIssueDate(ParsianUtil.getDateTimeFromPattern(cardBean.getIssueDate()).getTime());

            this.cardBeans.add(clientCardBean);
        }
    }
}
