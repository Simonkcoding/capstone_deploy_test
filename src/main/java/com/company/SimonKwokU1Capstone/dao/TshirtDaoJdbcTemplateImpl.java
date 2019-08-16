package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Tshirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TshirtDaoJdbcTemplateImpl implements TshirtDao{


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TshirtDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tshirt addTshirt(Tshirt tshirt) {
        String sql = "insert into t_shirt (size, color, description, price, quantity) "
                + "values (?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity()
        );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        tshirt.setTshirtId(id);

        return tshirt;
    }

    // helper method
    public Tshirt MapTo(ResultSet rs, int rowNum) throws SQLException {
        Tshirt tshirt = new Tshirt();
        tshirt.setTshirtId(rs.getInt("t_shirt_id"));
        tshirt.setSize(rs.getString("size"));
        tshirt.setColor(rs.getString("color"));
        tshirt.setDescription(rs.getString("description"));
        tshirt.setPrice(rs.getBigDecimal("price"));
        tshirt.setQuantity(rs.getInt("quantity"));

        return tshirt;
    }

    @Override
    public Tshirt getTshirtById(int id) {
        String sql = "select * from t_shirt where t_shirt_id=?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    this::MapTo,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Tshirt> getAllTshirts() {
        String sql = "select * from t_shirt";
        return jdbcTemplate.query(
                sql,
                this::MapTo
        );
    }

    @Override
    public void updateTshirt(Tshirt tshirt) {
        String sql = "update t_shirt set size=?, color=?, description=?, price=?, quantity=? "
                + "where t_shirt_id=?";
        jdbcTemplate.update(
                sql,
                tshirt.getSize(),
                tshirt.getColor(),
                tshirt.getDescription(),
                tshirt.getPrice(),
                tshirt.getQuantity(),
                tshirt.getTshirtId()
        );
    }

    @Override
    public void deleteTshirt(int id) {
        String sql = "delete from t_shirt where t_shirt_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public List<Tshirt> getTshirtByColor(String color) {
        String sql = "select * from t_shirt where color = ?";
        return jdbcTemplate.query(
                sql,
                this::MapTo,
                color
        );
    }

    @Override
    public List<Tshirt> getTshirtBySize(String size) {
        String sql = "select * from t_shirt where size = ?";
        return jdbcTemplate.query(
                sql,
                this::MapTo,
                size
        );
    }

}
