package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsoleDaoJdbcTemplateImpl implements ConsoleDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ConsoleDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Console addConsole(Console console) {
        String sql="insert into console (model, manufacturer, memory_amount, processor, price, quantity) "
                + "values (?,?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity()
        );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        console.setConsoleId(id);

        return console;
    }

    // helper method
    public Console MapTo(ResultSet rs, int rowNum)throws SQLException {
        Console console = new Console();
        console.setConsoleId(rs.getInt("console_id"));
        console.setModel(rs.getString("model"));
        console.setManufacturer(rs.getString("manufacturer"));
        console.setMemoryAmount(rs.getString("memory_amount"));
        console.setProcessor(rs.getString("processor"));
        console.setPrice(rs.getBigDecimal("price"));
        console.setQuantity(rs.getInt("quantity"));

        return console;
    }

    @Override
    public Console getConsoleById(int id) {
        String sql = "select * from console where console_id=?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    this::MapTo,
                    id
            );
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Console> getAllConsoles() {
        String sql = "select * from console";
        return jdbcTemplate.query(
                sql,
                this::MapTo
        );
    }

    @Override
    public void updateConsole(Console console) {
        String sql = "update console set model=?, manufacturer=?, memory_amount=?, processor=?, price=?, quantity=? "
                +"where console_id=?";
        jdbcTemplate.update(
                sql,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity(),
                console.getConsoleId()
        );
    }

    @Override
    public void deleteConsole(int id) {
        String sql = "delete from console where console_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public List<Console> getConsoleByMfg(String mfg) {
        String sql = "select * from console where manufacturer = ?";
        return jdbcTemplate.query(
                sql,
                this::MapTo,
                mfg
        );
    }
}
