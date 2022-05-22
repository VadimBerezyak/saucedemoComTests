package pages.products;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {
    public final SelenideElement pageTitle = $x("//span[@class='title']");
    public final SelenideElement cartButton = $x("//div[@id='shopping_cart_container']");
    public final SelenideElement problemPicture = $x("//img[@class = 'inventory_item_img']");
    public final SelenideElement backPackAddToCartButton = $x("//button[@name = 'add-to-cart-sauce-labs-backpack']");
    public final SelenideElement indicatorAddToCart = $x("//a[@class = 'shopping_cart_link']//span[@class = 'shopping_cart_badge']");
    public final SelenideElement removeButton = $x("//button[@data-test = 'remove-sauce-labs-backpack']");
    public void addToCar() {
        backPackAddToCartButton.click();
    }
    public void removerProduct() {
        removeButton.click();
    }
    public void goToCartPage() {
        cartButton.click();
    }
}
