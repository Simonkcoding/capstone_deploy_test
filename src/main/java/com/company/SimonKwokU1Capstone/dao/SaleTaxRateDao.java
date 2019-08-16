package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.SaleTaxRate;

import java.util.List;

public interface SaleTaxRateDao {

    void initiateSaleTaxRate();

    SaleTaxRate addSaleTaxRate(SaleTaxRate taxRate);

    SaleTaxRate getSaleTaxRateByState(String state);

    void deleteSaleTaxRateByState(String state);

    List<SaleTaxRate> getAllSaleTaxRate();
}
