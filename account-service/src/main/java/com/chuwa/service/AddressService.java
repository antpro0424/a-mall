package com.chuwa.service;

import com.chuwa.po.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    public Address getAddressById(Long id);

    public Page<Address> findAddressesByUserId(Long userId, Pageable pageable);
}
