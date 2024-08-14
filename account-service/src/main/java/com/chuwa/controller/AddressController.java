package com.chuwa.controller;

import com.chuwa.po.Address;
import com.chuwa.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Address Controller", description = "APIs for operating user's addresses.")
@RestController
@RequestMapping("/api/v0/account/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @Operation(summary = "Get address by address id")
    @GetMapping("{addressId}")
    public Address getAddressById(@PathVariable("addressId") Long addressId) {
        Address address = addressService.getAddressById(addressId);
//        TODO: After finishing login and save the user info to ApplicationContext, validate if current userid matches the userid of this address
        return address;
    }

    // TODO: Other APIs, like find all addresses according to the userId.
    @Operation
    @GetMapping
    public List<Address> getAddresses() {
        return null;
    }
}
