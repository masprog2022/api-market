package com.masprog.ice_market_api.services;

import com.masprog.ice_market_api.models.User;
import com.masprog.ice_market_api.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO address, User user);
    List<AddressDTO> getAddresses();
    AddressDTO getAddressesById(Long addressId);
    List<AddressDTO> getUserAddresses(User user);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDto);
    String deleteAddress(Long addressId);



}
