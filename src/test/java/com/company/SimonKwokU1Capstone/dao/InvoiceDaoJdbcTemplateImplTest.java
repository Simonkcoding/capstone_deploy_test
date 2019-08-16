package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceDaoJdbcTemplateImplTest {
    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));

    }

    @Test
    public void testAddGetByIdGetAll() {

        Invoice invoice1 = new Invoice();
        invoice1.setName("Simon");
        invoice1.setStreet("Street1");
        invoice1.setCity("City1");
        invoice1.setState("S1");
        invoice1.setZipCode("12345");
        invoice1.setItemType("game");
        invoice1.setItemId(1);
        invoice1.setUnitPrice(new BigDecimal("40.99"));
        invoice1.setQuantity(1);
        invoice1.setSubtotal(new BigDecimal("40.99"));
        invoice1.setTax(new BigDecimal("2.99"));
        invoice1.setProcessFee(new BigDecimal("2.99"));
        invoice1.setTotal(new BigDecimal("50.1").setScale(2));

        //test add
        invoice1 = invoiceDao.addInvoice(invoice1);

        //test get by id
        Invoice invoice2 = invoiceDao.getInvoiceById(invoice1.getInvoiceId());
        assertEquals(invoice2, invoice1);

        //test get all
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        assertEquals(invoices.size(), 1);

    }

    @Test
    public void testUpdateDelete() {
        Invoice invoice1 = new Invoice();
        invoice1.setName("Simon");
        invoice1.setStreet("Street1");
        invoice1.setCity("City1");
        invoice1.setState("S1");
        invoice1.setZipCode("12345");
        invoice1.setItemType("game");
        invoice1.setItemId(1);
        invoice1.setUnitPrice(new BigDecimal("40.99"));
        invoice1.setQuantity(1);
        invoice1.setSubtotal(new BigDecimal("40.99"));
        invoice1.setTax(new BigDecimal("2.99"));
        invoice1.setProcessFee(new BigDecimal("2.99"));
        invoice1.setTotal(new BigDecimal("50.1").setScale(2));

        //test add
        invoice1 = invoiceDao.addInvoice(invoice1);

        //test update
        invoice1.setName("NotSimon");
        invoice1.setStreet("NotStreet1");
        invoice1.setCity("NotCity1");
        invoice1.setState("S2");
        invoice1.setZipCode("54321");
        invoice1.setItemType("console");
        invoice1.setItemId(1);
        invoice1.setUnitPrice(new BigDecimal("10.99"));
        invoice1.setQuantity(1);
        invoice1.setSubtotal(new BigDecimal("10.99"));
        invoice1.setTax(new BigDecimal("22.99"));
        invoice1.setProcessFee(new BigDecimal("22.99"));
        invoice1.setTotal(new BigDecimal("250.10"));
        invoiceDao.updateInvoice(invoice1);
        Invoice invoice2 = invoiceDao.getInvoiceById(invoice1.getInvoiceId());
        assertEquals(invoice1, invoice2);

        //test delete
        invoiceDao.deleteInvoice(invoice1.getInvoiceId());
        assertNull(invoiceDao.getInvoiceById(invoice1.getInvoiceId()));

    }

//    @Test
//    public void testGetInvoiceByColorAndSize() {
//        Invoice invoice1 = new Invoice();
//        invoice1.setName("Simon");
//        invoice1.setStreet("Street1");
//        invoice1.setCity("City1");
//        invoice1.setState("S1");
//        invoice1.setZipCode("12345");
//        invoice1.setItemType("game");
//        invoice1.setItemId(1);
//        invoice1.setUnitPrice(new BigDecimal("40.99"));
//        invoice1.setQuantity(1);
//        invoice1.setSubtotal(new BigDecimal("40.99"));
//        invoice1.setTax(new BigDecimal("2.99"));
//        invoice1.setProcessFee(new BigDecimal("2.99"));
//        invoice1.setTotal(new BigDecimal("50.1"));
//        invoice1 = invoiceDao.addInvoice(invoice1);
//
//        //by color 1
//        List<Invoice> redInvoice = invoiceDao.getInvoiceByColor("red");
//        assertEquals(redInvoice.size(), 1);
//
//        //by color 2
//        List<Invoice> blueInvoice = invoiceDao.getInvoiceByColor("blue");
//        assertEquals(blueInvoice.size(), 0);
//
//        //by size 1
//        List<Invoice> sizeMInvoice = invoiceDao.getInvoiceBySize("M");
//        assertEquals(sizeMInvoice.size(), 0);
//
//        //by size 2
//        List<Invoice> sizeXLInvoice = invoiceDao.getInvoiceBySize("XL");
//        assertEquals(sizeXLInvoice.size(), 1);
//    }

}