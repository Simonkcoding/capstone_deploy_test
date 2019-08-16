package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.ProcessingFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProcessFeeDaoJdbcTemplateImpl implements ProcessFeeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProcessFeeDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProcessingFee addProcessFee(ProcessingFee fee) {
        String sql = "insert into processing_fee (product_type, fee)"
                + " values (?,?)";
        jdbcTemplate.update(
                sql,
                fee.getProductType(),
                fee.getFee()
        );

        return fee;
    }

    @Override
    public void initiateProcessFee() {
        String[] itemType = {
                "console",
                "tshirt",
                "game"
        };
        String[] fees = {
                "14.99",
                "1.98",
                "1.49"
        };

        String sql = "insert into processing_fee (product_type, fee)"
                + " values (?,?)";
        for (int i = 0; i < itemType.length; i++) {

            jdbcTemplate.update(
                    sql,
                    itemType[i],
                    fees[i]
            );
        }
    }

    private ProcessingFee MapTo(ResultSet rs, int rowNum)throws SQLException {
        ProcessingFee processFee = new ProcessingFee();
        processFee.setProductType(rs.getString("product_type"));
        processFee.setFee(rs.getBigDecimal("fee"));

        return processFee;
    }

    @Override
    public ProcessingFee getProcessFeeByItemType(String type) {

        try {
            String sql = "select* from processing_fee where product_type=?";
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
    public void deleteProcessFeeByItemType(String type) {
        String sql = "delete from processing_fee where product_type=?";
         jdbcTemplate.update(
                sql,
                type
        );
    }

    @Override
    public List<ProcessingFee> getAllProcessFee() {
        String sql = "select * from processing_fee ";
        return jdbcTemplate.query(
                sql,
                this::MapTo
        );
    }
}
