package ru.netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerate {
    private DataGenerate() {
    }

    public static String generateLogin() {
        return new Faker(new Locale("ru")).name().firstName();
    }

    public static String generatePassword() {
        return new Faker(new Locale("ru")).funnyName().toString();
    }


    public static RegistrationDto generateUser(String status) {
        return new RegistrationDto(generateLogin(), generatePassword(), status);
    }
}

