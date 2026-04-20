package app;

import controller.FilterManager;
import controller.MainController;
import controller.UIHandler;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.cart.Cart;
import model.fruitinfo.DiscountFruit;
import model.fruitinfo.Fruit;
import model.fruitinfo.FruitFactory;
import model.fruitinfo.FruitPrice;
import model.fruitinfo.PrijzenEnDealsFactory;
import model.vestigingen.ProvincieVestigingen;
import model.vestigingen.VestigingenFactory;
import view.BasketView;
import view.CartView;
import view.CheckoutView;
import view.FruitCard;
import view.GridView;
import view.MenuView;
import view.SortingView;
import view.ViewUtils;

public class AppFactory {
    private FruitFactory fruitFactory;
    private List<Fruit> fruits;

    private VestigingenFactory vestigingenFactory;
    private List<ProvincieVestigingen> provincies;
    private static final String HARDCODE_LOCATION = "Assen";

    private PrijzenEnDealsFactory prijzenFactory;
    private final ViewUtils viewUtils;
    private AppManager appManager;

    public AppFactory() {
        // Parse fruit info
        String fruitFilePath = "./src/resources/data/vruchtenlijst.json";
        this.fruitFactory = new FruitFactory(fruitFilePath);
        this.fruits = fruitFactory.parseFruits();

        // Parse vestigingen
        String vestigingenFilePath = "./src/resources/data/vestigingen.json";
        this.vestigingenFactory = new VestigingenFactory(vestigingenFilePath);
        this.provincies = vestigingenFactory.parseVestigingen();

        // Parse prijzen en deals
        String prijzenFilePath = "./src/resources/data/prijzenendeals.json";
        this.prijzenFactory = new PrijzenEnDealsFactory(prijzenFilePath);
        Map<String, FruitPrice> prijzenMap = prijzenFactory.parsePrijzen();

// Merge prices and deals into fruit objects
for (int i = 0; i < fruits.size(); i++) {
    Fruit f = fruits.get(i);
    FruitPrice fp = prijzenMap.get(f.getFruitName());

    if (fp != null) {
        if (fp.getDeal() != null) {
            Fruit discountFruit = new DiscountFruit(
                    f.getFruitName(),
                    fp.getPrice(),
                    fp.getWeight(),
                    f.getStory(),
                    f.getImagePath(),
                    fp.getDeal()
            );

            discountFruit.setOrigin(f.getOrigin());
            fruits.set(i, discountFruit);
        } else {
            f.setPrice(fp.getPrice());
            f.setWeight(fp.getWeight());
        }
    }
}

        this.viewUtils = new ViewUtils();
    }

    public void initUI(Stage stage) {
        Cart cart = new Cart();

        UIHandler uiHandler = new UIHandler(cart);
        uiHandler.setVestigingenFactory(vestigingenFactory);


        ObservableList<FruitCard> fruitCards = FXCollections.observableArrayList();
        for (Fruit fruit : fruits) {
            FruitCard card = new FruitCard(fruit, cart, uiHandler, viewUtils);
            fruitCards.add(card);
        }

        FilterManager filterManager = new FilterManager(fruitCards, provincies);

        // Hardcode location for now
        filterManager.setActiveVestigingByName(HARDCODE_LOCATION);

        for (FruitCard card : fruitCards) {
            boolean available = filterManager.isAvailable(card.getFruit());
            card.setAvailability(available);
        }

        GridView gridView = new GridView(filterManager.getSortedList());
        CartView cartView = new CartView(cart, uiHandler, viewUtils);
        SortingView sortingView = new SortingView(filterManager);
        MenuView menuView = new MenuView();

        BasketView basketView = new BasketView(fruits, uiHandler);
        CheckoutView checkoutView = new CheckoutView(uiHandler, cart, provincies);

        MainController controller = new MainController(gridView, cartView,sortingView, menuView, basketView, cart, uiHandler, provincies);
        controller.setCheckoutView(checkoutView);
        uiHandler.setMainController(controller);

        appManager = new AppManager(controller);
        controller.setAppManager(appManager);
        appManager.start(stage);
    }

    public AppManager getAppManager() {
        return appManager;
    }
}