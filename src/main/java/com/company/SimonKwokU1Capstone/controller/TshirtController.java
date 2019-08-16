package com.company.SimonKwokU1Capstone.controller;

import com.company.SimonKwokU1Capstone.service.GameStoreService;
import com.company.SimonKwokU1Capstone.viewmodel.TshirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tshirt")
public class TshirtController {

    @Autowired
    GameStoreService gameStoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TshirtViewModel createTshirt(@RequestBody @Valid TshirtViewModel tvm, Principal principal){
        return gameStoreService.addTshirt(tvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TshirtViewModel> getAllTshirt(){
        return gameStoreService.getAllTshirts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TshirtViewModel getTshirtById(@PathVariable @Valid int id){
        TshirtViewModel tvm = gameStoreService.getTshirtById(id);
        if (tvm ==null){
            throw new IllegalArgumentException("cannot find Tshirt id = "+id);
        }
        return tvm;
    }

    @GetMapping("/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<TshirtViewModel> getTshirtByColor(@PathVariable String color){
        return gameStoreService.getTshirtByColor(color);
    }

    @GetMapping("/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<TshirtViewModel> getTshirtBySize(@PathVariable String size){
        return gameStoreService.getTshirtBySize(size);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTshirt(@RequestBody @Valid TshirtViewModel tvm,
                             @PathVariable @Valid int id,
                             Principal principal){
        // if the view model doesnt have any id, set the id from path to it
        if (tvm.getTshirtId()==0){
            tvm.setTshirtId(id);
        }
        // if the id from path doesnt match id from view model, reject it
        if (tvm.getTshirtId()!=id){
            throw new IllegalArgumentException("path id should match with view model id");
        }
        gameStoreService.updateTshirt(tvm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@PathVariable @Valid int id,Principal principal){
        gameStoreService.deleteTshirt(id);
    }


}
