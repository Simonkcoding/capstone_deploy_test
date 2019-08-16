package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Console;

import java.util.List;

public interface ConsoleDao {

    Console addConsole(Console console);

    Console getConsoleById(int id);

    List<Console> getAllConsoles();

    void updateConsole(Console console);

    void deleteConsole(int id);

    List<Console> getConsoleByMfg(String mfg);

}
