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
class TestGetUser {

	APIInterface service = APIClient.getClient().create(APIInterface.class);

	@Test
	@DisplayName("GET - GET USER BY ID")
	void retrofitTest() {
		Response<User> response;
		String userId = "2";

		try {
			response = service.getUser(userId).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assert response.isSuccessful();

		User user = response.body();
		assert user != null;

		assert user.getData().getId().equals(Integer.valueOf(userId));
		assert user.getData().getFirstName().equals("Janet");
		assert user.getData().getLastName().equals("Weaver");
		assert user.getData().getEmail().equals("janet.weaver@reqres.in");

		System.out.println(user);
		System.out.println(user.getData());
		System.out.println(user.getSupport());
		System.out.println(user.getData().getEmail());
		System.out.println(user.getData().getFirstName());
		System.out.println(user.getData().getLastName());
	}
}
