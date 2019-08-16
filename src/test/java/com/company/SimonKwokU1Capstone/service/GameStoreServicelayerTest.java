package com.company.SimonKwokU1Capstone.service;

import com.company.SimonKwokU1Capstone.dao.*;
import com.company.SimonKwokU1Capstone.exception.StaticDataNotFoundException;
import com.company.SimonKwokU1Capstone.model.*;
import com.company.SimonKwokU1Capstone.viewmodel.ConsoleViewModel;
import com.company.SimonKwokU1Capstone.viewmodel.GameViewModel;
import com.company.SimonKwokU1Capstone.viewmodel.PurchaseItemViewModel;
import com.company.SimonKwokU1Capstone.viewmodel.TshirtViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GameStoreServicelayerTest {

    // use all Dao and service
    ConsoleDao consoleDao;
    GameDao gameDao;
    TshirtDao tshirtDao;
    ProcessFeeDao processFeeDao;
    SaleTaxRateDao saleTaxRateDao;
    InvoiceDao invoiceDao;
    GameStoreService gameStoreService;

    @Before
    public void setUp() throws Exception {
        //  invoke setup methods
        setUpGameDaoMock();
        setUpConsoleDaoMock();
        setUpTshirtDaoMock();
        setUpInvoiceDaoMock();
        setUpSaleTaxRate();
        setUpProcessingFee();

        gameStoreService = new GameStoreService(
                consoleDao,
                gameDao,
                tshirtDao,
                processFeeDao,
                saleTaxRateDao,
                invoiceDao);
    }

    // setUpMethods for Game, Tshirt, Console,
    // Invoice, Processfee, tax;
    private void setUpGameDaoMock() {
        gameDao = mock(GameDaoJdbcTemplateImpl.class);

        Game game = new Game();
        game.setGameId(4);
        game.setTitle("Dead or Alive Volley Ball");
        game.setEsrbRating("E");
        game.setDescription("Good for Boys");
        game.setStudio("Tecmo");
        game.setPrice(new BigDecimal("10.00"));
        game.setQuantity(12);

        Game game1 = new Game();
        game1.setTitle("Dead or Alive Volley Ball");
        game1.setEsrbRating("E");
        game1.setDescription("Good for Boys");
        game1.setStudio("Tecmo");
        game1.setPrice(new BigDecimal("10.00"));
        game1.setQuantity(12);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);

        // add, get 1 get all get by properties
        doReturn(game).when(gameDao).addGame(game1);
        doReturn(game).when(gameDao).getGameById(4);
        doReturn(gameList).when(gameDao).getAllGames();
        doReturn(gameList).when(gameDao).getGameByEsrbRating("E");
        doReturn(gameList).when(gameDao).getGameByStudio("Tecmo");
        doReturn(gameList).when(gameDao).getGameByTitle("Dead or Alive Volley Ball");

        // update
        Game game2 = new Game();
        game2.setGameId(4);
        game2.setTitle("Cooking Mama");
        game2.setEsrbRating("R");
        game2.setDescription("Not suitable for girls");
        game2.setStudio("Office Create");
        game2.setPrice(new BigDecimal("110.99"));
        game2.setQuantity(99);

        Mockito.spy(gameDao).updateGame(game2);

        //delete
        Mockito.spy(gameDao).deleteGame(game.getGameId());
    }

    private void setUpConsoleDaoMock() {
        consoleDao = mock(ConsoleDaoJdbcTemplateImpl.class);
        Console console = new Console();
        console.setConsoleId(2);
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setMemoryAmount("124MB");
        console.setProcessor("Intel4");
        console.setPrice(new BigDecimal("100.00").setScale(2));
        console.setQuantity(99);

        Console console1 = new Console();
        console1.setModel("PS4");
        console1.setManufacturer("Sony");
        console1.setMemoryAmount("124MB");
        console1.setProcessor("Intel4");
        console1.setPrice(new BigDecimal("100.00").setScale(2));
        console1.setQuantity(99);

        List<Console> consoleList = new ArrayList<>();
        consoleList.add(console);

        // test add get 1 get all get by properties
        doReturn(console).when(consoleDao).addConsole(console1);
        doReturn(console).when(consoleDao).getConsoleById(2);
        doReturn(consoleList).when(consoleDao).getAllConsoles();
        doReturn(consoleList).when(consoleDao).getConsoleByMfg("Sony");

    }

    private void setUpTshirtDaoMock() {
        tshirtDao = mock(TshirtDaoJdbcTemplateImpl.class);
        Tshirt tshirt = new Tshirt();
        tshirt.setTshirtId(5);
        tshirt.setColor("red");
        tshirt.setSize("XL");
        tshirt.setDescription("avengers");
        tshirt.setPrice(new BigDecimal("10.99"));
        tshirt.setQuantity(10);

        Tshirt tshirt1 = new Tshirt();
        tshirt1.setColor("red");
        tshirt1.setSize("XL");
        tshirt1.setDescription("avengers");
        tshirt1.setPrice(new BigDecimal("10.99"));
        tshirt1.setQuantity(10);

        List<Tshirt> tshirtList = new ArrayList<>();
        tshirtList.add(tshirt);

        // test get 1 get all get by properties
        doReturn(tshirt).when(tshirtDao).addTshirt(tshirt1);
        doReturn(tshirt).when(tshirtDao).getTshirtById(5);
        doReturn(tshirtList).when(tshirtDao).getAllTshirts();
        doReturn(tshirtList).when(tshirtDao).getTshirtByColor("red");
        doReturn(tshirtList).when(tshirtDao).getTshirtBySize("XL");

    }

    //set up tax
    private void setUpSaleTaxRate() {
        saleTaxRateDao = mock(SaleTaxRateDaoJdbcTemplateImpl.class);
        SaleTaxRate taxRate = new SaleTaxRate();
        taxRate.setRate(new BigDecimal("0.05"));
        taxRate.setState("NC");

        SaleTaxRate taxRate2 = new SaleTaxRate();
        taxRate2.setRate(new BigDecimal("0.06"));
        taxRate2.setState("SC");

        doReturn(taxRate).when(saleTaxRateDao).addSaleTaxRate(taxRate);
        doReturn(taxRate).when(saleTaxRateDao).getSaleTaxRateByState("NC");
        doReturn(taxRate2).when(saleTaxRateDao).getSaleTaxRateByState("SC");
        doReturn(null).when(saleTaxRateDao).getSaleTaxRateByState("HK");

    }

    // set up processing fee
    private void setUpProcessingFee() {
        processFeeDao = mock(ProcessFeeDaoJdbcTemplateImpl.class);
        ProcessingFee pf = new ProcessingFee();
        pf.setFee(new BigDecimal("1.49"));
        pf.setProductType("game");

        ProcessingFee pf2 = new ProcessingFee();
        pf2.setFee(new BigDecimal("14.99"));
        pf2.setProductType("console");

        doReturn(pf).when(processFeeDao).addProcessFee(pf);
        doReturn(pf).when(processFeeDao).getProcessFeeByItemType("game");
        doReturn(pf2).when(processFeeDao).getProcessFeeByItemType("console");
    }

    private void setUpInvoiceDaoMock() {
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(99);
        invoice.setName("Simon");
        invoice.setStreet("Street1");
        invoice.setCity("City1");
        invoice.setState("NC");
        invoice.setZipCode("12345");
        invoice.setItemType("game");
        invoice.setItemId(4);
        invoice.setUnitPrice(new BigDecimal("10.00"));
        invoice.setQuantity(1);
        invoice.setSubtotal(new BigDecimal("10.00"));
        invoice.setTax(new BigDecimal("0.50"));
        invoice.setProcessFee(new BigDecimal("1.49"));
        invoice.setTotal(new BigDecimal("11.99"));

        Invoice invoice1 = new Invoice();
        invoice1.setName("Simon");
        invoice1.setStreet("Street1");
        invoice1.setCity("City1");
        invoice1.setState("NC");
        invoice1.setZipCode("12345");
        invoice1.setItemType("game");
        invoice1.setItemId(4);
        invoice1.setUnitPrice(new BigDecimal("10.00"));
        invoice1.setQuantity(1);
        invoice1.setSubtotal(new BigDecimal("10.00"));
        invoice1.setTax(new BigDecimal("0.50"));
        invoice1.setProcessFee(new BigDecimal("1.49"));
        invoice1.setTotal(new BigDecimal("11.99"));

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoiceById(99);
        doReturn(invoices).when(invoiceDao).getAllInvoices();

    }

    //test Add and Get1
    //test Game
    @Test
    public void testSaveFind1FindAllGame() {
        GameViewModel gvm = new GameViewModel();
        gvm.setTitle("Dead or Alive Volley Ball");
        gvm.setEsrbRating("E");
        gvm.setDescription("Good for Boys");
        gvm.setStudio("Tecmo");
        gvm.setPrice(new BigDecimal("10.00"));
        gvm.setQuantity(12);
        int id = gvm.getGameId();
        gvm = gameStoreService.addGame(gvm);
        GameViewModel fromService = gameStoreService.getGameById(gvm.getGameId());
        assertEquals(gvm, fromService);

        // get all
        List<GameViewModel> gvmList = gameStoreService.getAllGame();
        assertEquals(gvmList.size(), 1);

        // studio;
        gvmList = gameStoreService.getGameByStudio("Tecmo");
        assertEquals(gvmList.size(), 1);

        // esrb;
        gvmList = gameStoreService.getGameByEsrbRating("E");
        assertEquals(gvmList.size(), 1);

        // title;
        gvmList = gameStoreService.getGameByTitle("Dead or Alive Volley Ball");
        assertEquals(gvmList.size(), 1);
    }

    // get by studio
    // get by esrb
    // get by title
    @Test
    public void testGetByStudioByEsrbByTitleGame(){
        GameViewModel gvm = new GameViewModel();
        gvm.setTitle("Dead or Alive Volley Ball");
        gvm.setEsrbRating("E");
        gvm.setDescription("Good for Boys");
        gvm.setStudio("Tecmo");
        gvm.setPrice(new BigDecimal("10.00"));
        gvm.setQuantity(12);
        int id = gvm.getGameId();
        gvm = gameStoreService.addGame(gvm);

        // test get by title
        List<GameViewModel> gvmList = gameStoreService.getGameByTitle("Dead or Alive Volley Ball");
        assertEquals(gvmList.size(),1);

        // get by esrb
        gvmList = gameStoreService.getGameByEsrbRating("E");
        assertEquals(gvmList.size(),1);

        // get by Studio
        gvmList = gameStoreService.getGameByStudio("Tecmo");
        assertEquals(gvmList.size(),1);

    }

    //test console
    @Test
    public void testSaveFind1FindAllConsole() {
        ConsoleViewModel cvm = new ConsoleViewModel();
        cvm.setModel("PS4");
        cvm.setManufacturer("Sony");
        cvm.setMemoryAmount("124MB");
        cvm.setProcessor("Intel4");
        cvm.setPrice(new BigDecimal("100.00").setScale(2));
        cvm.setQuantity(99);
        int id = cvm.getConsoleId();
        cvm = gameStoreService.addConsole(cvm);
        ConsoleViewModel fromService = gameStoreService.getConsoleById(cvm.getConsoleId());
        assertEquals(cvm, fromService);

        // find all
        List<ConsoleViewModel> cvmList = gameStoreService.getAllConsoles();
        assertEquals(cvmList.size(), 1);

        // test mfg
        cvmList = gameStoreService.getConsoleByMfg("Sony");
        assertEquals(cvmList.size(), 1);
    }

    // console
    // get by mfg
    @Test
    public void testGetByManufacturerConsole(){
        ConsoleViewModel cvm = new ConsoleViewModel();
        cvm.setModel("PS4");
        cvm.setManufacturer("Sony");
        cvm.setMemoryAmount("124MB");
        cvm.setProcessor("Intel4");
        cvm.setPrice(new BigDecimal("100.00").setScale(2));
        cvm.setQuantity(99);
        int id = cvm.getConsoleId();
        cvm = gameStoreService.addConsole(cvm);

        List<ConsoleViewModel> cvmList = gameStoreService.getConsoleByMfg("Sony");
        assertEquals(cvmList.size(), 1);
    }

    //test Tshirt
    @Test
    public void testSaveFind1FindAllTshirt() {
        TshirtViewModel tvm = new TshirtViewModel();
        tvm.setColor("red");
        tvm.setSize("XL");
        tvm.setDescription("avengers");
        tvm.setPrice(new BigDecimal("10.99"));
        tvm.setQuantity(10);
        int id = tvm.getTshirtId();
        tvm = gameStoreService.addTshirt(tvm);
        TshirtViewModel fromService = gameStoreService.getTshirtById(tvm.getTshirtId());
        assertEquals(tvm, fromService);

        //get all
        List<TshirtViewModel> listFromService = gameStoreService.getAllTshirts();
        assertEquals(listFromService.size(), 1);

        // test size
        listFromService = gameStoreService.getTshirtByColor(tvm.getColor());
        assertEquals(listFromService.size(), 1);

        // test color
        listFromService = gameStoreService.getTshirtBySize(tvm.getSize());
        assertEquals(listFromService.size(), 1);
    }

    // t shirt
    // get by size
    @Test
    public void testGetBySizeColorTshirt(){
        TshirtViewModel tvm = new TshirtViewModel();
        tvm.setColor("red");
        tvm.setSize("XL");
        tvm.setDescription("avengers");
        tvm.setPrice(new BigDecimal("10.99"));
        tvm.setQuantity(10);
        int id = tvm.getTshirtId();
        tvm = gameStoreService.addTshirt(tvm);

        // get by color
        List<TshirtViewModel> tvmList = gameStoreService.getTshirtByColor("red");
        assertEquals(tvmList.size(),1);

        // get by size
        tvmList = gameStoreService.getTshirtBySize("XL");
        assertEquals(tvmList.size(),1);
    }


    // test processing fee
    // inside the service layer there is a call for process fee and tax
    @Test
    public void testSaveGetProcessFee() {
        ProcessingFee pf = new ProcessingFee();
        pf.setFee(new BigDecimal("1.49"));
        pf.setProductType("game");
        pf = gameStoreService.addProcessFee(pf);
        ProcessingFee pf2 = gameStoreService.getProcessFeeByItemType("game");
        assertEquals(pf.getFee(), pf2.getFee());
    }

    // test sale tax
    @Test
    public void testSaveGetSaleTax() {
        SaleTaxRate taxRate = new SaleTaxRate();
        taxRate.setRate(new BigDecimal("0.05"));
        taxRate.setState("NC");
        taxRate = gameStoreService.addSaleTaxRate(taxRate);
        SaleTaxRate tax2 = gameStoreService.getSaleTaxRateByState("NC");
        assertEquals(taxRate.getRate(), tax2.getRate());
    }

    //test Invoice
    @Test
    public void testSaveFind1FindAllInvoice() {

        PurchaseItemViewModel ivm = new PurchaseItemViewModel();
        ivm.setName("Simon");
        ivm.setStreet("Street1");
        ivm.setCity("City1");
        ivm.setState("NC");
        ivm.setZipCode("12345");
        ivm.setItemType("game");
        ivm.setItemId(4);
        ivm.setQuantity(1);
        ivm = gameStoreService.addInvoice(ivm);
        PurchaseItemViewModel fromService = gameStoreService.getInvoiceById(ivm.getInvoiceId());
        assertEquals(ivm, fromService);

        // get all
        List<PurchaseItemViewModel> purchaseItemViewModels = gameStoreService.getAllInvoices();
        assertEquals(purchaseItemViewModels.size(),1);
    }
    //test invalid state
    @Test(expected = StaticDataNotFoundException.class)
    public void testInvalidStateInvoice() {
        PurchaseItemViewModel ivm = new PurchaseItemViewModel();
        ivm.setName("Simon");
        ivm.setStreet("Street1");
        ivm.setCity("City1");
        ivm.setState("HK");
        ivm.setZipCode("12345");
        ivm.setItemType("game");
        ivm.setItemId(4);
        ivm.setQuantity(1);
        ivm = gameStoreService.addInvoice(ivm);
    }
    //test invalid item type
    @Test(expected = StaticDataNotFoundException.class)
    public void testInvalidItemTypeInvoice() {
        PurchaseItemViewModel ivm = new PurchaseItemViewModel();
        ivm.setName("Simon");
        ivm.setStreet("Street1");
        ivm.setCity("City1");
        ivm.setState("NC");
        ivm.setZipCode("12345");
        ivm.setItemType("action figure");
        ivm.setItemId(4);
        ivm.setQuantity(1);
        ivm = gameStoreService.addInvoice(ivm);
    }

    // test update and delete
    // game
    @Test
    public void testUpdateDeleteGame() {
        GameViewModel gvm = new GameViewModel();
        gvm.setTitle("Dead or Alive Volley Ball");
        gvm.setEsrbRating("E");
        gvm.setDescription("Good for Boys");
        gvm.setStudio("Tecmo");
        gvm.setPrice(new BigDecimal("10.00"));
        gvm.setQuantity(12);
        int id = gvm.getGameId();
        gvm = gameStoreService.addGame(gvm);

        GameViewModel gvm2 = new GameViewModel();
        gvm2.setGameId(4);
        gvm2.setTitle("Cooking Mama");
        gvm2.setEsrbRating("R");
        gvm2.setDescription("Not suitable for girls");
        gvm2.setStudio("Office Create");
        gvm2.setPrice(new BigDecimal("100.00"));
        gvm2.setQuantity(99);

        gameStoreService.updateGame(gvm2);
        gameStoreService.deleteGame(gvm.getGameId());

    }

    // t shirt
    @Test
    public void testUpdateDeleteTshirt() {
        TshirtViewModel tvm = new TshirtViewModel();
        tvm.setColor("red");
        tvm.setSize("XL");
        tvm.setDescription("avengers");
        tvm.setPrice(new BigDecimal("10.99"));
        tvm.setQuantity(10);
        tvm = gameStoreService.addTshirt(tvm);

        TshirtViewModel tvm1 = new TshirtViewModel();
        tvm1.setTshirtId(5);
        tvm1.setColor("blue");
        tvm1.setSize("M");
        tvm1.setDescription("Toy Story");
        tvm1.setPrice(new BigDecimal("5.99"));
        tvm1.setQuantity(99);

        gameStoreService.updateTshirt(tvm1);

        Tshirt tshirt2 = new Tshirt();
        tshirt2.setTshirtId(5);
        tshirt2.setColor("blue");
        tshirt2.setSize("M");
        tshirt2.setDescription("Toy Story");
        tshirt2.setPrice(new BigDecimal("5.99"));
        tshirt2.setQuantity(99);
        //update
        Mockito.verify(tshirtDao).updateTshirt(tshirt2);

        //delete
        gameStoreService.deleteTshirt(tvm.getTshirtId());
        Mockito.verify(tshirtDao).deleteTshirt(tvm.getTshirtId());
    }

    // console
    @Test
    public void testUpdateDeleteConsole() {
        ConsoleViewModel cvm = new ConsoleViewModel();
        cvm.setModel("PS4");
        cvm.setManufacturer("Sony");
        cvm.setMemoryAmount("124MB");
        cvm.setProcessor("Intel4");
        cvm.setPrice(new BigDecimal("100.00").setScale(2));
        cvm.setQuantity(99);
        int id = cvm.getConsoleId();
        cvm = gameStoreService.addConsole(cvm);

        ConsoleViewModel cvm2 = new ConsoleViewModel();
        cvm2.setConsoleId(2);
        cvm2.setModel("Switch");
        cvm2.setManufacturer("nintendo");
        cvm2.setMemoryAmount("15MB");
        cvm2.setProcessor("Intel1");
        cvm2.setPrice(new BigDecimal("50.9").setScale(2));
        cvm2.setQuantity(9);
        gameStoreService.updateConsole(cvm2);

        // update
        Console console2 = new Console();
        console2.setConsoleId(2);
        console2.setModel("Switch");
        console2.setManufacturer("nintendo");
        console2.setMemoryAmount("15MB");
        console2.setProcessor("Intel1");
        console2.setPrice(new BigDecimal("50.9").setScale(2));
        console2.setQuantity(9);

        Mockito.verify(consoleDao).updateConsole(console2);

        //delete
        gameStoreService.deleteConsole(cvm.getConsoleId());
        Mockito.verify(consoleDao).deleteConsole(2);

    }

    // invoice
    @Test
    public void testUpdateDeleteInvoice(){
        PurchaseItemViewModel ivm = new PurchaseItemViewModel();
        ivm.setName("Simon");
        ivm.setStreet("Street1");
        ivm.setCity("City1");
        ivm.setState("NC");
        ivm.setZipCode("12345");
        ivm.setItemType("game");
        ivm.setItemId(4);
        ivm.setQuantity(1);
        ivm = gameStoreService.addInvoice(ivm);

        PurchaseItemViewModel ivm2 = new PurchaseItemViewModel();
        ivm2.setInvoiceId(99);
        ivm2.setName("Simon");
        ivm2.setStreet("Street1");
        ivm2.setCity("City1");
        ivm2.setState("SC");
        ivm2.setZipCode("12345");
        ivm2.setItemType("console");
        ivm2.setItemId(2);
        ivm2.setQuantity(2);
        gameStoreService.updateInvoice(ivm2);

        Invoice invoice2 = new Invoice();
        invoice2.setInvoiceId(99);
        invoice2.setName("Simon");
        invoice2.setStreet("Street1");
        invoice2.setCity("City1");
        invoice2.setState("SC");
        invoice2.setZipCode("12345");
        invoice2.setItemType("console");
        invoice2.setItemId(2);
        invoice2.setUnitPrice(new BigDecimal("100.00"));
        invoice2.setQuantity(2);
        invoice2.setSubtotal(new BigDecimal("200.00"));
        invoice2.setTax(new BigDecimal("12.00"));
        invoice2.setProcessFee(new BigDecimal("14.99"));
        invoice2.setTotal(new BigDecimal("226.99"));

        Mockito.verify(invoiceDao).updateInvoice(invoice2);

    }

}
