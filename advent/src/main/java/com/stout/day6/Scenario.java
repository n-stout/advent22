package com.stout.day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
			reader.close();

            String[] c = line.split("");

            for(int i=3; i<c.length; i++) {
                if(!c[i].equals(c[i-1]) && !c[i].equals(c[i-2]) && !c[i].equals(c[i-3]) &&
                    !c[i-1].equals(c[i-2]) && !c[i-1].equals(c[i-3]) && !c[i-2].equals(c[i-3])) {
                        System.out.println("Cypher: " + c[i-3] + c[i-2] + c[i-1] + c[i] + " at " + (i+1));
                        break;
                    }
            }

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
			reader.close();

            String[] c = line.split("");

            for(int i=0; i<c.length-14; i++) {
                boolean goodValue = true;
                Set<String> exists = new HashSet<>();
                for(int j=0; j<14; j++) {
                    if(!exists.contains(c[i+j])) {
                        exists.add(c[i+j]);
                    } else {
                        goodValue = false;
                    }
                }
                if(goodValue) {
                    System.out.println("Found beginning of sequence at " + i + ", so end at " + (i+14));
                    break;
                }
            }

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day6a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day6a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day6b-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day6a-input.txt");
        mainB.runScenarioB();

    }
}
