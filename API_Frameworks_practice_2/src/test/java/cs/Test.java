package cs;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import cs.pojo.Data;
import cs.pojo.Support;
import cs.pojo.User;
import org.springframework.http.HttpStatus;

public class Test extends TestNGCitrusTestRunner {

    private TestContext context;

    @org.testng.annotations.Test(description = "Получение информации о пользователе")
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .send()
                .get("api/users/2")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload(getJsonData(), "objectMapper")
        );
    }

    public User getJsonData(){
        User user = new User();

        Data data = new Data();
        data.setId(2);
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
