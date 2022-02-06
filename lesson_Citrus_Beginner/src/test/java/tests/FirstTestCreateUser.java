package tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import pojo.CreateUserResponse;

public class FirstTestCreateUser extends TestNGCitrusTestRunner {

    private TestContext context;
    String name = "Nick";
    String job = "Teacher";

    @Test(description = "Создание пользователе")
    @CitrusTest
    public void getTestActions() {
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
//                        .ignore("$.id")ex
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
