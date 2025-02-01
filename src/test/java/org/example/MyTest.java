package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyTest {

    @BeforeAll
    static void setup() {
        System.out.println("Before all tests");
    }

    @BeforeEach
    void init() {
        System.out.println("Before each test");
    }

    @Test
    void testAddition() {
        assertEquals(2 + 3, 5, "Addition should be correct");
    }

    @Test
    void testString() {
        String str = "JUnit 5";
        assertTrue(str.startsWith("JUnit"), "String should start with 'JUnit'");
    }

    @AfterEach
    void cleanup() {
        System.out.println("After each test");
    }

    @AfterAll
    static void teardown() {
        System.out.println("After all tests");
    }
}
