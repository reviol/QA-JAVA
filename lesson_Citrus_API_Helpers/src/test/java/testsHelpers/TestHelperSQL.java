package testsHelpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import javax.sql.DataSource;

public class TestHelperSQL extends TestNGCitrusTestRunner {

    @Autowired
    public DataSource sqlHelper;

    private TestContext context;

    @Test(description = "Test DB", enabled = false)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        sql(action -> action.dataSource(sqlHelper).statement("delete from dbo.request_log"));

        query(action -> action.dataSource(sqlHelper)
                .statement("select client_id from db.offer where offer_id = 2")
                .extract("client_id", "checkClientId"));
    }
}
