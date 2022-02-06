package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Test;
import pojo.wss.ErrorResponse;
import pojo.wss.WssError;

public class TestHelperWSS extends TestNGCitrusTestRunner {
    private TestContext context;

    @Test(description = "Description")
    @CitrusTest
    public void getTestActionsWSS(){
        this.context = citrus.createTestContext();
        context.setVariable("payload", "none");

        send(action -> action
                .endpoint("wssHelperClient")
                .fork(true)
        );

        receive(action -> action
                .endpoint("wssHelperClient")
//                .messageType(MessageType.JSON)
//                .extractFromPayload("$.*", "payload")
//                .payload(getJsonDataResponseWSS(), "objectMapper")
        );
//        echo("############: ${payload}");
    }

    public ErrorResponse getJsonDataResponseWSS(){
        ErrorResponse errorResponse = new ErrorResponse();

        WssError error = new WssError();
        error.setCode(3);
        error.setMsg("Invalid JSON: EOF while parsing a value at line 1 column 0");
        errorResponse.setError(error);

        return errorResponse;
    }

}
