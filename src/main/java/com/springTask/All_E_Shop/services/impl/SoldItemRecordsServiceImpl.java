package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.SoldItemRecord;
import com.springTask.All_E_Shop.repositories.CountryRepository;
import com.springTask.All_E_Shop.repositories.SoldItemRecordsRepository;
import com.springTask.All_E_Shop.services.SoldItemRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoldItemRecordsServiceImpl implements SoldItemRecordsService {
    @Autowired
    private SoldItemRecordsRepository repository;

    @Override
    public SoldItemRecord addSoldItemRecord(SoldItemRecord soldItemRecord) {
        return repository.save(soldItemRecord);
    }

    @Override
    public List<SoldItemRecord> getAllSoldItemRecords() {
        return repository.findAll();
    }

    @Override
    public SoldItemRecord getSoldItemRecord(Long id) {
        return repository.getOne(id);
    }

    @Override
    public void deleteSoldItemRecord(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SoldItemRecord saveSoldItemRecord(SoldItemRecord soldItemRecord) {
        return repository.save(soldItemRecord);
    }
}
