package com.masprog.ice_market_api.exceptions;

public class APIException extends RuntimeException{
    public APIException(String message){
        super(message);
    }

    public APIException(){

    }
}
