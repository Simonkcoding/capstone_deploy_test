package com.company.SimonKwokU1Capstone.controller;


import com.company.SimonKwokU1Capstone.service.GameStoreService;
import com.company.SimonKwokU1Capstone.viewmodel.PurchaseItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchasingItemController {
    @Autowired
    GameStoreService gameStoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseItemViewModel createPurchase(@RequestBody @Valid PurchaseItemViewModel pvm, Principal principal){
        return gameStoreService.addInvoice(pvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseItemViewModel> getAllPurchases(Principal principal){
        return gameStoreService.getAllInvoices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseItemViewModel getPurchaseById(@PathVariable @Valid int id,Principal principal){
        PurchaseItemViewModel pvm = gameStoreService.getInvoiceById(id);
        if (pvm ==null){
            throw new IllegalArgumentException("cannot find purchase item id ="+id);
        }
        return pvm;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePurchase(@RequestBody @Valid PurchaseItemViewModel pvm,
                              @PathVariable @Valid int id,
                               Principal principal){
        // if the view model doesnt have any id, set the id from path to it
        if (pvm.getInvoiceId()==0){
            pvm.setInvoiceId(id);
        }
        // if the id from path doesnt match id from view model, reject it
        if (pvm.getInvoiceId()!=id){
            throw new IllegalArgumentException("path id should match to model id");
        }
        gameStoreService.updateInvoice(pvm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchase(@PathVariable @Valid int id,Principal principal){
        gameStoreService.deleteInvoice(id);
    }

}
