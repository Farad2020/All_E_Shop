package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.Country;
import com.springTask.All_E_Shop.entities.ShopItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {
    Country addCountry(Country country);
    List<Country> getAllCountries();
    Country getCountry(Long id);
    void deleteCountry(Long id);
    Country saveCountry(Country country);

}
