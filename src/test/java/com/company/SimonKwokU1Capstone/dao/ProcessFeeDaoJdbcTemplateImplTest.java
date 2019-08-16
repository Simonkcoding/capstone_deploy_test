package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.ProcessingFee;
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
public class ProcessFeeDaoJdbcTemplateImplTest {

    @Autowired
    ProcessFeeDao processFeeDao;

    @Before
    public void setUp() throws Exception {
        List<ProcessingFee> pfList = processFeeDao.getAllProcessFee();
        //test delete
        pfList.stream().forEach(processingFee -> processFeeDao.deleteProcessFeeByItemType(processingFee.getProductType()));
    }

    @Test
    public void testAddAndGetProcessFeeByItemType() {
        ProcessingFee pf = new ProcessingFee();
        pf.setFee(new BigDecimal("1.49"));
        pf.setProductType("game");
        pf = processFeeDao.addProcessFee(pf);

        //test get by product type
//        ProcessingFee pf1 = processFeeDao.getProcessFeeByItemType("console");
        ProcessingFee pf2 = processFeeDao.getProcessFeeByItemType("game");
//        ProcessingFee pf3 = processFeeDao.getProcessFeeByItemType("tshirt");
//        assertEquals(pf1.getFee(), new BigDecimal("14.99"));
        assertEquals(pf2.getFee(), pf.getFee());
//        assertEquals(pf3.getFee(), new BigDecimal("1.98"));
    }

    @Test
    public void testInitiateAndGetAllProcessFee() {
        //test add
        processFeeDao.initiateProcessFee();

        //test get all
        List<ProcessingFee> pfList = processFeeDao.getAllProcessFee();
        assertEquals(pfList.size(), 3);
    }


}