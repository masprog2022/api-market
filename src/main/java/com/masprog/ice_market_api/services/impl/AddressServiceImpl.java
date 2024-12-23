package com.masprog.ice_market_api.services.impl;

import com.masprog.ice_market_api.models.Address;
import com.masprog.ice_market_api.models.User;
import com.masprog.ice_market_api.payload.AddressDTO;
import com.masprog.ice_market_api.repositories.AddressRepository;
import com.masprog.ice_market_api.services.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(ModelMapper modelMapper, AddressRepository addressRepository) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO address, User user) {
        return null;
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getAddressesById(Long addressId) {
        return null;
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {
        return List.of();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDto) {
        return null;
    }

    @Override
    public String deleteAddress(Long addressId) {
        return "";
    }
}
