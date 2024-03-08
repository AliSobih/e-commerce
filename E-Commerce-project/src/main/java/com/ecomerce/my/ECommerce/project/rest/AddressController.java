package com.ecomerce.my.ECommerce.project.rest;

import com.ecomerce.my.ECommerce.project.Service.AddressServer;
import com.ecomerce.my.ECommerce.project.dto.AddressDTO;
import com.ecomerce.my.ECommerce.project.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressServer addressServer;

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestBody AddressDTO addressDTO) {

        return addressServer.createAddress(addressDTO)
                ? new ResponseEntity<>(HttpStatus.CREATED): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return new ResponseEntity<>(addressServer.getAllAddresses(), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Address> updateAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressServer.updateAddress(addressDTO);
        return address != null
                ? new ResponseEntity<>(address, HttpStatus.CREATED): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable Long id) {
        boolean address = addressServer.deleteAddress(id);
        return addressServer.deleteAddress(id)
                ? new ResponseEntity<>( HttpStatus.NO_CONTENT): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
