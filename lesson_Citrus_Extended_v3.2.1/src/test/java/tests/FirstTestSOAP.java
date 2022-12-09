package tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import features.CustomMarshaller;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

public class FirstTestSOAP extends TestNGCitrusSupport {

    public TestContext context;

    @Test(description = "Получение информации о пользователе", enabled=true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        CustomMarshaller<Class<NumberToDollars>> ptxRq = new CustomMarshaller<>();
        CustomMarshaller<Class<NumberToDollarsResponse>> ptxRs = new CustomMarshaller<>();
        run(soap()
                .client("soapClient")
                .send()
                .message()
                .body(ptxRq.convert(NumberToDollars.class, getNumberToDollarsRequest(),
                        "http://www.dataaccess.com/webservicesserver/", "NumberToDollars")));

        run(soap()
                .client("soapClient")
                .receive()
                .message()
                .body(ptxRs.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(),
                        "http://www.dataaccess.com/webservicesserver/", "NumberToDollarsResponse")));
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
