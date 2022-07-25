package behaviors;

import com.consol.citrus.TestActionRunner;
import com.consol.citrus.TestBehavior;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import org.springframework.http.HttpStatus;
import pojo.CreateUserResponse;
import static com.consol.citrus.dsl.JsonSupport.json;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateUserBehavior implements TestBehavior {
    private TestContext context;
    public String name;
    public String job;

    public CreateUserBehavior(String name, String job, TestContext context) {
        this.context = context;
        this.name = name;
        this.job = job;
    }

    @Override
    public void apply(TestActionRunner testActionRunner) {

        testActionRunner.run(http()
                .client("restClientReqres")
                .send()
                .post("users")
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}"));

        testActionRunner.run(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getResponseData(name, job), "objectMapper"))
                .validate(json()
                        .ignore("$.createdAt"))
        );
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