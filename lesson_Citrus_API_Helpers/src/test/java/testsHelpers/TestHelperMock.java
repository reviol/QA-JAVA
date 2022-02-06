package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import pojo.http.Data;
import pojo.http.Support;
import pojo.http.User;

public class TestHelperMock extends TestNGCitrusTestRunner {
    private TestContext context;

    @Test(description = "Получение информации о пользователе", enabled=true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqresMock")
                .send()
                .get("users/${userId}")
                .fork(true)
                );

        http(httpActionBuilder -> httpActionBuilder
                .server("restHelperServer")
                .receive()
                .get());

        http(httpActionBuilder -> httpActionBuilder
                .server("restHelperServer")
                .send()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload("{\n" +
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
                        "}")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqresMock")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload(getJsonData(), "objectMapper"));
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
