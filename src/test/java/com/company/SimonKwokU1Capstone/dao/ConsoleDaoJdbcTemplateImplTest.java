package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Console;
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
public class ConsoleDaoJdbcTemplateImplTest {

    @Autowired
    ConsoleDao consoleDao;

    @Before
    public void setUp() throws Exception {
        List<Console> consoles = consoleDao.getAllConsoles();
        consoles.stream().forEach(console -> consoleDao.deleteConsole(console.getConsoleId()));
    }

    @Test
    public void testAddGetByIdGetAll() {

        Console console1 = new Console();
        console1.setModel("PS4");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("124MB");
        console1.setProcessor("Intel4");
        console1.setPrice(new BigDecimal("100.1").setScale(2));
        console1.setQuantity(99);

        //test add
        console1 = consoleDao.addConsole(console1);

        //test get by id
        Console console2 = consoleDao.getConsoleById(console1.getConsoleId());
        assertEquals(console2, console1);

        //test get all
        List<Console> consoles = consoleDao.getAllConsoles();
        assertEquals(consoles.size(), 1);

    }

    @Test
    public void testUpdateDelete() {

        Console console1 = new Console();
        console1.setModel("PS4");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("124MB");
        console1.setProcessor("Intel4");
        console1.setPrice(new BigDecimal("100.1").setScale(2));
        console1.setQuantity(99);
        console1 = consoleDao.addConsole(console1);

        //test update
        console1.setModel("Switch");
        console1.setManufacturer("nintendo");
        console1.setMemoryAmount("15MB");
        console1.setProcessor("Intel1");
        console1.setPrice(new BigDecimal("50.9").setScale(2));
        console1.setQuantity(9);
        consoleDao.updateConsole(console1);
        Console console2 = consoleDao.getConsoleById(console1.getConsoleId());
        assertEquals(console1, console2);

        //test delete
        consoleDao.deleteConsole(console1.getConsoleId());
        assertNull(consoleDao.getConsoleById(console1.getConsoleId()));

    }

    @Test
    public void testGetConsoleByMfg() {
        Console console1 = new Console();
        console1.setModel("PS4");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("124MB");
        console1.setProcessor("Intel4");
        console1.setPrice(new BigDecimal("100.1").setScale(2));
        console1.setQuantity(99);
        console1 = consoleDao.addConsole(console1);

        //by Mfg 1
        List<Console> tecmoConsole = consoleDao.getConsoleByMfg("Sony");
        assertEquals(tecmoConsole.size(), 1);

        //by Mfg 2
        List<Console> officeCreateConsole = consoleDao.getConsoleByMfg("nintendo");
        assertEquals(officeCreateConsole.size(), 0);

    }


}