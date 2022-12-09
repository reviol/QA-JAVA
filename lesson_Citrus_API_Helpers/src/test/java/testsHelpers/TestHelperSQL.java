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

        //sql(action -> action.dataSource(sqlHelper).statement("CREATE TABLE teachers()"));
//        sql(action -> action.dataSource(sqlHelper).statement("CREATE TABLE roles(\n" +
//                "   role_id serial PRIMARY KEY,\n" +
//                "   role_name VARCHAR (255) UNIQUE NOT NULL\n" +
//                ")"));
        //      sql(action -> action.dataSource(sqlHelper).statement("insert into roles values('36','anotherRole')"));
//        sql(action -> action.dataSource(sqlHelper).statement("delete from dbo.request_log"));

        query(action -> action.dataSource(sqlHelper)
                .statement("select * from roles")
                .extract("role_name", "current_role"));

//        query(action -> action.dataSource(sqlHelper)
//                .statement("select role_name from roles where role_id = 36")
//                .extract("role_name", "current_role"));

        echo("${current_role}");
    }
}
