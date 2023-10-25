package dat;


import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;



class UserRessourceTest {

    private String BASE_URL = "http://localhost:7777";

    @BeforeAll
    static void setUpAll() {
        UserRessource.spinUpServerAndRoutes(7777);

    }

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void tearDown() {


    }


    @Test
    public void getAll() {
        given().header("Authorization", "login token").when().get(BASE_URL + "/api/users/").then().body("size()", is(2));


    }

    @Test
    void getAllCheckFirstPerson() {
        given().header("Authorization", "login token").when().get(BASE_URL + "/api/users").prettyPeek().then().body("user1.name", is("Morten"));

    }

    @Test
    void getAllCheckFirstPersonWrongToken() {
        given().header("Authorization", "wrong token").when().get(BASE_URL + "/api/users").then().statusCode(401).log().all();





    }
    @Test
    public void testCreateUser() {

        String userName = "JohnDoe";
        UserRessource.CreateUserRequest createUserRequest = new UserRessource.CreateUserRequest();
        createUserRequest.setName(userName);

        given().header("Authorization", "login token").body(createUserRequest).when().post(BASE_URL + "/api/users/name").then().statusCode(201);


    }

    @Test
    public void testCreateUserWrongToken() {

        String userName = "JohnDoe";
        UserRessource.CreateUserRequest createUserRequest = new UserRessource.CreateUserRequest();
        createUserRequest.setName(userName);

        given().header("Authorization", "wrong token key idiot").body(createUserRequest)
                .when()
                .post(BASE_URL + "/api/users/name")
                .then()
                .statusCode(401);


    }


}


