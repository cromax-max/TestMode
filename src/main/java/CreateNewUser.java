import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class CreateNewUser {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static RegistrationDto setUpAll(String locale, String status) {
        RegistrationDto user = generateUser(locale, status);
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    private static RegistrationDto generateUser(String locale, String status) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationDto(
                faker.name().firstName(),
                faker.funnyName().toString(),
                status);
    }


}