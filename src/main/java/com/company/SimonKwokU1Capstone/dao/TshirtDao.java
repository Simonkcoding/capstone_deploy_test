package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Tshirt;

import java.util.List;

public interface TshirtDao {

    Tshirt addTshirt(Tshirt tshirt);

    Tshirt getTshirtById(int id);

    List<Tshirt> getAllTshirts();

    void updateTshirt(Tshirt tshirt);

    void deleteTshirt(int id);

    List<Tshirt> getTshirtByColor(String color);

    List<Tshirt> getTshirtBySize(String size);


}
