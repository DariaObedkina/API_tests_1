package com.dobedkina;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests {

    private static final String BASE_URL = "https://reqres.in";

    @Test
    void getSingleUserCheckEmail() {
        given()
                .when()
                .get(BASE_URL + "/api/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    void getResourcesCheckTotal() {
        Response response = given()
                .when()
                .get(BASE_URL + "/api/unknown")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.path("total").equals(12));
    }

    @Test
    void createUserCheckJob() {
        given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post(BASE_URL + "/api/users")
                .then()
                .statusCode(200)
                .body("job", is("leader"));
    }

    @Test
    void updateUserCheckName() {
        given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put(BASE_URL + "/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"));
    }

    @Test
    void successfulregister() {
        Response response = given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .when()
                .post(BASE_URL + "/api/register")
                .then()
                .statusCode(200)
                .extract().response();
        assertEquals("{\"id\":4,\"token\":\"QpwL5tke4Pnpja7X4\"}", response.asString());

    }
}

