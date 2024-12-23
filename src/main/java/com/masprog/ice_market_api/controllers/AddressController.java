package com.masprog.ice_market_api.controllers;

import com.masprog.ice_market_api.payload.AddressDTO;
import com.masprog.ice_market_api.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Endereços", description = "Endpoints para gerenciar endereços" )
@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Listar todos endereços", description = "Apenas os ADMIN conseguem ver",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço listado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)))
            })
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressList = addressService.getAddresses();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }


    @Operation(summary = "Mostrar endereço pelo ID", description = "Apenas os ADMIN conseguem ver",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço flitrado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)))
            })
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressesById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }
}
