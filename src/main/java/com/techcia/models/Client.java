package com.techcia.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity

@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String document;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String typeDocument;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    @CreationTimestamp
    @Column(nullable=false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(nullable=false)
    private Date updatedAt;

}
