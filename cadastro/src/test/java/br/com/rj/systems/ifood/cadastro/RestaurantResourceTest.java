package br.com.rj.systems.ifood.cadastro;

import br.com.rj.systems.ifood.cadastro.model.Restaurant;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.approvaltests.JsonApprovals;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(RegisterTestLifecycleManager.class)
class RestaurantResourceTest {

    @Test
    @DataSet("restaurants-scenario-1.yml")
    void testFindRestaurants() {
        var result = RestAssured.given()
                .when().get("/restaurants")
                .then()
                .statusCode(200)
                .extract().asString();

        JsonApprovals.verifyJson(result);
    }

    @Test
    @DataSet("restaurants-scenario-1.yml")
    void testUpdateRestaurant() {
        var dto = new Restaurant();
        dto.name = "newName";
        var parameterValue = 123L;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .with().pathParam("id", parameterValue)
                .body(dto)
                .when().put("/restaurants/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode())
                .extract().asString();

        Restaurant findById = Restaurant.findById(parameterValue);

        Assertions.assertEquals(dto.name, findById.name);
    }
}