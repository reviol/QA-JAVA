package com.example.apiHelpers;

import com.example.apiHelpers.pojo.CreateUserRequest;
import com.example.apiHelpers.pojo.CreateUserResponse;
import com.example.apiHelpers.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import java.io.IOException;

@SpringBootTest
class ApiHelpersRetrofitTests {

	APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

	@Test
	@DisplayName("GET - GET USER BY ID")
	void retrofitTest() {
		Response<User> response;

		try {
			response = service.getUser().execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assert response.isSuccessful();

		if(response.isSuccessful()) {
			System.out.println("Response SUCCESS");

			User user = response.body();
			assert user != null;
			System.out.println(user.getData().getId());
			assert user.getData().getId() == 2;

			System.out.println(user.getData().getEmail());
			assert user.getData().getEmail().equals("janet.weaver@reqres.in");

			System.out.println(user);
			System.out.println(user.getData());
			System.out.println(user.getSupport());
			System.out.println(user.getData().getFirstName());
			System.out.println(user.getData().getLastName());
		} else {
			System.out.println("Response ERROR");
		}
	}

	@Test
	@DisplayName("POST - CREATE USER")
	void checkUserCreation() throws IOException {
		Response<CreateUserResponse> responseCreateUser;
		CreateUserResponse userResponse;

		System.out.println(getRequestBody());
		responseCreateUser = service.createUser(getRequestBody()).execute();

		userResponse = responseCreateUser.body();
		System.out.println(userResponse.getId());
		System.out.println(userResponse.getCreatedAt());

		if(responseCreateUser.isSuccessful()){
			System.out.println("Response SUCCESS");
		}else{
			System.out.println("Response ERROR");
		}
		System.out.println(responseCreateUser.body());
		Assertions.assertEquals(201, responseCreateUser.code());
	}

	public CreateUserRequest getRequestBody() {
		CreateUserRequest requestNewUserData = new CreateUserRequest();
		requestNewUserData.setJob("morpheus");
		requestNewUserData.setName("leader");

		return requestNewUserData;
	}
}
