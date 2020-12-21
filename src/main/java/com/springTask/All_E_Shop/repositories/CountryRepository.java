package com.springTask.All_E_Shop.repositories;

import com.springTask.All_E_Shop.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CountryRepository  extends JpaRepository<Country, Long> {

}
