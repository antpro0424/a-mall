package com.chuwa.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "zipcode", length = 10)
    private String zipcode;

    @Column(name = "mobile", length = 15)
    private String mobile;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "notes")
    private String notes;

}

