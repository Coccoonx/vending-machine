package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.TestBase;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.dto.BuyRequest;
import com.challenge.vendingmachine.model.dto.Coin;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BuyControllerTest extends TestBase {

    @Before
    public void init() throws Exception {
        setup();
    }

    @Test
    public void testDepositBuyerInvalidCoin_Fail() throws Exception {
        Coin coin = new Coin(15);
       preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(coin))
                .log()
                .body()
                .post("/deposit")
                .then()
                .log()
                .body()
                .statusCode(400);
    }


    @Test
    public void testDepositBuyer_Success() throws Exception {
        Coin coin = new Coin(20);
        ValidatableResponse response = preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(coin))
                .log()
                .body()
                .post("/deposit")
                .then()
                .log()
                .body()
                .statusCode(200);

        assertThat(response.extract().jsonPath().get("deposit"), not(equalTo(0)));
    }

    @Test
    public void testDepositSeller_Fail() throws Exception {
        Coin coin = new Coin(20);
        preLoadedGivenSeller.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(coin))
                .log()
                .body()
                .post("/deposit")
                .then()
                .log()
                .body()
                .statusCode(403);
    }

    @Test
    public void testBuyBuyerInsufficientCoin_Fail() throws Exception {
        Coin coin = new Coin(20);
        preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(coin))
                .log()
                .body()
                .post("/deposit")
                .then()
                .log()
                .body()
                .statusCode(200);

        createSampleProducts();

        BuyRequest buyRequest = new BuyRequest(1L, 4);

        preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(buyRequest))
                .log()
                .body()
                .post("/buy")
                .then()
                .log()
                .body()
                .statusCode(400);
    }

    @Test
    public void testBuyBuyerInvalidQuantity_Fail() throws Exception {
        Coin coin = new Coin(100);
        preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(coin))
                .log()
                .body()
                .post("/deposit")
                .then()
                .log()
                .body()
                .statusCode(200);

        createSampleProducts();

        BuyRequest buyRequest = new BuyRequest(3L, 20);

        preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(buyRequest))
                .log()
                .body()
                .post("/buy")
                .then()
                .log()
                .body()
                .statusCode(400);
    }

    @Test
    public void testBuyBuyer_Success() throws Exception {
        Coin coin = new Coin(20);
        preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(coin))
                .log()
                .body()
                .post("/deposit")
                .then()
                .log()
                .body()
                .statusCode(200);

        createSampleProducts();

        BuyRequest buyRequest = new BuyRequest(3L, 3);

        ValidatableResponse response = preLoadedGivenBuyer.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(buyRequest))
                .log()
                .body()
                .post("/buy")
                .then()
                .log()
                .body()
                .statusCode(200);

        assertThat(response.extract().jsonPath().get("changes"), contains(5));
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


}
