package com.techcia.services;

import com.techcia.models.Admin;
import com.techcia.models.Client;
import com.techcia.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service

@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public Admin save(Admin admin){
        return adminRepository.save(admin);
    }
}
