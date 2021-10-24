import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    static RegistrationDto activeUser;
    static RegistrationDto blockedUser;
    static String invalidValue;

    @BeforeAll
    static void createUser() {
        activeUser = CreateNewUser.setUpAll("ru", "active");
        blockedUser = CreateNewUser.setUpAll("fi-FI", "blocked");
        invalidValue = "SuperUser";
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @AfterEach
    void clearBrowser() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldBeActiveUserAndValidData() {
        $(By.xpath("//input[@name='login']")).val(activeUser.getLogin());
        $(By.xpath("//input[@name='password']")).val(activeUser.getPassword());
        $(By.xpath("//span[text()='Продолжить']")).click();
        $("h2.heading")
                .shouldBe(visible)
                .shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldBeActiveUserAndInvalidPassword() {
        $(By.xpath("//input[@name='login']")).val(activeUser.getLogin());
        $(By.xpath("//input[@name='password']")).val(invalidValue);
        $(By.xpath("//span[text()='Продолжить']")).click();
        $(By.xpath("//div[@class='notification__content']"))
                .shouldBe(visible)
                .shouldHave(text("Ошибка!"));
    }

    @Test
    void shouldBeActiveUserAndInvalidLogin() {
        $(By.xpath("//input[@name='login']")).val(invalidValue);
        $(By.xpath("//input[@name='password']")).val(activeUser.getPassword());
        $(By.xpath("//span[text()='Продолжить']")).click();
        $(By.xpath("//div[@class='notification__content']"))
                .shouldBe(visible)
                .shouldHave(text("Ошибка!"));
    }

    @Test
    void shouldBeBlockedUser() {
        $(By.xpath("//input[@name='login']")).val(blockedUser.getLogin());
        $(By.xpath("//input[@name='password']")).val(blockedUser.getPassword());
        $(By.xpath("//span[text()='Продолжить']")).click();
        $(By.xpath("//div[@class='notification__content']"))
                .shouldBe(visible)
                .shouldHave(text("Пользователь заблокирован"));
    }
}
