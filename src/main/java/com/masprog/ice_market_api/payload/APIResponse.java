package com.masprog.ice_market_api.payload;

import lombok.*;

@Data
@NoArgsConstructor
public class APIResponse {
    public String message;
    private boolean status;

    public APIResponse(String message, boolean status){
        this.message = message;
        this.status = status;
    }
}
