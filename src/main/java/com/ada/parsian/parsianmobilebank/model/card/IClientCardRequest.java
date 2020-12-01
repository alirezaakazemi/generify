package com.ada.parsian.parsianmobilebank.model.card;

import com.ada.parsian.parsianmobilebank.model.IClientRequest;

public interface IClientCardRequest extends IClientRequest {

    public ClientCardAuthorizeParams getCardAuthorizeParams();

    public void setCardAuthorizeParams(ClientCardAuthorizeParams cardAuthorizeParams);

}
