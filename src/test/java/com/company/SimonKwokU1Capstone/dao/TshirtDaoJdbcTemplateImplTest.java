package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Tshirt;
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
public class TshirtDaoJdbcTemplateImplTest {

    @Autowired
    TshirtDao tshirtDao;

    @Before
    public void setUp() throws Exception {
        List<Tshirt> tshirts = tshirtDao.getAllTshirts();
        tshirts.stream().forEach(tshirt -> tshirtDao.deleteTshirt(tshirt.getTshirtId()));
    }

    @Test
    public void testAddGetByIdGetAll() {

        Tshirt tshirt1 = new Tshirt();
        tshirt1.setColor("red");
        tshirt1.setSize("XL");
        tshirt1.setDescription("avengers");
        tshirt1.setPrice(new BigDecimal("10.99"));
        tshirt1.setQuantity(10);

        //test add
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        //test get by id
        Tshirt tshirt2 = tshirtDao.getTshirtById(tshirt1.getTshirtId());
        assertEquals(tshirt2, tshirt1);

        //test get all
        List<Tshirt> tshirts = tshirtDao.getAllTshirts();
        assertEquals(tshirts.size(), 1);

    }

    @Test
    public void testUpdateDelete() {
        Tshirt tshirt1 = new Tshirt();
        tshirt1.setColor("red");
        tshirt1.setSize("XL");
        tshirt1.setDescription("avengers");
        tshirt1.setPrice(new BigDecimal("10.99"));
        tshirt1.setQuantity(10);
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        //test update
        tshirt1.setColor("blue");
        tshirt1.setSize("M");
        tshirt1.setDescription("Toy Story");
        tshirt1.setPrice(new BigDecimal("5.99"));
        tshirt1.setQuantity(99);
        tshirtDao.updateTshirt(tshirt1);
        Tshirt tshirt2 = tshirtDao.getTshirtById(tshirt1.getTshirtId());
        assertEquals(tshirt1, tshirt2);

        //test delete
        tshirtDao.deleteTshirt(tshirt1.getTshirtId());
        assertNull(tshirtDao.getTshirtById(tshirt1.getTshirtId()));

    }

    @Test
    public void testGetTshirtByColorAndSize() {
        Tshirt tshirt1 = new Tshirt();
        tshirt1.setColor("red");
        tshirt1.setSize("XL");
        tshirt1.setDescription("avengers");
        tshirt1.setPrice(new BigDecimal("10.99"));
        tshirt1.setQuantity(10);
        tshirt1 = tshirtDao.addTshirt(tshirt1);

        //by color 1
        List<Tshirt> redTshirt = tshirtDao.getTshirtByColor("red");
        assertEquals(redTshirt.size(), 1);

        //by color 2
        List<Tshirt> blueTshirt = tshirtDao.getTshirtByColor("blue");
        assertEquals(blueTshirt.size(), 0);

        //by size 1
        List<Tshirt> sizeMTshirt = tshirtDao.getTshirtBySize("M");
        assertEquals(sizeMTshirt.size(), 0);

        //by size 2
        List<Tshirt> sizeXLTshirt = tshirtDao.getTshirtBySize("XL");
        assertEquals(sizeXLTshirt.size(), 1);
    }
}