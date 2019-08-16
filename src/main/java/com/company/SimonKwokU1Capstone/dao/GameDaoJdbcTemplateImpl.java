package com.company.SimonKwokU1Capstone.dao;

import com.company.SimonKwokU1Capstone.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoJdbcTemplateImpl implements GameDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game addGame(Game game) {
        String sql = "insert into game (title, esrb_rating, description, studio, price, quantity) "
                + "values (?,?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getStudio(),
                game.getPrice(),
                game.getQuantity()
        );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        game.setGameId(id);

        return game;
    }

    // helper method
    public Game MapTo(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setEsrbRating(rs.getString("esrb_rating"));
        game.setDescription(rs.getString("description"));
        game.setStudio(rs.getString("studio"));
        game.setPrice(rs.getBigDecimal("price"));
        game.setQuantity(rs.getInt("quantity"));

        return game;
    }

    @Override
    public Game getGameById(int id) {
        String sql = "select * from game where game_id=?";
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
    public List<Game> getAllGames() {
        String sql = "select * from game";
        return jdbcTemplate.query(
                sql,
                this::MapTo
        );
    }

    @Override
    public void updateGame(Game game) {
        String sql = "update game set title=?, esrb_rating=?, description=?, studio=?, price=?, quantity=? "
                + "where game_id=?";
        jdbcTemplate.update(
                sql,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getStudio(),
                game.getPrice(),
                game.getQuantity(),
                game.getGameId()
        );
    }

    @Override
    public void deleteGame(int id) {
        String sql = "delete from game where game_id=?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public List<Game> getGameByStudio(String studio) {
        String sql = "select * from game where studio = ?";
        return jdbcTemplate.query(
                sql,
                this::MapTo,
                studio
        );
    }

    @Override
    public List<Game> getGameByEsrbRating(String esrb) {
        String sql = "select * from game where esrb_rating=?";
        return jdbcTemplate.query(
                sql,
                this::MapTo,
                esrb
        );
    }

    @Override
    public List<Game> getGameByTitle(String title) {
        String sql = "select * from game where title = ?";
        return jdbcTemplate.query(
                sql,
                this::MapTo,
                title
        );
    }
}
