package cs.behaviors;

import com.consol.citrus.dsl.runner.AbstractTestBehavior;
import com.consol.citrus.message.MessageType;
import cs.pojo.CreateUserResponse;
import org.springframework.http.HttpStatus;

public class CreateUserBehavior extends AbstractTestBehavior {
    public String name;
    public String job;

    public CreateUserBehavior(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @Override
    public void apply() {
        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .send()
                .post("api/users/")
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
        );
        echo("Behavior DONE! Created user with: currentId = ${currentId} and createdAt = ${createdAt}");
    }

    public CreateUserResponse getResponseData(String name, String job) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(name);
        createUserResponse.setJob(job);
        createUserResponse.setId("@isNumber()@");
        createUserResponse.setCreatedAt("@ignore()@");

        return createUserResponse;
    }
}

