package com.company.SimonKwokU1Capstone.controller;

import com.company.SimonKwokU1Capstone.exception.RequestNotFoundException;
import com.company.SimonKwokU1Capstone.service.GameStoreService;
import com.company.SimonKwokU1Capstone.viewmodel.GameViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameStoreService gameStoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameViewModel createGame(@RequestBody @Valid GameViewModel gvm, Principal principal){
        return gameStoreService.addGame(gvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getAllGame(){
        return gameStoreService.getAllGame();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameViewModel getGameById(@PathVariable @Valid int id){
        GameViewModel gvm = gameStoreService.getGameById(id);
        if (gvm == null){
            throw new RequestNotFoundException("cannot find game id = "+id);
        }
        return gvm;
    }

    @GetMapping("/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getConsleByStudio(@PathVariable String studio){
        return gameStoreService.getGameByStudio(studio);
    }

    @GetMapping("/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getGameByTitle(@PathVariable String title){
        return gameStoreService.getGameByTitle(title);
    }

    @GetMapping("/esrb/{esrb}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getGameByEsrb(@PathVariable String esrb){
        return gameStoreService.getGameByEsrbRating(esrb);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody @Valid GameViewModel gvm,
                           @PathVariable @Valid int id,
                           Principal principal){
        // if the view model doesnt have any id, set the id from path to it
        if (gvm.getGameId()==0){
            gvm.setGameId(id);
        }
        // if the id from path doesnt match id from view model, reject it
        if(gvm.getGameId()!=id){
            throw new IllegalArgumentException("path id should match view model id");
        }
        gameStoreService.updateGame(gvm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id,Principal principal){
        gameStoreService.deleteGame(id);
    }


}
