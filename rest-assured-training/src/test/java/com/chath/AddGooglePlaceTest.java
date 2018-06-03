package com.chath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public class AddGooglePlaceTest {

    public void addGooglePlaceApiTest() {

        RestAssured.baseURI = GooglePlacesConstants.BASE_URI;

        final File requstBody = new File("place-add.json");
        System.out.println(requstBody.getAbsolutePath());
        assertThat(requstBody.exists()).isTrue();

        Response response =
                given().queryParam("key", GooglePlacesConstants.API_KEY)
                        .body(requstBody)
                        .post("/maps/api/place/add/json");

        response.then().statusCode(200)
                .contentType(ContentType.JSON);
    }
}
