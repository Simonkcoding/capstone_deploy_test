package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.SaleTaxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SaleTaxRateDaoJdbcTemplateImpl implements SaleTaxRateDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SaleTaxRateDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SaleTaxRate addSaleTaxRate(SaleTaxRate taxRate) {
        String sql = "insert into sales_tax_rate (state, rate)"
                + " values (?,?)";
        jdbcTemplate.update(
                sql,
                taxRate.getState(),
                taxRate.getRate()
        );

        return taxRate;
    }

    @Override
    public void initiateSaleTaxRate() {

        Map<String, BigDecimal>  rateMap = new HashMap<>();
        rateMap.put("AL", new BigDecimal(".05").setScale(2));
        rateMap.put("AK", new BigDecimal(".06").setScale(2));
        rateMap.put("AZ", new BigDecimal(".04").setScale(2));
        rateMap.put("AR", new BigDecimal(".06").setScale(2));
        rateMap.put("CA", new BigDecimal(".06").setScale(2));
        rateMap.put("CO", new BigDecimal(".04").setScale(2));
        rateMap.put("CT", new BigDecimal(".03").setScale(2));
        rateMap.put("DE", new BigDecimal(".05").setScale(2));
        rateMap.put("FL", new BigDecimal(".06").setScale(2));
        rateMap.put("GA", new BigDecimal(".07").setScale(2));
        rateMap.put("HI", new BigDecimal(".05").setScale(2));
        rateMap.put("ID", new BigDecimal(".03").setScale(2));
        rateMap.put("IL", new BigDecimal(".05").setScale(2));
        rateMap.put("IN", new BigDecimal(".05").setScale(2));
        rateMap.put("IA", new BigDecimal(".04").setScale(2));
        rateMap.put("KS", new BigDecimal(".06").setScale(2));
        rateMap.put("KY", new BigDecimal(".04").setScale(2));
        rateMap.put("LA", new BigDecimal(".05").setScale(2));
        rateMap.put("ME", new BigDecimal(".03").setScale(2));
        rateMap.put("MD", new BigDecimal(".07").setScale(2));
        rateMap.put("MA", new BigDecimal(".05").setScale(2));
        rateMap.put("MI", new BigDecimal(".06").setScale(2));
        rateMap.put("MN", new BigDecimal(".06").setScale(2));
        rateMap.put("MS", new BigDecimal(".05").setScale(2));
        rateMap.put("MO", new BigDecimal(".05").setScale(2));
        rateMap.put("MT", new BigDecimal(".03").setScale(2));
        rateMap.put("NE", new BigDecimal(".04").setScale(2));
        rateMap.put("NV", new BigDecimal(".04").setScale(2));
        rateMap.put("NH", new BigDecimal(".06").setScale(2));
        rateMap.put("NJ", new BigDecimal(".05").setScale(2));
        rateMap.put("NM", new BigDecimal(".05").setScale(2));
        rateMap.put("NY", new BigDecimal(".06").setScale(2));
        rateMap.put("NC", new BigDecimal(".05").setScale(2));
        rateMap.put("ND", new BigDecimal(".05").setScale(2));
        rateMap.put("OH", new BigDecimal(".04").setScale(2));
        rateMap.put("OK", new BigDecimal(".04").setScale(2));
        rateMap.put("OR", new BigDecimal(".07").setScale(2));
        rateMap.put("PA", new BigDecimal(".06").setScale(2));
        rateMap.put("RI", new BigDecimal(".06").setScale(2));
        rateMap.put("SC", new BigDecimal(".06").setScale(2));
        rateMap.put("SD", new BigDecimal(".06").setScale(2));
        rateMap.put("TN", new BigDecimal(".05").setScale(2));
        rateMap.put("TX", new BigDecimal(".03").setScale(2));
        rateMap.put("UT", new BigDecimal(".04").setScale(2));
        rateMap.put("VT", new BigDecimal(".07").setScale(2));
        rateMap.put("VA", new BigDecimal(".06").setScale(2));
        rateMap.put("WA", new BigDecimal(".05").setScale(2));
        rateMap.put("WV", new BigDecimal(".05").setScale(2));
        rateMap.put("WI", new BigDecimal(".03").setScale(2));
        rateMap.put("WY", new BigDecimal(".04").setScale(2));

        String sql = "insert into sales_tax_rate (state, rate)"
                + " values (?,?)";
        for (Map.Entry<String,BigDecimal> entry:rateMap.entrySet()) {

            jdbcTemplate.update(
                    sql,
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }

    private SaleTaxRate MapTo(ResultSet rs, int rowNum)throws SQLException {
        SaleTaxRate processFee = new SaleTaxRate();
        processFee.setState(rs.getString("state"));
        processFee.setRate(rs.getBigDecimal("rate"));

        return processFee;
    }

    @Override
    public SaleTaxRate getSaleTaxRateByState(String type) {

        try {
            String sql = "select* from sales_tax_rate where state=?";
            return jdbcTemplate.queryForObject(
                    sql,
                    this::MapTo,
                    type
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void deleteSaleTaxRateByState(String type) {
        String sql = "delete from sales_tax_rate where state=?";
        jdbcTemplate.update(
                sql,
                type
        );
    }

    @Override
    public List<SaleTaxRate> getAllSaleTaxRate() {
        String sql = "select * from sales_tax_rate ";
        return jdbcTemplate.query(
                sql,
                this::MapTo
        );
    }
}
