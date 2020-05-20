package com.techcia.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Data //cria getters e setters, hashcode etc
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private Double value;
    @Column(nullable=false)
    private String status;
    @CreationTimestamp
    @Column(nullable=false, updatable = false)
    private Date checkin;
    @Column()
    private Date dataPay;
    @Column()
    private Date checkout;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="parking_id",referencedColumnName="id",nullable=false)
    Parking parking;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="client_id",referencedColumnName="id",nullable=false)
    Client client;
}
