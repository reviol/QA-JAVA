package citrus.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.server.HttpServer;
import com.consol.citrus.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;


public class TestFirst extends TestNGCitrusTestRunner {

    @Autowired
    private HttpClient restClient;
    private HttpServer restServer;

    private TestContext context;

    private String user = "2";

    @Test(description = "Получение информации о пользователе")
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        context.setVariable("value", "!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        echo(context.getVariable("value"));
        echo(context.getVariable("var"));
        echo("${var}" + "123456789");
        echo("${var}" + "123456789");

        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .send()
                .get("users/" + user)
        );

        http(httpActionBuilder -> httpActionBuilder
                .client(restClient)
                .receive()
                .response()
                .messageType(MessageType.JSON)
                .payload("{\n" +
                        "   \"data\":{\n" +
                        "      \"id\":2,\n" +
                        "      \"email\":\"janet.weaver@reqres.in\",\n" +
                        "      \"first_name\":\"Janet\",\n" +
                        "      \"last_name\":\"Weaver\",\n" +
                        "      \"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "   },\n" +
                        "   \"support\":{\n" +
                        "      \"url\":\"https://reqres.in/#support-heading\",\n" +
                        "      \"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "   }\n" +
                        "}")
        );

//        http(httpActionBuilder -> httpActionBuilder
//                        .server(restServer)
//                        .receive()
//                        .post("/ready")
//                        .payload("{\"report\":\"done\"}"));

        // submitOrderRequest from COM to FSOM
//        soap(soapActionBuilder -> soapActionBuilder
//                .client(soapClient)
//                .send()
//                .payload(ptxRq.convert(SubmitOrderRequest.class, submitOrderRequestComFsom(), "http://oms.rt.ru/", "submitOrderRequest" ))
//        );

        // submitOrderResponse from FSOM to COM
//        soap(soapActionBuilder -> soapActionBuilder
//                .client(soapClient)
//                .receive()
//                .xsdSchemaRepository("schemaRepositoryService")
//                .ignore(".//orderStartDate")
//                .payload(ptxRs.convert(SubmitOrderResponse.class, submitOrderResponseFsomCom(), "http://oms.rt.ru/", "submitOrderResponse"))
//                .extractFromPayload("//orderOMSId", "orderOMSIdFsom")
//        );
    }
}
