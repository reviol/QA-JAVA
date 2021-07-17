package cs;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.CitrusParameters;
import cs.pojo.Data;
import cs.pojo.Support;
import cs.pojo.User;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestDataProvider extends TestNGCitrusTestRunner {

    private TestContext context;

    @DataProvider(name = "dataProvider")
    public Object[][] cardTypeProvider() {
        return new Object[][]{
                new Object[]{"1","George", "Bluth"},
                new Object[]{"2","Janet", "Weaver"},
                new Object[]{"3","Emma", "Wong"},
                new Object[]{"5","Charles", "Morris"},
                new Object[]{"7","Michael", "Lawson"},
                new Object[]{"10","Byron", "Fields"},
                new Object[]{"12","Rachel", "Howell"},
        };
    }

    @Test(description = "Получение информации о пользователе", dataProvider = "dataProvider" )
    @Parameters({"context"})
    @CitrusTest
    @CitrusParameters({"id", "name", "surname"})
    public void getTestActions(String id, String name, String surname) {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .send()
                .get("api/users/" + id)
        );

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload(getJsonData(Integer.valueOf(id), name, surname), "objectMapper")
                .ignore("$.data.email")
                .ignore("$.data.avatar")
                .ignore("$.support.url")
                .ignore("$.support.text")
        );
    }

    public User getJsonData(Integer id, String name, String surname){
        User user = new User();

        Data data = new Data();
        data.setId(id);
        data.setEmail("janet.weaver@reqres.in");
        data.setFirstName(name);
        data.setLastName(surname);
        data.setAvatar("https://reqres.in/img/faces/2-image.jpg");
        user.setData(data);

        Support support = new Support();
        support.setUrl("https://reqres.in/#support-heading");
        support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");

        user.setSupport(support);
        return user;
    }
}
