package cs.tests;

import java.math.BigDecimal;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import cs.behaviors.CreateUserBehavior;
import cs.features.PojoToXML;
import org.testng.annotations.Test;

public class FirstTestSOAP extends TestNGCitrusTestRunner {

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

        PojoToXML<Class<NumberToDollars>> ptxRq = new PojoToXML<>();
        PojoToXML<Class<NumberToDollarsResponse>> ptxRs = new PojoToXML<>();

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .send()
//                .payload(getNumberToDollarsRequest(), "marshallerRequest")
                .payload(ptxRq.convert(NumberToDollars.class, getNumberToDollarsRequest(), "http://www.dataaccess.com/webservicesserver/", "NumberToDollars" ))
        );

        soap(soapActionBuilder -> soapActionBuilder
                .client("soapClient")
                .receive()
                .xsdSchemaRepository("schemaRepositoryService")
//                .payload(getNumberToDollarsResponse(), "marshallerResponse")
                .payload(ptxRs.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(), "http://www.dataaccess.com/webservicesserver/", "NumberToDollarsResponse"))
        );
    }


    public NumberToDollars getNumberToDollarsRequest() {
        NumberToDollars numberToDollars = new NumberToDollars();
        numberToDollars.setDNum(new BigDecimal("15"));
        return numberToDollars;
    }

    public NumberToDollarsResponse getNumberToDollarsResponse() {
        NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
        numberToDollarsResponse.setNumberToDollarsResult("fifteen dollars");
        return numberToDollarsResponse;
    }
}
