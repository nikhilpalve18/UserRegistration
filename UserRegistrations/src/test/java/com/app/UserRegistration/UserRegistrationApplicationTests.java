package com.app.UserRegistration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRegistrationApplicationTests {

	@Test
	void itShouldAddTwoNumbers() {
		int actual = addTwoNumbers(3,4);
		assertThat(actual).isEqualTo(7);
	}

	public static int addTwoNumbers(int a, int b){
		return a+b;
	}
}
