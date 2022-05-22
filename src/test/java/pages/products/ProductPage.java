package pages.products;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {
    public final SelenideElement pageTitle = $x("//span[@class='title']");
    public final SelenideElement cartButton = $x("//div[@id='shopping_cart_container']");
    public final SelenideElement problemPicture = $x("//img[@class = 'inventory_item_img']");
}
