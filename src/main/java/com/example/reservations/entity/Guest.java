package com.example.reservations.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="GUEST")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="GUEST_ID")
    private Long id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="STATE")
    private String state;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;
}
