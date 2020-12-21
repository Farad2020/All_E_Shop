package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.SoldItemRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SoldItemRecordsService  {
    SoldItemRecord addSoldItemRecord(SoldItemRecord soldItemRecord);
    List<SoldItemRecord> getAllSoldItemRecords();
    SoldItemRecord getSoldItemRecord(Long id);
    void deleteSoldItemRecord(Long id);
    SoldItemRecord saveSoldItemRecord(SoldItemRecord soldItemRecord);


}
