package com.amdocs.eureka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void intEquals() {

		System.out.println("Hello World");
		assertEquals(1, 1);
	}

	@Test
	void stringEquals() {
		System.out.println("Hello World");
		assertEquals("Hello World", "Hello World");
	}

	@Test
	void booleanEquals() {
		System.out.println("Hello World");
		assertEquals(true, true);
	}

}
