package pages.products;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {
    public final SelenideElement pageTitle = $x("//span[@class='title']");
    public final SelenideElement cartButton = $x("//div[@id='shopping_cart_container']");
    public final SelenideElement problemPicture = $x("//img[@class = 'inventory_item_img']");
    public final SelenideElement backPackAddToCartButton = $x("//button[@name = 'add-to-cart-sauce-labs-backpack']");
    public final SelenideElement indicatorAddToCart = $x("//a[@class = 'shopping_cart_link']//span[@class = 'shopping_cart_badge']");
    public final SelenideElement removeButton = $x("//button[@data-test = 'remove-sauce-labs-backpack']");
    public final SelenideElement productPageTitle = $x("//div[@class='inventory_item_name']");
    public final SelenideElement productPagePrice = $x("//div[@class='inventory_item_price']");
    @Step("Add product in the cart")
    public void addToCar() {
        backPackAddToCartButton.click();
    }

    @Step("Remove product from the cart")
    public void removerProduct() {

        removeButton.click();
    }

    @Step("Go to the cart page")
    public void goToCartPage() {
        cartButton.click();
    }
}
