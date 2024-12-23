package com.masprog.ice_market_api.models;

import jakarta.persistence.*;
import lombok.ToString;

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long
            roleId;
    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private AppRole roleName;

    public Role(AppRole roleName){
        this.roleName = roleName;
    }
}
