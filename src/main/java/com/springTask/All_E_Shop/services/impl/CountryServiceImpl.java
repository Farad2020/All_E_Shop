package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.Country;
import com.springTask.All_E_Shop.entities.ShopItem;
import com.springTask.All_E_Shop.repositories.CountryRepository;
import com.springTask.All_E_Shop.repositories.ShopItemRepository;
import com.springTask.All_E_Shop.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountry(Long id) {
        if(countryRepository.existsById(id))
            return countryRepository.getOne(id);
        else
            return null;
    }

    @Override
    public void deleteCountry(Long id) {
        if(countryRepository.existsById(id))
            countryRepository.deleteById(id);
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }
}
