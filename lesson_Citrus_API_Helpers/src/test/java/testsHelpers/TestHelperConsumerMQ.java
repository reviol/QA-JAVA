package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.testng.annotations.Test;

public class TestHelperConsumerMQ extends TestNGCitrusTestRunner {
    private TestContext context;

    @Test(description = "Test MQ", enabled = false)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        receive(action -> action
                .endpoint("mqHelperProduser")
                .messageType(MessageType.XML)
                .xsd("schemaName")
                .schemaValidation(true)
                .payload("payload")
        );
    }
}
