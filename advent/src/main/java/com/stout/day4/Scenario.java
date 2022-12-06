package com.stout.day4;

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
            int pairsOverlapped = 0;
			while (line != null) {
                String[] a = line.split("[,-]");
                Integer aMin = Integer.parseInt(a[0]);
                Integer aMax = Integer.parseInt(a[1]);
                Integer bMin = Integer.parseInt(a[2]);
                Integer bMax = Integer.parseInt(a[3]);

                // Check if 1 inside of 2
                if(aMin >= bMin && aMax <= bMax || bMin >= aMin && bMax <= aMax) {
                    pairsOverlapped++;
                }

                // Do thing
				line = reader.readLine();
			}

            System.out.println("Total pairs overlapped: " + pairsOverlapped);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			int pairsOverlapped = 0;
			while (line != null) {
                String[] a = line.split("[,-]");
                Integer aMin = Integer.parseInt(a[0]);
                Integer aMax = Integer.parseInt(a[1]);
                Integer bMin = Integer.parseInt(a[2]);
                Integer bMax = Integer.parseInt(a[3]);

                // Check if 1 inside of 2
                if(aMin <= bMax && aMax >= bMin) {
                    pairsOverlapped++;
                }

                // Do thing
				line = reader.readLine();
			}

            System.out.println("Total pairs overlapped: " + pairsOverlapped);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day4a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day4a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day4a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day4a-input.txt");
        mainB.runScenarioB();
    }
}
