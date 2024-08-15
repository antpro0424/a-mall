package com.chuwa.service.impl;

import com.chuwa.po.Address;
import com.chuwa.repository.AddressRepository;
import com.chuwa.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));
    }

    public Page<Address> findAddressesByUserId(Long userId, Pageable pageable) {
        return addressRepository.findByUserId(userId, pageable);
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }
}
