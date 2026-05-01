package com.saqib.app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    @Test
    void testMainLogsMessage() {
        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Run the main method
            App.main(new String[]{});

            // Convert captured output to string
            String output = outContent.toString();

            // Assert that the expected log message appears
            assertTrue(output.contains("Hello Saqib, Pipeline is working!"),
                    "Logger should print the expected message");
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}




