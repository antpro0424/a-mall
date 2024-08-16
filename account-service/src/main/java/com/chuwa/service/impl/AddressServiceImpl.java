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
        Long userId = address.getUserId();

        // 查找当前用户的默认地址
        Address currentDefaultAddress = addressRepository.findByUserIdAndIsDefaultTrue(userId);

        // 如果用户已经有默认地址
        if (currentDefaultAddress != null) {
            if (address.getIsDefault() != null && address.getIsDefault()) {
                // 如果新地址也被标记为默认地址
                // 将原来的默认地址设为非默认
                currentDefaultAddress.setIsDefault(false);
                addressRepository.save(currentDefaultAddress);
            } else {
                // 如果新地址没有被标记为默认地址
                // 将新地址的默认标志设为 false
                address.setIsDefault(false);
            }
        } else {
            // 如果用户没有默认地址，将新地址设为默认
            address.setIsDefault(true);
        }

        // 保存新地址
        return addressRepository.save(address);
    }


    @Override
    public void deleteAddressById(Long addressId) {
        addressRepository.deleteById(addressId);
    }

//
//    @Override
//    public Address updateAddress(Address address) {
//        return addressRepository.save(address);
//    }
}
