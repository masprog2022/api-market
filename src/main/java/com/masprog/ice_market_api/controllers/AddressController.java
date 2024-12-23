package com.masprog.ice_market_api.controllers;

import com.masprog.ice_market_api.payload.AddressDTO;
import com.masprog.ice_market_api.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Remover endereço", description = "Apenas os ADMIN conseguem ver",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço removido com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
            })
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar endereço", description = "Apenas os ADMIN conseguem ver",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço actualizado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)))
            })
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
}
