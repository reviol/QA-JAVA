package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import features.PojoToXML;
import org.testng.annotations.Test;
import pojo.xml.com.dataaccess.webservicesserver.*;

import java.math.BigDecimal;

public class TestHelperSOAP extends TestNGCitrusTestRunner {

    private TestContext context;

    @Test(description = "Получение", enabled=true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        PojoToXML<Class<NumberToDollars>> convRequest = new PojoToXML<>();
        PojoToXML<Class<NumberToDollarsResponse>> convResponse = new PojoToXML<>();


        soap(soapActionBuilder -> soapActionBuilder
                        .client("soapHelperClient")
                        .send()
//                .payload(getNumberToDollarsRequest(), "marshallerRequest")
                        .payload(convRequest.convert(NumberToDollars.class, getNumberToDollarsRequest(), "http://www.dataaccess.com/webservicesserver/", "NumberToDollars" ))
        );

        soap(soapActionBuilder -> soapActionBuilder
                        .client("soapHelperClient")
                        .receive()
                        .xsdSchemaRepository("schemaRepositoryService")
//                .payload(getNumberToDollarsResponse(), "marshallerResponse")
                        .payload(convResponse.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(), "http://www.dataaccess.com/webservicesserver/", "NumberToDollarsResponse"))
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
