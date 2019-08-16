package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {

        String sql = "insert into invoice (" +
                "name," +
                "street," +
                "city," +
                "state," +
                "zipcode," +
                "item_type," +
                "item_id," +
                "unit_price," +
                "quantity," +
                "subtotal," +
                "tax," +
                "processing_fee," +
                "total) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipCode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessFee(),
                invoice.getTotal()
        );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;
    }

    // helper method
    public Invoice MapTo(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setName(rs.getString("name"));
        invoice.setStreet(rs.getString("street"));
        invoice.setCity(rs.getString("city"));
        invoice.setState(rs.getString("state"));
        invoice.setZipCode(rs.getString("zipcode"));
        invoice.setItemType(rs.getString("item_type"));
        invoice.setItemId(rs.getInt("item_id"));
        invoice.setUnitPrice(rs.getBigDecimal("unit_price"));
        invoice.setQuantity(rs.getInt("quantity"));
        invoice.setSubtotal(rs.getBigDecimal("subtotal"));
        invoice.setTax(rs.getBigDecimal("tax"));
        invoice.setProcessFee(rs.getBigDecimal("processing_fee"));
        invoice.setTotal(rs.getBigDecimal("total"));

        return invoice;
    }

    @Override
    public Invoice getInvoiceById(int id) {
        String sql = "select * from invoice where invoice_id=?";
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
    public List<Invoice> getAllInvoices() {
        String sql = "select * from invoice";
        return jdbcTemplate.query(
                sql,
                this::MapTo
        );
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        String sql = "update invoice set "+
                "name=?," +
                "street=?," +
                "city=?," +
                "state=?," +
                "zipcode=?," +
                "item_type=?," +
                "item_id=?," +
                "unit_price=?," +
                "quantity=?," +
                "subtotal=?," +
                "tax=?," +
                "processing_fee=?," +
                "total=? "
                + "where invoice_id=?";
        jdbcTemplate.update(
                sql,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipCode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessFee(),
                invoice.getTotal(),
                invoice.getInvoiceId()
        );
    }

    @Override
    public void deleteInvoice(int id) {
        String sql = "delete from invoice where invoice_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }
}
