package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.ProcessingFee;

import java.math.BigDecimal;
import java.util.List;

public interface ProcessFeeDao {

    void initiateProcessFee();

    ProcessingFee addProcessFee(ProcessingFee processingFee);

    ProcessingFee getProcessFeeByItemType(String itemType);

    void deleteProcessFeeByItemType(String itemType);

    List<ProcessingFee> getAllProcessFee();

}
