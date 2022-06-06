package pages.login;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.files.DownloadActions.click;

public class LoginPage {
    public final SelenideElement inputUserName = $x("//input[@id = 'user-name']");
    public final SelenideElement inputUserPassword = $x("//input[@id = 'password']");
    public final SelenideElement loginButton = $x("//input[@id = 'login-button']");
    public final SelenideElement errorButtonClose = $x("//button[@class = 'error-button']");
    public final SelenideElement errorMessageContainer = $x("//div[@class = 'error-message-container error']");

    @Step("Set user data and click login button")
    public void loginUser(String login, String password){
        inputUserName.setValue(login);
        inputUserPassword.setValue(password);
        loginButton.click();
    }


}
