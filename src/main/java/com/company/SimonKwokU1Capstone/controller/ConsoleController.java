package com.company.SimonKwokU1Capstone.controller;

import com.company.SimonKwokU1Capstone.exception.RequestNotFoundException;
import com.company.SimonKwokU1Capstone.model.Console;
import com.company.SimonKwokU1Capstone.service.GameStoreService;
import com.company.SimonKwokU1Capstone.viewmodel.ConsoleViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    GameStoreService gameStoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsoleViewModel createConsole(@RequestBody @Valid ConsoleViewModel cvm, Principal principal){
        return gameStoreService.addConsole(cvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsoleViewModel> getAllConsole(){
        return gameStoreService.getAllConsoles();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsoleViewModel getConsoleById(@PathVariable @Valid int id){
        ConsoleViewModel cvm = gameStoreService.getConsoleById(id);
        if (cvm == null){
            throw new RequestNotFoundException("cannot find console with id "+id);
        }
        return cvm;
    }

    @GetMapping("/mfg/{mfg}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsoleViewModel> getConsoleByMfg(@PathVariable String mfg){
        return gameStoreService.getConsoleByMfg(mfg);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@RequestBody @Valid ConsoleViewModel cvm,
                              @PathVariable @Valid int id,
                              Principal principal){
        // if the view model doesnt have any id, set the id from path to it
        if (cvm.getConsoleId()==0){
            cvm.setConsoleId(id);
        }
        // if the id from path doesnt match id from view model, reject it
        if (cvm.getConsoleId()!= id){
            throw new IllegalArgumentException("view model id do not match with path id");
        }
        gameStoreService.updateConsole(cvm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable @Valid int id, Principal principal){
        gameStoreService.deleteConsole(id);
    }


}
