package tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import pojo.Data;
import pojo.Support;
import pojo.User;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class TestMock extends TestNGCitrusSupport {

    public TestContext context;

    @Test(description = "Получение информации о пользователе", enabled=true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        run(http()
                .client("restClient")
                .send()
                .get("users/" + context.getVariable("userId"))
                .fork(true)
        );

        run(http()
                .server("restServer")
                .receive()
                .get());

        run(http()
                .server("restServer")
                .send()
                .response()
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": ${userId},\n" +
                        "        \"email\": \"janet.weaver@reqres.in\",\n" +
                        "        \"first_name\": \"Janet\",\n" +
                        "        \"last_name\": \"Weaver\",\n" +
                        "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "    },\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}"));

        run(http().client("restClient")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getJsonData(), "objectMapper"))
        );
    }

    public User getJsonData() {
        User user = new User();

        Data data = new Data();
        data.setId(Integer.valueOf(context.getVariable("userId")));
        data.setEmail("janet.weaver@reqres.in");
        data.setFirstName("Janet");
        data.setLastName("Weaver");
        data.setAvatar("https://reqres.in/img/faces/2-image.jpg");
        user.setData(data);

        Support support = new Support();
        support.setUrl("https://reqres.in/#support-heading");
        support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");

        user.setSupport(support);

        return user;
    }
}