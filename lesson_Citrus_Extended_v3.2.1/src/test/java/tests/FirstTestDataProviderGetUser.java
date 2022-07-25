package tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.CitrusParameters;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Data;
import pojo.Support;
import pojo.User;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstTestDataProviderGetUser extends TestNGCitrusSupport {

    public TestContext context;

    @DataProvider(name = "dataProvider")
    public Object[][] cardTypeProvider() {
        return new Object[][]{
                new Object[]{"1","George", "Bluth"},
                new Object[]{"2","Janet", "Weaver"},
                new Object[]{"3","Emma", "Wong"},
                new Object[]{"4","Eve", "Holt"},
                new Object[]{"5","Charles", "Morris"},
                new Object[]{"6","Tracey", "Ramos"},
                new Object[]{"7","Michael", "Lawson"},
                new Object[]{"8","Lindsay", "Ferguson"},
                new Object[]{"9","Tobias", "Funke"},
                new Object[]{"10","Byron", "Fields"},
                new Object[]{"11","George", "Edwards"},
                new Object[]{"12","Rachel", "Howell"},
        };
    }

    @Test(description = "Получение информации о пользователе", dataProvider = "dataProvider" )
    @CitrusTest
    public void getTestActions(String id, String name, String surname) {
        this.context = citrus.getCitrusContext().createTestContext();

        run(http()
                .client("restClientReqres")
                .send()
                .get("users/" + id)
        );

        run(http().client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .validate(jsonPath()
                        .expression("$.data.id", id)
                        .expression("$.data.first_name", name)
                        .expression("$.data.last_name", surname))
        );
    }
}