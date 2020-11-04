package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class GreetingTest
{
    // NOTE: RestAssured is aware of the application.properties quarkus.http.root-path switch

    @Test
    public void test_NoAccess() {
        RestAssured.when().get("/hello").then()
                .statusCode(403)
                .contentType("text/plain")
                .body(equalTo("No access"));
    }
}
