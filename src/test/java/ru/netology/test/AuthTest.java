package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import ru.netology.data.CreateNewUser;
import ru.netology.data.DataGenerate;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    static RegistrationDto activeUser;
    static RegistrationDto blockedUser;

    @BeforeAll
    static void createUser() {
        activeUser = CreateNewUser.createUser( "active");
        blockedUser = CreateNewUser.createUser("blocked");
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
        $(By.xpath("//input[@name='password']")).val(DataGenerate.generatePassword());
        $(By.xpath("//span[text()='Продолжить']")).click();
        $(By.xpath("//div[@class='notification__content']"))
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldBeActiveUserAndInvalidLogin() {
        $(By.xpath("//input[@name='login']")).val(DataGenerate.generateLogin());
        $(By.xpath("//input[@name='password']")).val(activeUser.getPassword());
        $(By.xpath("//span[text()='Продолжить']")).click();
        $(By.xpath("//div[@class='notification__content']"))
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
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
