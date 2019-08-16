package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Game;

import java.util.List;

public interface GameDao {

    Game addGame(Game game);

    Game getGameById(int id);

    List<Game> getAllGames();

    void updateGame(Game game);

    void deleteGame(int id);

    List<Game> getGameByStudio(String studio);

    List<Game> getGameByEsrbRating(String esrb);

    List<Game> getGameByTitle(String title);
}
