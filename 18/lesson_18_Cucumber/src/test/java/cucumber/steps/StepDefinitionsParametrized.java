package cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class StepDefinitionsParametrized {

    @Given("We have user id {string} in system")
    public void weHaveUserIdInSystem(String id) {
        System.out.println("We have user " + id + " in DB");
    }

    @When("Getting information about user {string} with name {string} and surname {string}")
    public void gettingInformationAboutUserWithNameAndSurname(String id, String name, String surname) {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://reqres.in/api/users/" + id)
                .then()
                .extract().response();

        System.out.println(response.getBody().prettyPrint());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(name, response.jsonPath().getString("data.first_name"));
        Assertions.assertEquals(surname, response.jsonPath().getString("data.last_name"));
    }

    @Then("DB saved the information about request for user id {string}")
    public void dbSavedTheInformationAboutRequestForUserId(String id) {
        System.out.println("Information about activity for user " + id + " added");
    }
}
