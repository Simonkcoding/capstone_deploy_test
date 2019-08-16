package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GameDaoJdbcTemplateImplTest {


    @Autowired
    GameDao gameDao;

    @Before
    public void setUp() throws Exception {
        List<Game> games = gameDao.getAllGames();
        games.stream().forEach(game -> gameDao.deleteGame(game.getGameId()));
    }

    @Test
    public void testAddGetByIdGetAll() {

        Game game1 = new Game();
        game1.setTitle("Dead or Alive Volley Ball");
        game1.setEsrbRating("E");
        game1.setDescription("Good for Boys");
        game1.setStudio("Tecmo");
        game1.setPrice(new BigDecimal("10.99"));
        game1.setQuantity(12);

        //test add
        game1 = gameDao.addGame(game1);

        //test get by id
        Game game2 = gameDao.getGameById(game1.getGameId());
        assertEquals(game2, game1);

        //test get all
        List<Game> games = gameDao.getAllGames();
        assertEquals(games.size(), 1);

    }

    @Test
    public void testUpdateDelete() {

        Game game1 = new Game();
        game1.setTitle("Dead or Alive Volley Ball");
        game1.setEsrbRating("E");
        game1.setDescription("Good for Boys");
        game1.setStudio("Tecmo");
        game1.setPrice(new BigDecimal("10.99"));
        game1.setQuantity(12);
        game1 = gameDao.addGame(game1);

        //test update
        game1.setTitle("Cooking Mama");
        game1.setEsrbRating("R");
        game1.setDescription("Not suitable for girls");
        game1.setStudio("Office Create");
        game1.setPrice(new BigDecimal("110.99"));
        game1.setQuantity(99);
        gameDao.updateGame(game1);
        Game game2 = gameDao.getGameById(game1.getGameId());
        assertEquals(game1, game2);

        //test delete
        gameDao.deleteGame(game1.getGameId());
        assertNull(gameDao.getGameById(game1.getGameId()));

    }

    @Test
    public void testGetGameByStudioAndEsrbRatingAndTitle() {
        Game game1 = new Game();
        game1.setTitle("Dead or Alive Volley Ball");
        game1.setEsrbRating("E");
        game1.setDescription("Good for Boys");
        game1.setStudio("Tecmo");
        game1.setPrice(new BigDecimal("10.99"));
        game1.setQuantity(12);
        game1 = gameDao.addGame(game1);

        //by Studio 1
        List<Game> tecmoGame = gameDao.getGameByStudio("Tecmo");
        assertEquals(tecmoGame.size(), 1);

        //by Studio 2
        List<Game> officeCreateGame = gameDao.getGameByStudio("Office create");
        assertEquals(officeCreateGame.size(), 0);

        //by EsrbRating 1
        List<Game> rateRGame = gameDao.getGameByEsrbRating("R");
        assertEquals(rateRGame.size(), 0);

        //by EsrbRating 2
        List<Game> rateEGame = gameDao.getGameByEsrbRating("E");
        assertEquals(rateEGame.size(), 1);

        //by Title 1
        List<Game> goodGame = gameDao.getGameByTitle("Dead or Alive Volley Ball");
        assertEquals(goodGame.size(), 1);

        //by Title 2
        List<Game> badGame = gameDao.getGameByTitle("Cooking Mama");
        assertEquals(badGame.size(), 0);
    }

}