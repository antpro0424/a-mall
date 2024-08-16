package com.chuwa.controller;

import com.chuwa.exception.BadRequestException;

import com.chuwa.po.Address;
import com.chuwa.service.AddressService;
import com.chuwa.utils.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name="Address Controller", description = "APIs for operating user's addresses.")
@RestController
@RequestMapping("/api/v0/accounts/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @Operation(summary = "Get address by address id")
    @GetMapping("{addressId}")
    public Address getAddressById(@PathVariable("addressId") Long addressId) {
        System.out.println("USER id: " + UserContext.getUser());
        Address address = addressService.getAddressById(addressId);

        Long userId = UserContext.getUser();
        if (!address.getUserId().equals(userId)) {
            throw new BadRequestException("Address does not belong to user.");
        }
        return address;
    }

    // TODO: Other APIs, like find all addresses according to the userId.
    @Operation(summary = "Get all addresses of current user")
    @GetMapping
    public Page<Address> findMyAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long userId = UserContext.getUser();
        Pageable pageable = PageRequest.of(page, size);
        return addressService.findAddressesByUserId(userId, pageable);
    }

    @Operation(summary = "Add an address of current user")
    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        address.setUserId(UserContext.getUser());
        return addressService.addAddress(address);
    }

//    @Operation(summary = "update address information")
//    @PostMapping("/update")
//    public Address updateAddress(@RequestBody Address address) {
//        return addressService.updateAddress(address);
//    }
    
}
