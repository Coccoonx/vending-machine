package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.TestBase;
import com.challenge.vendingmachine.model.Product;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductControllerTest extends TestBase {

    @Before
    public void init() throws Exception {
        setup();
    }

    @Test
    public void testCreateSeller_Success() throws Exception {
        Product product = createProduct("Banana", 30, 10);

        ValidatableResponse response = preLoadedGivenSeller.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(product))
                .log()
                .body()
                .post("/products")
                .then()
                .log()
                .body()
                .statusCode(200);

        assertThat(response.extract().jsonPath().get("productName"), equalTo("Banana"));
    }

    @Test
    public void testCreateSellerInvalidCost_Fail() throws Exception {
        Product product = createProduct("Banana", 22, 10);

        preLoadedGivenSeller.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(product))
                .log()
                .body()
                .post("/products")
                .then()
                .log()
                .body()
                .statusCode(400);
    }

    @Test
    public void testCreateBuyer_Fail() throws Exception {
        Product product = createProduct("Banana", 30, 10);

        preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(product))
                .log()
                .body()
                .post("/products")
                .then()
                .log()
                .body()
                .statusCode(403);
    }


    private void createSampleProducts() {
        Product one = createProduct("Orange", 20, 10);
        Product two = createProduct("Pineapple", 25, 15);
        Product three = createProduct("Apple", 5, 12);

        productService.create(one);
        productService.create(two);
        productService.create(three);
    }

    private Product createProduct(String productName, long cost, double amountAvailable) {
        Product product = new Product();
        product.setProductName(productName);
        product.setCost(cost);
        product.setAmountAvailable(amountAvailable);
        return product;
    }


    @Test
    public void testGetAll_Without_Auth_Success() {
        createSampleProducts();

        ValidatableResponse response = given()
                .get("/products")
                .then()
                .log()
                .body()
                .statusCode(200);

        assertThat(response.extract().jsonPath().getList("$").size(), equalTo(3));
    }

    @Test
    public void testUpdate_Without_Auth_Fail() throws Exception {
        createSampleProducts();
        Product productUpdate = createProduct("Coconuts", 20, 10);

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(productUpdate))
                .put("/products/1L")
                .then()
                .log()
                .body()
                .statusCode(401);

    }


    @Test
    public void testUpdate_With_Auth_Success() throws Exception {
        createSampleProducts();
        Product productUpdate = createProduct("Coconuts", 20, 10);

        ValidatableResponse response = preLoadedGivenSeller
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(productUpdate))
                .put("/products/1")
                .then()
                .log()
                .body()
                .statusCode(200);

        assertThat(response.extract().jsonPath().get("productName"), equalTo("Coconuts"));
    }


    @Test
    public void testDelete_With_Auth_Success() {
        createSampleProducts();

        preLoadedGivenSeller
                .delete("/products/1")
                .then()
                .log()
                .body()
                .statusCode(200);

        ValidatableResponse response  = given()
                .get("/products")
                .then()
                .log()
                .body()
                .statusCode(200);

        assertThat(response.extract().jsonPath().getList("$").size(), equalTo(2));
    }
}
