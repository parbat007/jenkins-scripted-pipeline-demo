package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for App class
 * These tests will be executed during the Jenkins pipeline
 */
public class AppTest {
    
    private App app;
    
    /**
     * Set up test fixture before each test method
     */
    @BeforeEach
    public void setUp() {
        app = new App();
    }
    
    /**
     * Test the getWelcomeMessage method with valid input
     */
    @Test
    public void testGetWelcomeMessageWithValidName() {
        String result = app.getWelcomeMessage("Jenkins");
        assertEquals("Hello, Jenkins! Welcome to our application.", result);
    }
    
    /**
     * Test the getWelcomeMessage method with null input
     */
    @Test
    public void testGetWelcomeMessageWithNullName() {
        String result = app.getWelcomeMessage(null);
        assertEquals("Hello, Anonymous!", result);
    }
    
    /**
     * Test the getWelcomeMessage method with empty input
     */
    @Test
    public void testGetWelcomeMessageWithEmptyName() {
        String result = app.getWelcomeMessage("");
        assertEquals("Hello, Anonymous!", result);
    }
    
    /**
     * Test the add method
     */
    @Test
    public void testAddMethod() {
        assertEquals(5, app.add(2, 3));
        assertEquals(0, app.add(-5, 5));
        assertEquals(-10, app.add(-5, -5));
    }
    
    /**
     * Test that ensures the application doesn't crash with large numbers
     */
    @Test
    public void testAddWithLargeNumbers() {
        assertEquals(2000000000, app.add(1000000000, 1000000000));
    }
}
