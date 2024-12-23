package com.masprog.ice_market_api.services.impl;

import com.masprog.ice_market_api.controllers.AddressController;
import com.masprog.ice_market_api.exceptions.ResourceNotFoundException;
import com.masprog.ice_market_api.models.Address;
import com.masprog.ice_market_api.models.User;
import com.masprog.ice_market_api.payload.AddressDTO;
import com.masprog.ice_market_api.repositories.AddressRepository;
import com.masprog.ice_market_api.repositories.UserRepository;
import com.masprog.ice_market_api.services.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    public AddressServiceImpl(ModelMapper modelMapper, AddressRepository addressRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        List<Address> addressList = user.getAddressList();
        addressList.add(address);
        user.setAddressList(addressList);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
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
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {
       List<Address> addresses = user.getAddressList();
       return addresses.stream()
               .map(address -> modelMapper.map(address, AddressDTO.class))
               .toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDto) {
       Address addressFromDatabase = addressRepository.findById(addressId)
               .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        addressFromDatabase.setStreet(addressDto.getStreet());
        addressFromDatabase.setPostalCode(addressDto.getPostalCode());
        addressFromDatabase.setMunicipality(addressDto.getMunicipality());
        addressFromDatabase.setCountry(addressDto.getCountry());
        addressFromDatabase.setProvince(addressDto.getProvince());

        Address updatedAddress = addressRepository.save(addressFromDatabase);

        User user = addressFromDatabase.getUser();
        user.getAddressList().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddressList().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        User user = addressFromDatabase.getUser();
        user.getAddressList().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDatabase);

        return "Address deleted successfully with addressId: " + addressId;
    }



}
