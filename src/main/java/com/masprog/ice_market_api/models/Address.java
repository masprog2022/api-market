package com.masprog.ice_market_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long addressId;

    @NotBlank
    @Size(max = 10, message = "Street name must be at least 10 characters")
    private String street;

    @NotBlank
    @Size(max = 20, message = "Municipality name must be at least 10 characters")
    private String municipality;

    @NotBlank
    @Size(max = 20, message = "Province name must be at least 10 characters")
    private String province;

    @NotBlank
    @Size(min = 3, message = "Country name must be at least 2 characters")
    private String country;

    @NotBlank
    @Size(min = 5, message = "Postal Code must be at least 5 characters")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(){}

    public Address(String street, String municipality, String province, String country, String postalCode) {
        this.street = street;
        this.municipality = municipality;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
