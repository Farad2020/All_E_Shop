package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.Brand;
import com.springTask.All_E_Shop.entities.Country;
import com.springTask.All_E_Shop.repositories.BrandRepository;
import com.springTask.All_E_Shop.repositories.CountryRepository;
import com.springTask.All_E_Shop.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrand(Long id) {
        if(brandRepository.existsById(id))
            return brandRepository.getOne(id);
        else
            return null;
    }

    @Override
    public void deleteBrand(Long id) {
        if(brandRepository.existsById(id))
            brandRepository.deleteById(id);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository.getOne(id);
    }
}
