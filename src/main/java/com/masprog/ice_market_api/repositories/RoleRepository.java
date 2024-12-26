package com.masprog.ice_market_api.repositories;

import com.masprog.ice_market_api.models.enums.AppRole;
import com.masprog.ice_market_api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
