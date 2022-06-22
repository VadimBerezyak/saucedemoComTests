package ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Test;
import pages.cart.CartPage;

import static com.codeborne.selenide.Condition.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Owner("Vadim Berezyak")
@Severity(value = SeverityLevel.CRITICAL)
public class SmokeTests extends BaseTest {

    int index = (int) Math.abs((Math.random() * 10 - 4));

    @Test
    @Description("Checking the fact that product may be correctly added to the cart: assert names and prices values")
    void checkCorrectAddedProductInCartTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        step("Check that indicator is working", () -> {
            productPage.indicatorAddToCart.shouldHave(text("1"));
        });
        step("Check that remove-button appeared on page", () -> {
            productPage.getRemoveButtonByIndex(index).shouldBe(exist);
        });
        String item = productPage.getProductNameInCatalogByIndex(index);
        String itemPrice = productPage.getProductPriceInCatalogByIndex(index);
        productPage.goToCartPage();
        String inCartItem = cartPage.productInCart.getText();
        String inCartItemPrice = cartPage.cartPagePrice.getText();
        step("Name and price of product in cart are correct", () -> {
            assertThat(inCartItem).isEqualTo(item);
            assertThat(inCartItemPrice).isEqualTo(itemPrice);
        });
    }

    @Test
    @Owner("Vadim Berezyak")
    @Description("Checking of 'Remove from cart' button's functionality ")
    void removeButtonTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        step("Click remove button on product page", () -> {
            productPage.getRemoveButtonByIndex(index).click();
        });
        step("Check that remove button disappeared", () -> {
            productPage.getRemoveButtonByIndex(index).shouldBe(not(exist));
        });
    }

    @Test
    @Owner("Vadim Berezyak")
    @Description("Checking that the product in cart is the same that was chosen by user")
    void checkProductAddedToCartTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        step("Check that cart contains product", () -> {
            cartPage.productInCart.shouldBe(exist);
        });
        step("Check that name of product in cart is same that was added by user", () -> {
            cartPage.productInCart.getText().equals(productPage.productPageTitle.getText());
        });
        step("Check that name of product in cart is visible", () -> {
            cartPage.productInCart.shouldBe(visible);
        });
        step("Check that remove-button exist on cart page", () -> {
            cartPage.removeButtonInCart.shouldBe(exist);
        });
        step("Check that user added only one product", () -> {
            cartPage.productInCartQuantity.shouldHave(text("1"));
        });
        step("Check that price of product in cart is the same", () -> {
            productPage.productPagePrice.innerText().equals(cartPage.cartPagePrice.innerText());
        });
    }

    @Test
    @Description("Checking of 'remove from cart' button's on cart page functionality ")
    void removeButtonInCartTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        cartPage.removeFromCart();
        step("Check that product was removed from cart", () -> {
            cartPage.productInCart.shouldBe(not(exist));
        });
        step("Check remove-button disappeared", () -> {
            cartPage.removeButtonInCart.shouldBe(not(exist));
        });
    }

}
