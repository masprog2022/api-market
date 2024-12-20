package com.masprog.ice_market_api.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    public String message;
    private boolean status;
}
