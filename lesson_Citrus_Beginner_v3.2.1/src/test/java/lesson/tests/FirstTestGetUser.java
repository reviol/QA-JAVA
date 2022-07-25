package lesson.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import lesson.pojo.Data;
import lesson.pojo.Support;
import lesson.pojo.User;
import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstTestGetUser extends TestNGCitrusSupport {

    private TestContext context;

    @Test(description = "Получение информации о пользователе", enabled=true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        context.setVariable("value", "superValue");
        $(echo("Property \"value\" = " + context.getVariable("value")));

        $(echo("We have userId = " + context.getVariable("userId")));
        $(echo("Property \"userId\" = " + "${userId}"));

        variable("now", "citrus:currentDate()");
        $(echo("Today is: ${now}"));

        $(http()
                .client("restClientReqres")
                .send()
                //.get("users/${userId}")
                .get("users/" + context.getVariable("userId"))
        );

        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .body(new ObjectMappingPayloadBuilder(getJsonData(), "objectMapper"))

//                .body(new ClassPathResource("json/user2.json"))

                .body("{\n" +
                "    \"data\": {\n" +
                "        \"id\": 2,\n" +
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
