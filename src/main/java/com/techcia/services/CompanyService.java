package com.techcia.services;

import com.techcia.models.Company;
import com.techcia.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public Company save(Company company){
        return companyRepository.save(company);
    }

    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public Optional<Company> findById(Long id){
        return companyRepository.findById(id);
    }

    public void deleteById(Long id){
        companyRepository.deleteById(id);
    }
}
