package starter.petstore;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class CreateAndFetchPetTest {
    Long newPetId = null;
    PetApiActions petApi;


    @Test
    public void fetchAlreadyAvailablePet() {
        newPetId = petApi.givenKittyIsAvailableInPetStore();
        petApi.whenIAskForAPetWithId(newPetId);
        petApi.thenISeeKittyAsResult();
    }

    @Test
    public void fetchPetsByStatus() {
        String status = "available";
        petApi.givenPetsArePresentInPetStoreByNameAndStatus("Tiger", status);
        petApi.whenIAskForPetsByStatus(status);
        petApi.thenISeeResultFor(status);
    }

    @Test
    public void verifyPetsAreNotReturnedForIncorrectStatus() {
        petApi.givenPetsArePresentInPetStoreByNameAndStatus("Lion", "Pending");
        petApi.whenIAskForPetsByStatus("sold");
        petApi.thenISeeNoneResultFor("sold");
    }
}