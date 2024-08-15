package com.chuwa.service;

import com.chuwa.po.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    Address getAddressById(Long id);

    Page<Address> findAddressesByUserId(Long userId, Pageable pageable);

    Address addAddress(Address address);

//    Address updateAddress(Address address);
}
