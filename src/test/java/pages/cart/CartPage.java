package pages.cart;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class CartPage {
public final SelenideElement productInCart = $x("//div[@class = 'inventory_item_name']");
public final SelenideElement productInCartQuantity = $x("//div[@class = 'cart_quantity']");
public final SelenideElement cartButton = $x("//a[@class = 'shopping_cart_link']");
public final SelenideElement removeButtonInCart = $x("//button[@class = 'btn btn_secondary btn_small cart_button']");
public final SelenideElement cartPagePrice = $x("//div[@class='inventory_item_price']");

@Step("Click remove button to remove the product from cart")
public void removeFromCart(){

    removeButtonInCart.click();
}



}

