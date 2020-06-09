package com.techcia.services;

import com.techcia.constants.SaleConstants;
import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.models.Parking;
import com.techcia.models.Sale;
import com.techcia.repositories.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.*;

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
        return saleRepository.findByClientOrderByIdDesc(client);
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

    private Double calculateValue(Sale sale){
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

    public List<Sale> findByParkingsByDate(List<Integer> parkings , Date initialDate, Date finalDate){
        String parkingsString = ParkingListToString(parkings);
        return saleRepository.findByParkingsByDate(parkingsString,initialDate, finalDate);
    }

    public Map<String, Object> countAndSumValueSales(List<Integer> parkings, Date initialDate, Date finalDate){
        String parkingsString = ParkingListToString(parkings);
        return saleRepository.countAndSumValueSales(parkingsString, initialDate, finalDate);
    }

    private String ParkingListToString(List<Integer> parkings){
        String parkingsString = parkings.toString();
        parkingsString = parkingsString.substring(1, parkingsString.length()-1);
        return parkingsString;
    }

    public Page<Sale> findByDateByCompany(Company company, Date initialDate, Date finalDate, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return saleRepository.findByDateByCompany(company.getId(), initialDate, finalDate, pageRequest);
    }
}
