package com.example.apiHelpers;

import com.example.apiHelpers.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import java.io.IOException;

@SpringBootTest
class ApiHelpersRetrofitTests {

	@Test
	void retrofitTest() {
		Response<User> response;
		APIInterface service = APIClientHelper.getClient().create(APIInterface.class);
		try {
			response = service.getUser().execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

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
}
