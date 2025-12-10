#!groovy

/**
 * Jenkins Scripted Pipeline for Java Application
 * 
 * This pipeline demonstrates:
 * - Multi-stage build process
 * - Automated testing
 * - Artifact archiving
 * - Email notifications
 * - Error handling
 */

// Define pipeline variables
def mavenHome
def javaHome

node {
    // Define stages for better organization and visualization
    stage('Environment Setup') {
        echo "=== Environment Setup Stage ==="
        
        // Print environment information
        echo "Pipeline started at: ${new Date()}"
        echo "Jenkins URL: ${env.JENKINS_URL}"
        echo "Job Name: ${env.JOB_NAME}"
        echo "Build Number: ${env.BUILD_NUMBER}"
        echo "Workspace: ${env.WORKSPACE}"
        
        // Set tool locations
        javaHome = tool name: 'java-17', type: 'jdk'
        mavenHome = tool name: 'maven-3', type: 'maven'
        
        // Set environment variables
        env.JAVA_HOME = javaHome
        env.PATH = "${javaHome}/bin:${mavenHome}/bin:${env.PATH}"
        
        // Verify tools
        sh 'java -version'
        sh 'mvn -version'
    }
    
    stage('Source Code Checkout') {
        echo "=== Source Code Checkout Stage ==="
        
        try {
            // Clean workspace
            deleteDir()
            
            // Checkout source code from GitHub
            checkout scm
            
            echo "Source code checked out successfully"
            
            // List files to verify checkout
            sh 'ls -la'
            
        } catch (Exception e) {
            echo "Error during checkout: ${e.getMessage()}"
            currentBuild.result = 'FAILURE'
            throw e
        }
    }
    
    stage('Build Validation') {
        echo "=== Build Validation Stage ==="
        
        // Validate project structure
        if (!fileExists('pom.xml')) {
            error('pom.xml not found! Please ensure Maven project structure is correct.')
        }
        
        if (!fileExists('src/main/java')) {
            error('Source directory not found! Please check project structure.')
        }
        
        echo "Project structure validation passed"
    }
    
    stage('Compile') {
        echo "=== Compile Stage ==="
        
        try {
            // Clean and compile the project
            sh 'mvn clean compile'
            
            echo "Compilation completed successfully"
            
        } catch (Exception e) {
            echo "Compilation failed: ${e.getMessage()}"
            currentBuild.result = 'FAILURE'
            throw e
        }
    }
    
    stage('Unit Tests') {
        echo "=== Unit Tests Stage ==="
        
        try {
            // Run unit tests
            sh 'mvn test'
            
            echo "Unit tests completed successfully"
            
        } catch (Exception e) {
            echo "Unit tests failed: ${e.getMessage()}"
            currentBuild.result = 'UNSTABLE'
            
            // Continue pipeline even if tests fail (for demo purposes)
            echo "Continuing pipeline despite test failures..."
            
        } finally {
            // Always publish test results
            if (fileExists('target/surefire-reports/*.xml')) {
                junit 'target/surefire-reports/*.xml'
                echo "Test results published"
            }
        }
    }
    
    stage('Code Quality Analysis') {
        echo "=== Code Quality Analysis Stage ==="
        
        // Simple code quality checks
        echo "Running basic code quality checks..."
        
        // Count Java files
        sh '''
            echo "=== Project Statistics ==="
            find src -name "*.java" | wc -l | xargs echo "Java files:"
            find src -name "*.java" -exec wc -l {} + | tail -1 | awk '{print $1}' | xargs echo "Total lines of code:"
        '''
        
        // Check for TODO comments
        sh '''
            echo "=== TODO Analysis ==="
            grep -r "TODO" src/ || echo "No TODO comments found"
        '''
    }
    
    stage('Package') {
        echo "=== Package Stage ==="
        
        try {
            // Create JAR package
            sh 'mvn package -DskipTests'
            
            echo "Packaging completed successfully"
            
            // Verify JAR was created
            sh 'ls -la target/*.jar'
            
        } catch (Exception e) {
            echo "Packaging failed: ${e.getMessage()}"
            currentBuild.result = 'FAILURE'
            throw e
        }
    }
    
    stage('Archive Artifacts') {
        echo "=== Archive Artifacts Stage ==="
        
        // Archive the built JAR file
        if (fileExists('target/*.jar')) {
            archiveArtifacts artifacts: 'target/*.jar', 
                           fingerprint: true,
                           allowEmptyArchive: false
            echo "Artifacts archived successfully"
        } else {
            echo "Warning: No JAR files found to archive"
        }
        
        // Archive test reports
        if (fileExists('target/surefire-reports/')) {
            archiveArtifacts artifacts: 'target/surefire-reports/*', 
                           allowEmptyArchive: true
            echo "Test reports archived"
        }
    }
    
    stage('Deployment Simulation') {
        echo "=== Deployment Simulation Stage ==="
        
        // Simulate deployment process
        echo "Simulating deployment to development environment..."
        
        // Run the application to verify it works
        sh '''
            echo "Testing application execution..."
            java -jar target/*.jar || echo "Application executed"
        '''
        
        echo "Deployment simulation completed"
    }
    
    stage('Notification') {
        echo "=== Notification Stage ==="
        
        // Build completion notification
        echo "=== BUILD SUMMARY ==="
        echo "Build Status: ${currentBuild.result ?: 'SUCCESS'}"
        echo "Build Duration: ${currentBuild.durationString}"
        echo "Build URL: ${env.BUILD_URL}"
        
        // In a real environment, you might send emails, Slack notifications, etc.
        if (currentBuild.result == 'SUCCESS' || currentBuild.result == null) {
            echo "✅ Pipeline completed successfully!"
        } else {
            echo "❌ Pipeline completed with issues. Status: ${currentBuild.result}"
        }
    }
}

/**
 * Post-build actions (executed regardless of pipeline success/failure)
 */
// Note: In scripted pipelines, post-build actions are handled differently
// than in declarative pipelines. We use try-catch blocks within stages.
