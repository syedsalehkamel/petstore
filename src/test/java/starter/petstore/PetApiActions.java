package starter.petstore;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.SerenityRest.when;
import static net.serenitybdd.rest.SerenityRest.given;

import net.serenitybdd.core.steps.UIInteractions;
import org.hamcrest.Matchers;

import static net.serenitybdd.rest.SerenityRest.*;

public class PetApiActions extends UIInteractions {
    @Given("Kitty is available in the pet store")
    public Long givenKittyIsAvailableInPetStore() {
        Pet pet = new Pet("Kitty", "available");
        Long newId = given().baseUri("https://petstore.swagger.io").basePath("/v2/pet").body(pet, ObjectMapperType.GSON).accept(ContentType.JSON).contentType(ContentType.JSON).post().getBody().as(Pet.class, ObjectMapperType.GSON).getId();
        return newId;
    }

    @When("I ask for a pet using Kitty's ID: {0}")
    public void whenIAskForAPetWithId(Long id) {
        when().get("/" + id);
    }

    @Then("I get Kitty as result")
    public void thenISeeKittyAsResult() {
        then().body("name", Matchers.equalTo("Kitty"));
    }

    @Given("Pet with '{0}' status is present in the pet store")
    public void givenPetsArePresentInPetStoreByNameAndStatus(String name, String status) {
        Pet pet = new Pet(name, status);
        given().baseUri("https://petstore.swagger.io").basePath("/v2/pet").body(pet, ObjectMapperType.GSON).accept(ContentType.JSON).contentType(ContentType.JSON).post();
    }
    @When("I ask for pets using Status: {0}")
    public void whenIAskForPetsByStatus(String status) {
        when().get("/" + status);
    }

    @Then("I get pets for status as '{0}'")
    public void thenISeeResultFor(String status) {
        then().body("status", Matchers.equalTo(status));
    }

    @Then("I get none pets for status as '{0}'")
    public void thenISeeNoneResultFor(String status) {
        then().body("status", Matchers.hasSize(0));
    }
}