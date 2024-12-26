package com.masprog.ice_market_api.controllers;

import com.masprog.ice_market_api.models.User;
import com.masprog.ice_market_api.payload.AddressDTO;
import com.masprog.ice_market_api.payload.ProductDTO;
import com.masprog.ice_market_api.services.AddressService;
import com.masprog.ice_market_api.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Endereços", description = "Endpoints para gerenciar endereços" )
@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AuthUtil authUtil;

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Registar endereço", description = "Requisição feita por qualquer user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Endereço registado com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)))
            })
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);

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

    @Operation(summary = "Listar endereços pelo user", description = "Todos user logados podem ver seus endereços",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Endereço listados com sucesso ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDTO.class)))
            })
    @GetMapping("/addresses/user")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(){
        User user = authUtil.loggedInUser();

        List<AddressDTO> addressList = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }
}
