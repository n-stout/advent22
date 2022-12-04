package com.stout.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/dayXa-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/dayXa-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/dayXb-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/dayXb-input.txt");
        mainB.runScenarioB();

    }
}
