package com.chath;

import com.google.common.collect.ImmutableList;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Test
public class QueryGooglePlacesTest {

    public void test() {

        RestAssured.baseURI = GooglePlacesConstants.BASE_URI;

        Response response =
                given().param("key", GooglePlacesConstants.API_KEY)
                        .param("location", "-33.8670522,151.1957362")
                        .param("radius", "1")
                        .when()
                        .get("/maps/api/place/nearbysearch/json");

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("OK"))
                .body("results.size", equalTo(2))
                ;

        //doing assertions in a different way
        JsonPath jsonPath = new JsonPath(response.asInputStream());

        assertThat(jsonPath.getString("status")).isEqualTo("OK");

        assertThat(jsonPath.getList("results.name"))
                .containsAll(ImmutableList.of("Sydney", "Pyrmont"));
    }
}
