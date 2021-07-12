package com.example.stubs;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;

@SpringBootTest
class StubsApplicationTests {

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

	@Test
	void restAssuredTest() {

		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get("https://reqres.in/api/users/2")
//				.get("http://localhost:5050/api/users/2")
				.then()
				.extract().response();

		Assertions.assertEquals(200, response.statusCode());
		System.out.println(response.getBody().prettyPrint());
		Assertions.assertEquals("Janet", response.jsonPath().getString("data.first_name"));
		Assertions.assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
	}

	@AfterAll
	public static void tearDownMockServer(){
		wireMockServer.stop();
	}
}


