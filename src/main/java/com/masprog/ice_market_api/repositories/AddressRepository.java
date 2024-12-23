package com.masprog.ice_market_api.repositories;

import com.masprog.ice_market_api.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
