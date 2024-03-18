package com.app.UserRegistration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserRegistrationApplicationTests {

	@Test
	void itShouldAddTwoNumbers() {
		int expected = addTwoNumbers(3,4);
		assertEquals(7, expected);
	}

	public static int addTwoNumbers(int a, int b){
		return a+b;
	}
}
