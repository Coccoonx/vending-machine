package com.challenge.vendingmachine;


import com.challenge.vendingmachine.model.dto.AuthRequest;
import com.challenge.vendingmachine.model.dto.UserRegistration;
import com.challenge.vendingmachine.repository.*;
import com.challenge.vendingmachine.service.UserSessionService;
import com.challenge.vendingmachine.service.VMUserDetailsService;
import com.challenge.vendingmachine.service.impl.BuyerServiceImpl;
import com.challenge.vendingmachine.service.impl.ProductServiceImpl;
import com.challenge.vendingmachine.service.impl.PurchaseServiceImpl;
import com.challenge.vendingmachine.service.impl.UserServiceImpl;
import com.challenge.vendingmachine.utils.mapper.ProductMapper;
import com.challenge.vendingmachine.utils.mapper.PurchaseMapper;
import com.challenge.vendingmachine.utils.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
public class TestBase {
    // repositories
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected PurchaseRepository purchaseRepository;
    @Autowired
    protected UserSessionRepository userSessionRepository;
    @Autowired
    protected RoleRepository roleRepository;

    // services
    @Autowired
    protected UserServiceImpl userService;
    @Autowired
    protected ProductServiceImpl productService;
    @Autowired
    protected PurchaseServiceImpl purchaseService;
    @Autowired
    protected UserSessionService userSessionService;
    @Autowired
    protected BuyerServiceImpl buyerService;
    @Autowired
    protected VMUserDetailsService vmUserDetailsService;

    // mapper
    @Autowired
    protected ProductMapper productMapper;
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected PurchaseMapper purchaseMapper;
    @Autowired
    protected ObjectMapper objectMapper;

    protected RequestSpecification preLoadedGivenSeller;
    protected RequestSpecification preLoadedGivenBuyer;

    @LocalServerPort
    protected int port;
    protected String tokenBuyer, tokenSeller;


    public void setup() throws Exception {
        RestAssured.port = port;

        this.tokenBuyer = authenticateDefaultBuyer();
        this.tokenSeller = authenticateDefaultSeller();

        // a given() function pre-loaded with token
        preLoadedGivenSeller = given().header("Authorization", String.format("Bearer %s", this.tokenSeller));
        preLoadedGivenBuyer = given().header("Authorization", String.format("Bearer %s", this.tokenBuyer));
    }

    public String authenticateDefaultSeller() throws JsonProcessingException {

        UserRegistration userRegistrationSeller = buildUserSeller();

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userRegistrationSeller))
                .log()
                .body()
                .post("/users")
                .then()
                .log()
                .body()
                .statusCode(200);

        AuthRequest authRequest = buildAuthRequest(userRegistrationSeller);
        return given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(authRequest))
                .log()
                .body()
                .post("/auth/login")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .body()
                .path("token");
    }

    public String authenticateDefaultBuyer() throws JsonProcessingException {

        UserRegistration userRegistrationBuyer = buildUserBuyer();

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(userRegistrationBuyer))
                .log()
                .body()
                .post("/users")
                .then()
                .log()
                .body()
                .statusCode(200);

        AuthRequest authRequest = buildAuthRequest(userRegistrationBuyer);
        return given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(authRequest))
                .log()
                .body()
                .post("/auth/login")
                .then()
                .log()
                .body()
                .statusCode(200)
                .extract()
                .body()
                .path("token");
    }

    private AuthRequest buildAuthRequest(UserRegistration userRegistration) {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(userRegistration.getUsername());
        authRequest.setPassword(userRegistration.getPassword());
        return authRequest;
    }

    public UserRegistration buildUserSeller() {
        return this.buildUser("Jimmy", "2022", "SELLER");
    }

    public UserRegistration buildUserBuyer() {
        return this.buildUser("John", "2024", "BUYER");
    }

    public UserRegistration buildUser(String username, String password, String ...roles) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setUsername(username);
        userRegistration.setPassword(password);
        userRegistration.setRoles(new HashSet<>(Arrays.asList(roles)));

        return userRegistration;
    }


}
