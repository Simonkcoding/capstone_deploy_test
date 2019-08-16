package com.company.SimonKwokU1Capstone.service;


import com.company.SimonKwokU1Capstone.dao.*;
import com.company.SimonKwokU1Capstone.exception.StaticDataNotFoundException;
import com.company.SimonKwokU1Capstone.exception.ExceedNumberLengthLimitationException;
import com.company.SimonKwokU1Capstone.exception.NotEnoughInventoryException;
import com.company.SimonKwokU1Capstone.model.*;
import com.company.SimonKwokU1Capstone.viewmodel.ConsoleViewModel;
import com.company.SimonKwokU1Capstone.viewmodel.GameViewModel;
import com.company.SimonKwokU1Capstone.viewmodel.PurchaseItemViewModel;
import com.company.SimonKwokU1Capstone.viewmodel.TshirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameStoreService {

    // use all the Dao
    ConsoleDao consoleDao;
    GameDao gameDao;
    TshirtDao tshirtDao;
    ProcessFeeDao processFeeDao;
    SaleTaxRateDao saleTaxRateDao;
    InvoiceDao invoiceDao;

    //Auto-wired
    @Autowired
    public GameStoreService(ConsoleDao consoleDao, GameDao gameDao, TshirtDao tshirtDao, ProcessFeeDao processFeeDao,
                            SaleTaxRateDao saleTaxRateDao, InvoiceDao invoiceDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.tshirtDao = tshirtDao;
        this.processFeeDao = processFeeDao;
        this.saleTaxRateDao = saleTaxRateDao;
        this.invoiceDao = invoiceDao;
    }

    //build viewModels
    // Console
    private ConsoleViewModel buildConsoleViewModel(Console console) {
        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setConsoleId(console.getConsoleId());
        consoleViewModel.setModel(console.getModel());
        consoleViewModel.setManufacturer(console.getManufacturer());
        consoleViewModel.setMemoryAmount(console.getMemoryAmount());
        consoleViewModel.setProcessor(console.getProcessor());
        consoleViewModel.setPrice(console.getPrice());
        consoleViewModel.setQuantity(console.getQuantity());

        return consoleViewModel;
    }

    // Game
    private GameViewModel buildGameViewModel(Game game) {
        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setGameId(game.getGameId());
        gameViewModel.setTitle(game.getTitle());
        gameViewModel.setEsrbRating(game.getEsrbRating());
        gameViewModel.setDescription(game.getDescription());
        gameViewModel.setStudio(game.getStudio());
        gameViewModel.setPrice(game.getPrice());
        gameViewModel.setQuantity(game.getQuantity());

        return gameViewModel;
    }

    // Tshirt
    private TshirtViewModel buildTshirtViewModel(Tshirt game) {
        TshirtViewModel tshirtViewModel = new TshirtViewModel();
        tshirtViewModel.setTshirtId(game.getTshirtId());
        tshirtViewModel.setSize(game.getSize());
        tshirtViewModel.setColor(game.getColor());
        tshirtViewModel.setDescription(game.getDescription());
        tshirtViewModel.setPrice(game.getPrice());
        tshirtViewModel.setQuantity(game.getQuantity());

        return tshirtViewModel;
    }

    // Invoice
    @Transactional
    private PurchaseItemViewModel buildInvoiceViewModel(Invoice invoice) {
        PurchaseItemViewModel purchaseItemViewModel = new PurchaseItemViewModel();
        purchaseItemViewModel.setInvoiceId(invoice.getInvoiceId());
        purchaseItemViewModel.setName(invoice.getName());
        purchaseItemViewModel.setStreet(invoice.getStreet());
        purchaseItemViewModel.setCity(invoice.getCity());
        purchaseItemViewModel.setState(invoice.getState());
        purchaseItemViewModel.setZipCode(invoice.getZipCode());
        purchaseItemViewModel.setItemType(invoice.getItemType());
        purchaseItemViewModel.setItemId(invoice.getItemId());
        purchaseItemViewModel.setQuantity(invoice.getQuantity());
        // get List of purchase Item
        int id = purchaseItemViewModel.getItemId();
        if (purchaseItemViewModel.getItemType().equals("console")) {
            purchaseItemViewModel.setPurchaseItems(consoleDao.getConsoleById(id));
        } else if (purchaseItemViewModel.getItemType().equals("game")) {
            purchaseItemViewModel.setPurchaseItems(gameDao.getGameById(id));
        } else {
            purchaseItemViewModel.setPurchaseItems(tshirtDao.getTshirtById(id));
        }

        return purchaseItemViewModel;
    }


    // Console API
    //    Console addConsole(Console console);
    public ConsoleViewModel addConsole(ConsoleViewModel consoleViewModel) {
        Console console = new Console();
        console.setModel(consoleViewModel.getModel());
        console.setManufacturer(consoleViewModel.getManufacturer());
        console.setMemoryAmount(consoleViewModel.getMemoryAmount());
        console.setProcessor(consoleViewModel.getProcessor());
        console.setPrice(consoleViewModel.getPrice());
        console.setQuantity(consoleViewModel.getQuantity());
        console = consoleDao.addConsole(console);

        consoleViewModel.setConsoleId(console.getConsoleId());
        return consoleViewModel;
    }

    //    Console getConsoleById(int id);
    public ConsoleViewModel getConsoleById(int id) {
        Console console = consoleDao.getConsoleById(id);
        if (console == null) {
            return null;
        } else {
            return buildConsoleViewModel(console);
        }
    }

    //   List<Console> getAllConsoles();
    public List<ConsoleViewModel> getAllConsoles() {
        List<Console> consoles = consoleDao.getAllConsoles();
        List<ConsoleViewModel> cvmList = new ArrayList<>();
        consoles.stream().forEach(console ->
                cvmList.add(buildConsoleViewModel(console)));
        return cvmList;
    }

    //    List<Console> getConsoleByMfg(String mfg);
    public List<ConsoleViewModel> getConsoleByMfg(String mfg) {
        List<ConsoleViewModel> cvmList = new ArrayList<>();
        List<Console> consoles = consoleDao.getConsoleByMfg(mfg);
        for (Console console : consoles) {
            cvmList.add(buildConsoleViewModel(console));
        }
        return cvmList;
    }

    //    void updateConsole(Console console);
    public void updateConsole(ConsoleViewModel consoleViewModel) {
        Console console = new Console();
        console.setConsoleId(consoleViewModel.getConsoleId());
        console.setModel(consoleViewModel.getModel());
        console.setManufacturer(consoleViewModel.getManufacturer());
        console.setMemoryAmount(consoleViewModel.getMemoryAmount());
        console.setProcessor(consoleViewModel.getProcessor());
        console.setPrice(consoleViewModel.getPrice());
        console.setQuantity(consoleViewModel.getQuantity());
        consoleDao.updateConsole(console);
    }

    //    void deleteConsole(int id);
    public void deleteConsole(int id) {
        consoleDao.deleteConsole(id);
    }


    //Game API
    //   Game addGame(Game game);
    public GameViewModel addGame(GameViewModel gameViewModel) {
        Game game = new Game();
        game.setTitle(gameViewModel.getTitle());
        game.setEsrbRating(gameViewModel.getEsrbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setStudio(gameViewModel.getStudio());
        game.setPrice(gameViewModel.getPrice());
        game.setQuantity(gameViewModel.getQuantity());
        game = gameDao.addGame(game);

        gameViewModel.setGameId(game.getGameId());
        return gameViewModel;
    }

    //    Game getGameById(int id);
    public GameViewModel getGameById(int id) {
        Game game = gameDao.getGameById(id);
        if (game == null) {
            return null;
        } else {
            return buildGameViewModel(game);
        }
    }

    //    List<Game> getAllGames();
    public List<GameViewModel> getAllGame() {
        List<GameViewModel> gvmList = new ArrayList<>();
        List<Game> games = gameDao.getAllGames();
        games.stream().forEach(game -> gvmList.add(buildGameViewModel(game)));
        return gvmList;
    }

    //    List<Game> getGameByStudio(String studio);
    public List<GameViewModel> getGameByStudio(String studio) {
        List<GameViewModel> gvmList = new ArrayList<>();
        List<Game> gList = gameDao.getGameByStudio(studio);
        gList.stream().forEach(game -> gvmList.add(buildGameViewModel(game)));
        return gvmList;
    }

    //    List<Game> getGameByEsrbRating(String esrb);
    public List<GameViewModel> getGameByEsrbRating(String esrb) {
        List<GameViewModel> gvmList = new ArrayList<>();
        List<Game> gList = gameDao.getGameByEsrbRating(esrb);
        gList.stream().forEach(game -> gvmList.add(buildGameViewModel(game)));
        return gvmList;
    }

    //    List<Game> getGameByTitle(String title);
    public List<GameViewModel> getGameByTitle(String title) {
        List<GameViewModel> gvmList = new ArrayList<>();
        List<Game> gList = gameDao.getGameByTitle(title);
        gList.stream().forEach(game -> gvmList.add(buildGameViewModel(game)));
        return gvmList;
    }

    //    void updateGame(Game game);
    public void updateGame(GameViewModel gameViewModel) {
        Game game = new Game();
        game.setGameId(gameViewModel.getGameId());
        game.setTitle(gameViewModel.getTitle());
        game.setEsrbRating(gameViewModel.getEsrbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setStudio(gameViewModel.getStudio());
        game.setPrice(gameViewModel.getPrice());
        game.setQuantity(gameViewModel.getQuantity());
        gameDao.updateGame(game);
    }

    //    void deleteGame(int id);
    public void deleteGame(int id) {
        gameDao.deleteGame(id);
    }

    //Tshirt API
    //     Tshirt addTshirt(Tshirt tshirt);
    public TshirtViewModel addTshirt(TshirtViewModel tshirtViewModel) {
        Tshirt tshirt = new Tshirt();
        tshirt.setSize(tshirtViewModel.getSize());
        tshirt.setColor(tshirtViewModel.getColor());
        tshirt.setDescription(tshirtViewModel.getDescription());
        tshirt.setPrice(tshirtViewModel.getPrice());
        tshirt.setQuantity(tshirtViewModel.getQuantity());
        tshirt = tshirtDao.addTshirt(tshirt);

        tshirtViewModel.setTshirtId(tshirt.getTshirtId());
        return tshirtViewModel;
    }

    //    Tshirt getTshirtById(int id);
    public TshirtViewModel getTshirtById(int id) {
        Tshirt tshirt = tshirtDao.getTshirtById(id);
        if (tshirt == null) {
            return null;
        } else {
            return buildTshirtViewModel(tshirt);
        }
    }

    //    List<Tshirt> getAllTshirts();
    public List<TshirtViewModel> getAllTshirts() {
        List<TshirtViewModel> tvmList = new ArrayList<>();
        List<Tshirt> Tshirts = tshirtDao.getAllTshirts();
        Tshirts.stream().forEach(tshirt -> tvmList.add(buildTshirtViewModel(tshirt)));
        return tvmList;
    }


    //    List<Tshirt> getTshirtByColor(String color);
    public List<TshirtViewModel> getTshirtByColor(String color) {
        List<Tshirt> tList = tshirtDao.getTshirtByColor(color);
        List<TshirtViewModel> tvmList = new ArrayList<>();
        tList.stream().forEach(tshirt -> tvmList.add(buildTshirtViewModel(tshirt)));
        return tvmList;
    }

    //    List<Tshirt> getTshirtBySize(String size);
    public List<TshirtViewModel> getTshirtBySize(String size) {
        List<Tshirt> tList = tshirtDao.getTshirtBySize(size);
        List<TshirtViewModel> tvmList = new ArrayList<>();
        tList.stream().forEach(tshirt -> tvmList.add(buildTshirtViewModel(tshirt)));
        return tvmList;
    }

    //    void updateTshirt(Tshirt tshirt);
    public void updateTshirt(TshirtViewModel tshirtViewModel) {
        Tshirt tshirt = new Tshirt();
        tshirt.setTshirtId(tshirtViewModel.getTshirtId());
        tshirt.setSize(tshirtViewModel.getSize());
        tshirt.setColor(tshirtViewModel.getColor());
        tshirt.setDescription(tshirtViewModel.getDescription());
        tshirt.setPrice(tshirtViewModel.getPrice());
        tshirt.setQuantity(tshirtViewModel.getQuantity());
        tshirtDao.updateTshirt(tshirt);
    }

    //    void deleteTshirt(int id);
    public void deleteTshirt(int id) {
        tshirtDao.deleteTshirt(id);
    }


    //Invoice API
    //    Invoice addInvoice(Invoice invoice);
    @Transactional
    public PurchaseItemViewModel addInvoice(PurchaseItemViewModel purchaseItemViewModel) {
        // Common properties of Invoice and PurchaseItemViewModel:
        // 1      private int invoiceId;
        // 2      private String name;
        // 3      private String street;
        // 4      private String city;
        // 5      private String state;
        // 6      private String zipCode;
        // 7      private String itemType;
        // 8      private int itemId;
        // 9      private int quantity;
        // Properties only appear in Invoice, which are calculated before throwing it to the Dao.add
        // 10a    private BigDecimal unitPrice;
        // 11a    private BigDecimal subtotal;
        // 12a    private BigDecimal tax;
        // 13a    private BigDecimal processFee;
        // 14a    private BigDecimal total;
        // Properties only appear in PurchaseItemViewModel, which are get from Dao and inserted,
        // we need unitPrice and quantity for calculation, these are abstracted
        // 10b    private PurchaseItem purchaseItems;


        // set up Invoice invoice for Dao.add
        Invoice invoice = new Invoice();
        invoice.setName(purchaseItemViewModel.getName());         //2
        invoice.setStreet(purchaseItemViewModel.getStreet());     //3
        invoice.setCity(purchaseItemViewModel.getCity());         //4
        invoice.setState(purchaseItemViewModel.getState());       //5
        invoice.setZipCode(purchaseItemViewModel.getZipCode());   //6
        invoice.setItemType(purchaseItemViewModel.getItemType()); //7
        invoice.setItemId(purchaseItemViewModel.getItemId());     //8
        invoice.setQuantity(purchaseItemViewModel.getQuantity()); //9

        // insert purchase item to PurchaseItemViewModel according to type  //10b
        int id = purchaseItemViewModel.getItemId();
        if (purchaseItemViewModel.getItemType().equals("console")) {
            Console tempConsole = consoleDao.getConsoleById(id);
            if (tempConsole == null) {
                throw new StaticDataNotFoundException("cannot not find item in console of id = " + id);
            }
            // check inventory
            if (tempConsole.getQuantity()<invoice.getQuantity()){
                throw new NotEnoughInventoryException("not enough console");
            }
            // update inventory
            int consoleInventory = tempConsole.getQuantity();
            tempConsole.setQuantity(consoleInventory - invoice.getQuantity());
            consoleDao.updateConsole(tempConsole);
            // insert the item into purchase item model
            purchaseItemViewModel.setPurchaseItems(tempConsole);
        } else if (purchaseItemViewModel.getItemType().equals("game")) {
            Game tempGame = gameDao.getGameById(id);
            if (tempGame == null) {
                throw new StaticDataNotFoundException("cannot not find item in game of id = " + id);
            }
            // check inventory
            if (tempGame.getQuantity()<invoice.getQuantity()){
                throw new NotEnoughInventoryException("not enough game");
            }
            // update inventory
            int gameInventory = tempGame.getQuantity();
            tempGame.setQuantity(gameInventory - invoice.getQuantity());
            gameDao.updateGame(tempGame);
            // insert the item into purchase item model
            purchaseItemViewModel.setPurchaseItems(tempGame);
        } else if (purchaseItemViewModel.getItemType().equals("tshirt")) {
            Tshirt tempTshirt = tshirtDao.getTshirtById(id);
            if (tempTshirt == null) {
                throw new StaticDataNotFoundException("cannot not find item in Tshirt of id = " + id);
            }
            // check inventory
            if (tempTshirt.getQuantity()<invoice.getQuantity()){
                throw new NotEnoughInventoryException("not enough game");
            }
            // update inventory
            int tshirtInventory = tempTshirt.getQuantity();
            tempTshirt.setQuantity(tshirtInventory - invoice.getQuantity());
            tshirtDao.updateTshirt(tempTshirt);
            // insert the item into purchase item model
            purchaseItemViewModel.setPurchaseItems(tempTshirt);
        } else {
            throw new StaticDataNotFoundException("no item type");
        }

        PurchaseItem purchaseItem = purchaseItemViewModel.getPurchaseItems();
        invoice.setUnitPrice(purchaseItem.getPrice());       //10a
        invoice.setSubtotal(BigDecimal.valueOf(invoice.getQuantity()).multiply(invoice.getUnitPrice())); //11a
        // get tax by state
        BigDecimal tax; //12a
        try {
            tax = saleTaxRateDao.getSaleTaxRateByState(invoice.getState()).getRate();
        } catch (RuntimeException e) {
            throw new StaticDataNotFoundException("State not found");
        }
        BigDecimal taxAmount = invoice.getSubtotal().multiply(tax).setScale(2, BigDecimal.ROUND_HALF_UP);
        invoice.setTax(taxAmount);
        // the number of items on the order is greater than 10 in which case
        // an additional processing fee of $15.49 is applied to the order.      //13a
        BigDecimal extraFee = new BigDecimal("0").setScale(2);
        if (invoice.getQuantity() > 10) {
            extraFee = new BigDecimal("15.49");
        }
        // The processing fee is applied only once per order
        // regardless of the number of items in the order
        BigDecimal processFee = processFeeDao.getProcessFeeByItemType(invoice.getItemType()).getFee();
        invoice.setProcessFee(processFee.add(extraFee).setScale(2, BigDecimal.ROUND_HALF_UP));
        // 14a Calculate total
        BigDecimal total = invoice.getSubtotal().add(invoice.getTax()).add(invoice.getProcessFee()).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        if (String.valueOf(total).length() > 6) {
            throw new ExceedNumberLengthLimitationException("Total exceed storable value of database, adjust database" +
                    " setting or quantity");
        }
        invoice.setTotal(total);
        invoice = invoiceDao.addInvoice(invoice);
        purchaseItemViewModel.setInvoiceId(invoice.getInvoiceId()); //1 id appear after adding
        return purchaseItemViewModel;

    }

    //    Invoice getInvoiceById(int id);
    public PurchaseItemViewModel getInvoiceById(int id) {
        Invoice invoice = invoiceDao.getInvoiceById(id);
        if (invoice == null) {
            return null;
        } else {
            return buildInvoiceViewModel(invoice);
        }
    }

    //    List<Invoice> getAllInvoices();
    public List<PurchaseItemViewModel> getAllInvoices() {
        List<PurchaseItemViewModel> ivmList = new ArrayList<>();
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.stream().forEach(invoice -> ivmList.add(buildInvoiceViewModel(invoice)));
        return ivmList;
    }

    //    void updateInvoice(Invoice invoice);
    @Transactional
    public void updateInvoice(PurchaseItemViewModel purchaseItemViewModel) {

        // check the returning product
        Invoice oldInvoice = invoiceDao.getInvoiceById(purchaseItemViewModel.getInvoiceId());
        int oldInventory = oldInvoice.getQuantity();
        // return the returning product
        if (oldInvoice.getItemType().equals("console")){
            Console console = consoleDao.getConsoleById(oldInvoice.getItemId());
            int originInventory = console.getQuantity();
            console.setQuantity(originInventory+oldInventory);
            consoleDao.updateConsole(console);
        } else if (oldInvoice.getItemType().equals("game")){
            Game game = gameDao.getGameById(oldInvoice.getItemId());
            int originInventory = game.getQuantity();
            game.setQuantity(originInventory+oldInventory);
            gameDao.updateGame(game);
        } else if (oldInvoice.getItemType().equals("tshirt")) {
            Tshirt tshirt = tshirtDao.getTshirtById(oldInvoice.getItemId());
            int originInventory = tshirt.getQuantity();
            tshirt.setQuantity(originInventory + oldInventory);
            tshirtDao.updateTshirt(tshirt);
        }else {
            throw new StaticDataNotFoundException("the purchased item is no longer available for refund. Please contact representative");
        }

        // Create an Invoice invoice for update
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(purchaseItemViewModel.getInvoiceId());  //1
        invoice.setName(purchaseItemViewModel.getName());            //2
        invoice.setStreet(purchaseItemViewModel.getStreet());        //3
        invoice.setCity(purchaseItemViewModel.getCity());            //4
        invoice.setState(purchaseItemViewModel.getState());          //5
        invoice.setZipCode(purchaseItemViewModel.getZipCode());      //6
        invoice.setItemType(purchaseItemViewModel.getItemType());    //7
        invoice.setItemId(purchaseItemViewModel.getItemId());        //8
        invoice.setQuantity(purchaseItemViewModel.getQuantity());    //9

        // insert purchase item to PurchaseItemViewModel according to type  //10b
        int id = purchaseItemViewModel.getItemId();
        if (purchaseItemViewModel.getItemType().equals("console")) {
            Console tempConsole = consoleDao.getConsoleById(id);
            if (tempConsole == null) {
                throw new StaticDataNotFoundException("cannot not find item in console of id = " + id);
            }
            // check inventory
            if (tempConsole.getQuantity()<invoice.getQuantity()){
                throw new NotEnoughInventoryException("not enough console");
            }
            // update inventory
            int consoleInventory = tempConsole.getQuantity();
            tempConsole.setQuantity(consoleInventory - invoice.getQuantity());
            consoleDao.updateConsole(tempConsole);
            // insert the item into purchase item model
            purchaseItemViewModel.setPurchaseItems(tempConsole);
        } else if (purchaseItemViewModel.getItemType().equals("game")) {
            Game tempGame = gameDao.getGameById(id);
            if (tempGame == null) {
                throw new StaticDataNotFoundException("cannot not find item in game of id = " + id);
            }
            // check inventory
            if (tempGame.getQuantity()<invoice.getQuantity()){
                throw new NotEnoughInventoryException("not enough game");
            }
            // update inventory
            int gameInventory = tempGame.getQuantity();
            tempGame.setQuantity(gameInventory - invoice.getQuantity());
            gameDao.updateGame(tempGame);
            // insert the item into purchase item model
            purchaseItemViewModel.setPurchaseItems(tempGame);
        } else if (purchaseItemViewModel.getItemType().equals("tshirt")) {
            Tshirt tempTshirt = tshirtDao.getTshirtById(id);
            if (tempTshirt == null) {
                throw new StaticDataNotFoundException("cannot not find item in Tshirt of id = " + id);
            }
            // check inventory
            if (tempTshirt.getQuantity()<invoice.getQuantity()){
                throw new NotEnoughInventoryException("not enough game");
            }
            // update inventory
            int tshirtInventory = tempTshirt.getQuantity();
            tempTshirt.setQuantity(tshirtInventory - invoice.getQuantity());
            tshirtDao.updateTshirt(tempTshirt);
            // insert the item into purchase item model
            purchaseItemViewModel.setPurchaseItems(tempTshirt);
        } else {
            throw new StaticDataNotFoundException("no item type");
        }

        PurchaseItem purchaseItem = purchaseItemViewModel.getPurchaseItems();
        invoice.setUnitPrice(purchaseItem.getPrice());       //10a

        invoice.setSubtotal(BigDecimal.valueOf(invoice.getQuantity()).multiply(invoice.getUnitPrice())); //11a
        //Sales tax applies only to the cost of the items.
        BigDecimal tax; //12a
        try {
            tax = saleTaxRateDao.getSaleTaxRateByState(invoice.getState()).getRate();
        } catch (RuntimeException e) {
            throw new StaticDataNotFoundException("State not found");
        }
        BigDecimal taxAmount = invoice.getSubtotal().multiply(tax).setScale(2, BigDecimal.ROUND_HALF_UP);
        invoice.setTax(taxAmount);
        // the number of items on the order is greater than 10 in which case
        // an additional processing fee of $15.49 is applied to the order.    //13a
        BigDecimal extraFee = new BigDecimal("0").setScale(2);
        if (invoice.getQuantity() > 10) {
            extraFee = new BigDecimal("15.49");
        }
        // The processing fee is applied only once per order
        // regardless of the number of items in the order
        BigDecimal processFee = processFeeDao.getProcessFeeByItemType(invoice.getItemType()).getFee();
        invoice.setProcessFee(processFee.add(extraFee).setScale(2, BigDecimal.ROUND_HALF_UP));
        // calculate total // 14a
        BigDecimal total = invoice.getSubtotal().add(invoice.getTax()).add(invoice.getProcessFee()).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        if (String.valueOf(total).length() > 6) {
            throw new ExceedNumberLengthLimitationException("Total exceed storable value of database, adjust database" +
                    " setting or quantity");
        }
        invoice.setTotal(total);
        invoiceDao.updateInvoice(invoice);
    }

    //    void deleteInvoice(int id);
    public void deleteInvoice(int id) {
        invoiceDao.deleteInvoice(id);
    }

    // process free and tax
    public ProcessingFee addProcessFee(ProcessingFee fee) {
        return processFeeDao.addProcessFee(fee);
    }

    public ProcessingFee getProcessFeeByItemType(String type) {
        return processFeeDao.getProcessFeeByItemType(type);
    }

    public SaleTaxRate addSaleTaxRate(SaleTaxRate tax) {
        return saleTaxRateDao.addSaleTaxRate(tax);
    }

    public SaleTaxRate getSaleTaxRateByState(String state) {
        return saleTaxRateDao.getSaleTaxRateByState(state);
    }
}

// Done       Sales tax applies only to the cost of the items.
// Done       Sales tax does not apply to any processing fees for an invoice.
// Done       The processing fee is applied only once per order regardless of the number of items in the order unless
// the number of items on the order is greater than 10 in which case an additional processing fee of $15.49 is
// applied to the order.
// Done       The order process logic must properly update the quantity on hand for the item in the order.
// Done       Order quantity must be greater than zero.
// Done       Order quantity must be less than or equal to the number of items on hand in inventory.
// Done       Order must contain a valid state code.
// Done       The REST API must properly handle and report all violations of business rules.
