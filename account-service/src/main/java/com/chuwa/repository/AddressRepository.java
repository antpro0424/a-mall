package com.chuwa.repository;

import com.chuwa.po.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByUserId(Long userId, Pageable pageable);
}
