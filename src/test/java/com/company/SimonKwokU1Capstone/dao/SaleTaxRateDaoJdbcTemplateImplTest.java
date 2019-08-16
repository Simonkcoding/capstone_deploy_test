package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.SaleTaxRate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SaleTaxRateDaoJdbcTemplateImplTest {

    @Autowired
    SaleTaxRateDao saleTaxRateDao;

    @Before
    public void setUp() throws Exception {
        List<SaleTaxRate> taxList = saleTaxRateDao.getAllSaleTaxRate();
        taxList.stream().forEach(saleTaxRate -> saleTaxRateDao.deleteSaleTaxRateByState(saleTaxRate.getState()));
    }

    @Test
    public void testAddAndGetByState(){
            SaleTaxRate taxRate = new SaleTaxRate();
            taxRate.setRate(new BigDecimal("0.05"));
            taxRate.setState("NC");
            taxRate = saleTaxRateDao.addSaleTaxRate(taxRate);

            SaleTaxRate taxRate2 = saleTaxRateDao.getSaleTaxRateByState("NC");
            assertEquals(taxRate.getRate(),taxRate2.getRate());
    }

    @Test
    public void testInitiateAndGetAllTaxRate(){
        saleTaxRateDao.initiateSaleTaxRate();
        List<SaleTaxRate> taxRates = saleTaxRateDao.getAllSaleTaxRate();
        assertEquals(taxRates.size(),50);
    }
}