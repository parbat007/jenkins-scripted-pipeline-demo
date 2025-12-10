package com.example;

/**
 * Simple Hello World application
 * This demonstrates a basic Java application for Jenkins pipeline testing
 */
public class App {
    
    /**
     * Main method - entry point of the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World from Jenkins Pipeline!");
        System.out.println("This is a scripted pipeline demo application.");
        
        // Demonstrate some basic functionality
        App app = new App();
        String message = app.getWelcomeMessage("Jenkins");
        System.out.println(message);
    }
    
    /**
     * Returns a personalized welcome message
     * @param name the name to include in the welcome message
     * @return formatted welcome message
     */
    public String getWelcomeMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Hello, Anonymous!";
        }
        return "Hello, " + name + "! Welcome to our application.";
    }
    
    /**
     * Simple calculation method for testing purposes
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }
}
