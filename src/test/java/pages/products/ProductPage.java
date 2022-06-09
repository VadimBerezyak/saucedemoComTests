package pages.products;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class ProductPage {
    public final SelenideElement pageTitle = $x("//span[@class='title']");
    public final SelenideElement cartButton = $x("//div[@id='shopping_cart_container']");
    public final SelenideElement problemPicture = $x("//img[@class = 'inventory_item_img']");
    public final SelenideElement indicatorAddToCart = $x("//a[@class = 'shopping_cart_link']//span[@class = 'shopping_cart_badge']");
    public final SelenideElement productPageTitle = $x("//div[@class='inventory_item_name']");
    public final SelenideElement productPagePrice = $x("//div[@class='inventory_item_price']");


    ElementsCollection productsInCatalog = $$x("//div[@class='inventory_item_description']");

    @Step("Add product in the cart")
    public void addToCartByIndex(int index) {
        (productsInCatalog.get(index)).$x(".//button[@class='btn btn_primary btn_small btn_inventory']").click();
    }

    @Step("Get name of the product in cart by index")
    public String getProductNameInCartByIndex (int index){
        return productsInCatalog.get(index).$x(".//div[@class='inventory_item_name']").getText();
    }

    @Step("Remove product from the cart")
    public SelenideElement getRemoveButtonByIndex(int index) {
       // (productsInCatalog.get(index)).$x(".//button[contains(text(),'Remove')]").click();
        return (productsInCatalog.get(index)).$x(".//button[contains(text(),'Remove')]");
    }

    @Step("Go to the cart page")
    public void goToCartPage() {
        cartButton.click();
    }
}
