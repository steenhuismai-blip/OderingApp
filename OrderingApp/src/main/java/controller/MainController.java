package controller;

import view.BasketView;
import java.util.List;

import app.AppManager;
import model.cart.Cart;
import view.CartView;
import view.GridView;
import view.MenuView;
import view.SortingView;
import view.CheckoutView;
import controller.UIHandler;
import model.vestigingen.ProvincieVestigingen;

public class MainController {
    
    private GridView gridView;
    private CartView cartView;
    private SortingView sortingView;
    private MenuView menuView;
    private BasketView basketView;
    private CheckoutView checkoutView;
    private AppManager appManager;
    private Cart cart;
    private UIHandler uiHandler;
    private List<ProvincieVestigingen> provincies;

    public MainController(
        GridView gridView, 
        CartView cartView, 
        SortingView sortingView, 
        MenuView menuView, 
        BasketView basketView,
        Cart cart, 
        UIHandler uiHandler, 
        List<ProvincieVestigingen> provincies
    ) {
        this.gridView = gridView;
        this.cartView = cartView;
        this.sortingView = sortingView;
        this.menuView = menuView;
        this.basketView = basketView;
        this.cart = cart;
        this.uiHandler = uiHandler;
        this.provincies = provincies;
    }

    public GridView getGridView() {
        return gridView;
    }

    public CartView getCartView() {
        return cartView;
    }

    public SortingView getSortingView() {
        return sortingView;
    }

    public MenuView getMenuView() {
        return menuView;
    }
    public void setCheckoutView(CheckoutView checkoutView) {
        this.checkoutView = checkoutView;
    }
    public CheckoutView getCheckoutView() {
        return checkoutView;
    }

    public void setAppManager(AppManager appManager) {
        this.appManager = appManager;
    }

    public void showCheckout() {
        if (appManager != null && checkoutView != null) {
            appManager.getRootLayout().setCenter(checkoutView);
        }
    }

public void showMainScreen() {
    if (appManager != null) {
        appManager.getRootLayout().setTop(appManager.getOriginalTop());
        appManager.getRootLayout().setCenter(appManager.getOriginalCenter());
        appManager.getRootLayout().setRight(null);
        appManager.getRootLayout().setLeft(null);
        appManager.getRootLayout().setBottom(null);
    }
}


    public void updateCheckoutView() {
        if (checkoutView != null)
        checkoutView.updateCartView(cart);
        }

    public void resetCheckout() {
        this.checkoutView= new CheckoutView(uiHandler, cart, provincies);
    }  

    public BasketView getBasketView() {
        return basketView;
    }
    
}
