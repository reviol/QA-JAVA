package cs.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.CitrusParameters;
import cs.pojo.CreateUserResponse;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestDataProviderCreateUser extends TestNGCitrusTestRunner {

    private TestContext context;

    @DataProvider(name = "dataProvider")
    public Object[][] cardTypeProvider() {
        return new Object[][]{
                new Object[]{"George", "Driver"},
                new Object[]{"Nick", "Teacher"},
                new Object[]{"Anna", "Tester"},
                new Object[]{"Mike", "Actor"},
                new Object[]{"Liana", "Assistant"},
                new Object[]{"Peter", "Chef"},
                new Object[]{"Mary", "Conductor"},
                new Object[]{"Alex", "Controller"},
                new Object[]{"Ariel", "Decorator"},
                new Object[]{"Greg", "Fixer"},
        };
    }

    @Test(description = "Получение информации о пользователе", dataProvider = "dataProvider" )
    @Parameters({"context"})
    @CitrusTest
    @CitrusParameters({"name", "job"})
    public void getTestActions(String name, String job) {
        this.context = citrus.createTestContext();
        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .send()
                .post("users")
                .payload("{\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}")
        );

        http(httpActionBuilder -> httpActionBuilder
                        .client("restClientReqres")
                        .receive()
                        .response(HttpStatus.CREATED)
                        .messageType(MessageType.JSON)
                        .payload(getResponseData(name, job), "objectMapper")
                        .extractFromPayload("$.id", "currentId")
                        .extractFromPayload("$.createdAt", "createdAt")
                        .ignore("$.createdAt")
//                        .ignore("$.id")
        );
        echo("currentId = ${currentId} and createdAt = ${createdAt}");
    }

    public CreateUserResponse getResponseData(String name, String job) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(name);
        createUserResponse.setJob(job);
        createUserResponse.setId("@isNumber()@");
        createUserResponse.setCreatedAt("unknown");

        return createUserResponse;
    }

}
