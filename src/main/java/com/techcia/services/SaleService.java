package com.techcia.services;

import com.techcia.models.Sale;
import com.techcia.repositories.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor    //cria um construtor com parâmetros
public class SaleService {

    private final SaleRepository saleRepository;

    @Transactional
    public Sale save(Sale sale) {return saleRepository.save(sale); }

    public List<Sale> findAll() {return saleRepository.findAll();}

    public Optional<Sale> findById(Long id) {return saleRepository.findById(id);}

    public void deleteById(Long id) {saleRepository.deleteById(id);}
}
