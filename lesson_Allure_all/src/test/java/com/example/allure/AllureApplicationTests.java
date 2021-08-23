package com.example.allure;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.Stream;
import static io.restassured.RestAssured.given;

@SpringBootTest
class AllureApplicationTests {

	private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options()
			.port(5050));

	@BeforeAll
	public static void setUpMockServer() {
		wireMockServer.start();

		WireMock.configureFor("localhost", 5050);
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/users/2"))
				.willReturn(WireMock.aResponse()
						.withStatus(200)
						.withBody("{\n" +
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
								"}")));
	}
	@AfterAll
	public static void tearDownMockServer(){
		wireMockServer.stop();
	}

	@Test
	@Step("Get user id=2")
	@Epic("ReqRes")
	@Feature("GET")
	@Story("Getting user de id from www.reqres.in")
	@Description("Some description")
	void restTestId2() {

		Response response = given()
				.contentType(ContentType.JSON)
				.when()
//				.get("https://reqres.in/api/users/2")
				.get("http://localhost:5050/api/users/2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("Janet", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
	}

	@Test
	@Step("Get user id=7")
	@Epic("ReqRes")
	@Feature("GET")
	@Story("Getting user de id from www.reqres.in")
	@Description("Some description")
	void restTestId7() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("https://reqres.in/api/users/7")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("Michael", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Lawson", response.jsonPath().getString("data.last_name"));
	}

	@Test
	@Step("Get user id=9")
	@Epic("ReqRes")
	@Feature("GET")
	@Story("Getting user de id from www.reqres.in")
	@Description("Some description")
	void restTestId9() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("https://reqres.in/api/users/9")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("Tobias", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Funke", response.jsonPath().getString("data.last_name"));
	}

	private static Stream<Arguments> providedStrings() {
		return Stream.of(
				Arguments.of("1", "George4", "Bluth"),
				Arguments.of("2", "Janet", "Weaver"),
				Arguments.of("7", "Michael", "Lawson"),
				Arguments.of("10", "Byron", "Fields"),
				Arguments.of("12", "Rachel", "Howell9")
		);
	}
	@ParameterizedTest
	@Step("Get users dataProvider")
	@Epic("ReqRes-DataProvider")
	@Feature("GET-DataProvider")
	@Story("Getting user de id from www.reqres.in - DataProvider")
	@Description("Some description")
	@MethodSource("providedStrings")
	void restTestDataProvider(String id, String name, String surname) {

		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("https://reqres.in/api/users/" + id)
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals(name, response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals(surname, response.jsonPath().getString("data.last_name"));
	}
}


