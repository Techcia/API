package com.techcia.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
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
    @Column(unique=true, nullable=false)
    private String email;
    @Column(unique=true, nullable=false)
    private String password;
    @CreationTimestamp
    @Column(nullable=false, updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(nullable=false)
    private Date updatedAt;

}
