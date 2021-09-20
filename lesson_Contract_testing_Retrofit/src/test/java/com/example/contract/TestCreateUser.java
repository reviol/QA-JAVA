package com.example.contract;

import com.example.contract.pojo.CreateUserRequest;
import com.example.contract.pojo.CreateUserResponse;
import com.example.contract.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;

import java.io.IOException;

@SpringBootTest
class TestCreateUser {
	APIInterface service = APIClient.getClient().create(APIInterface.class);

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
		Assertions.assertEquals("Driver", responseCreateUser.body().getJob());
		Assertions.assertEquals("Nick", responseCreateUser.body().getName());

	}

	public CreateUserRequest getRequestBody() {
		CreateUserRequest requestNewUserData = new CreateUserRequest();
		requestNewUserData.setJob("Driver");
		requestNewUserData.setName("Nick");

		return requestNewUserData;
	}
}
