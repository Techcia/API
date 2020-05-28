package com.techcia.services;

import com.techcia.constants.SaleConstants;
import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.models.Sale;
import com.techcia.repositories.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
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

    public List<Sale> findByClient(Client client){
        return saleRepository.findByClient(client);
    }

    public Sale generatePay(Sale sale){
        sale.setDataPay(new Date());
        sale.setValue(this.calculateValue(sale));
        return this.save(sale);
    }

    public Sale pay(Sale sale){
        sale.setStatus(SaleConstants.PAGO);
        return this.save(sale);
    }

    public Sale checkout(Sale sale){
        sale.setCheckout(new Date());
        sale.setStatus(SaleConstants.FECHADO);
        return this.save(sale);
    }

    public Double calculateValue(Sale sale){
        DecimalFormat decimalFormat = new DecimalFormat("0");
        Calendar initialDate = Calendar.getInstance();
        initialDate.setTime(sale.getCheckin());
        Calendar finalDate = Calendar.getInstance();
        finalDate.setTime(sale.getDataPay());
        long difference = finalDate.getTimeInMillis() - initialDate.getTimeInMillis();
        long differenceInHours = difference / (60 * 60 * 1000);
        double differ = Double.parseDouble(decimalFormat.format(differenceInHours));
        Double value = (differ + 1) * sale.getParking().getValuePerHour();
        return value;
    }

    public List<Sale> findByCompanyByDate(Company company,Date initialDate, Date finalDate){
        return saleRepository.findByCompanyByDate(company.getId(),initialDate, finalDate);
    }
}
