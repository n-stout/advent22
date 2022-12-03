package com.stout.day3;

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

            int errorTotal = 0;
			while (line != null) {
                Set<String> errors = new HashSet<>();
                String[] sacks = new String[] {
                    line.substring(0, line.length()/2),
                    line.substring(line.length()/2)
                };

                for(String i : sacks[0].split("")) {
                    if(sacks[1].contains(i) && !errors.contains(i)) {
                        System.err.println("Found: " + i);
                        errorTotal += getScore(i.charAt(0));
                        errors.add(i);
                    }
                }

                System.out.println("Got: " + sacks[0] + ", " + sacks[1] + ". Total: " + errorTotal);
                
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int getScore(char c) {
        if((int) c < 97) {
            return (int)c - 38;
        } else {
            return (int)c - 96;
        }
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
            int total = 0;
			while (line != null) {
                String bag1 = line;
                line = reader.readLine();
                String bag2 = line;
				line = reader.readLine();
                String bag3 = line;
                line = reader.readLine();

                System.out.println("Bags: " + bag1 + ", " + bag2 + ", " + bag3);

                for(String i : bag1.split("")) {
                    if(bag2.contains(i) && bag3.contains(i)) {
                        total += getScore(i.charAt(0));
                        System.err.println("Found triple: " + i);
                        break;
                    }
                }
                System.out.println("Total: " + total);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day3a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day3a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day3b-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day3b-input.txt");
        mainB.runScenarioB();

    }
}
