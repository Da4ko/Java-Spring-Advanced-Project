package com.example.java_spring_advanced_project.repository;

import com.example.java_spring_advanced_project.model.entity.Currency;
import com.example.java_spring_advanced_project.model.entity.enums.CurrencyName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    Optional <Currency> findByCurrency(CurrencyName currencyName);
}
