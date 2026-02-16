package ch.tranchida.sample.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.anyOf;

@QuarkusTest
class BillResourceTest {

    @Test
    void testBillsEndpoint() {
        given()
          .when().get("/api/bills")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("", hasSize(5))
             .body("[0].id", is(1))
             .body("[0].description", is("Bill 1"))
             .body("[0].amount", anyOf(is(10), is(10.0)))
             .body("[4].id", is(5))
             .body("[4].description", is("Bill 5"));
    }
}

