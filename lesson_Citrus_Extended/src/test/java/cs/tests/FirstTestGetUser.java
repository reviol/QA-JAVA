package cs.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import cs.pojo.http.Data;
import cs.pojo.http.Support;
import cs.pojo.http.User;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class FirstTestGetUser extends TestNGCitrusTestRunner {

    private TestContext context;

    @Test(description = "Получение информации о пользователе", enabled=true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        context.setVariable("value", "superValue");
        echo("Property \"value\" = " + context.getVariable("value"));
        echo("We have userId = " + context.getVariable("userId"));
        echo("Property \"userId\" = " + "${userId}");

        variable("now", "citrus:currentDate()");
        echo("Today is: ${now}");

//        applyBehavior(new CreateUserBehavior("Mike", "Teacher"));

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .send()
                .get("users/${userId}")
        );

        http(httpActionBuilder -> httpActionBuilder
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .messageType(MessageType.JSON)
                .payload("{\n" +
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
//                .payload(getJsonData(), "objectMapper")
        );
    }

    public User getJsonData(){
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

//        soap(soapActionBuilder -> soapActionBuilder
//                .client(soapClient)
//                .send()
//                .payload(ptxRq.convert(SubmitOrderRequest.class, submitOrderRequestComFsom(), "http://oms.rt.ru/", "submitOrderRequest" ))
//        );

//        soap(soapActionBuilder -> soapActionBuilder
//                .client(soapClient)
//                .receive()
//                .xsdSchemaRepository("schemaRepositoryService")
//                .ignore(".//orderStartDate")
//                .payload(ptxRs.convert(SubmitOrderResponse.class, submitOrderResponseFsomCom(), "http://oms.rt.ru/", "submitOrderResponse"))
//                .extractFromPayload("//orderOMSId", "orderOMSIdFsom")
//        );
}
