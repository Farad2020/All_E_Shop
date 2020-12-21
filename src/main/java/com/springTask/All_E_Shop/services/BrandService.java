package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.Brand;
import com.springTask.All_E_Shop.entities.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {


    Brand addBrand(Brand brand);
    List<Brand> getAllBrands();
    Brand getBrand(Long id);
    void deleteBrand(Long id);
    Brand saveBrand(Brand brand);

    List<Country> getAllCountries();
    Country addCountry(Country country);
    Country saveCountry(Country country);
    Country getCountry(Long id);

}
