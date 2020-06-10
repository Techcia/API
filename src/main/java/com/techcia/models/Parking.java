package com.techcia.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private int number;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String neighborhood;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private int numberOfVacancies;
    @Column(nullable = false)
    private int occupiedPlaces;
    @Column(nullable = false)
    private double valuePerHour;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createTime;
    @UpdateTimestamp
    @Column(nullable = false)
    private Date updateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="company_id",referencedColumnName="id",nullable=false)
    Company company;

}
