package com.example.frameworks.retrofit;

import com.example.frameworks.pojo.CreateUserRequest;
import com.example.frameworks.pojo.CreateUserResponse;
import com.example.frameworks.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import java.io.IOException;

@SpringBootTest
class RetrofitTests {
	APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

	@Test
	@DisplayName("GET - GET USER BY ID")
	void checkGetUser() throws IOException {
		Response<User> response = service.getUser().execute();

		System.out.println(response.body());
		Assertions.assertTrue(response.isSuccessful());
		Assertions.assertEquals(2, response.body().getData().getId());
		Assertions.assertEquals("Janet", response.body().getData().getFirstName());
		Assertions.assertEquals("Weaver", response.body().getData().getLastName());

	}

	@Test
	@DisplayName("POST - CREATE USER")
	void checkUserCreation() throws IOException {
		Response<CreateUserResponse> responseCreateUser = service.createUser(getRequestBody()).execute();
		System.out.println(getRequestBody());

		System.out.println(responseCreateUser.body());
		Assertions.assertTrue(responseCreateUser.isSuccessful());
		Assertions.assertEquals(201, responseCreateUser.code());
		Assertions.assertEquals("morpheus", responseCreateUser.body().getName());
		Assertions.assertEquals("leader", responseCreateUser.body().getJob());
	}

	public CreateUserRequest getRequestBody() {
		CreateUserRequest requestNewUserData = new CreateUserRequest();
		requestNewUserData.setJob("leader");
		requestNewUserData.setName("morpheus");

		return requestNewUserData;
	}
}
