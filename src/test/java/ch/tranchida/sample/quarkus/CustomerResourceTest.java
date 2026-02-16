package ch.tranchida.sample.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class CustomerResourceTest {
    @Test
    void testCustomersEndpoint() {
        given()
          .when().get("/api/customers")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("", hasSize(10))
             .body("[0].id", is(1))
             .body("[0].name", is("Customer 1"))
             .body("[9].id", is(10))
             .body("[9].name", is("Customer 10"));
    }

}

