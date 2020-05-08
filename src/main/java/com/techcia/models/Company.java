package com.techcia.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@DynamicUpdate
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String document;
    @Column(unique=true, nullable=false)
    private String email;
    @Column(unique=true, nullable=false)
    private String password;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String tradeName;
    @CreationTimestamp
    @Column(nullable=false, updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(nullable=false)
    private Date updatedAt;

}
