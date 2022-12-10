package com.stout.day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            int cycle = 1;
            int register = 1;
            int signalStrength = 0;
			while (line != null) {
                int[] cycleInstructions = parseScenarioA(line);
                for(int i=0; i<cycleInstructions[0]; i++) {
                    if((cycle + 20) % 40 == 0) {
                        int sig = (cycle * register);
                        signalStrength += sig;
                        System.out.println("Cycle: " + cycle + ", Register: " + register + ", SIgnal strength: " + sig + ", Total: " + signalStrength);
                    }
                    cycle++;
                }
                register += cycleInstructions[1];

                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int[] parseScenarioA(String line) {
        String[] command = line.split(" ");
        switch(command[0]) {
            case "addx":
                return new int[] { 2, Integer.parseInt(command[1])};
            case "noop":
                return new int[] { 1, 0 };
            default:
                throw new RuntimeException("Bad command");
        }
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            int cycle = 0;
            int register = 1;
            int row = 0;
            List<StringBuilder> sbs = new ArrayList<>();
            sbs.add(new StringBuilder());
			while (line != null) {
                int[] cycleInstructions = parseScenarioA(line);
                for(int i=0; i<cycleInstructions[0]; i++) {
                    if(cycle % 40 == 0) {
                        row++;
                        cycle = 0;
                        sbs.add(new StringBuilder());
                    }
                    if(Math.abs(register - cycle) <= 1) {
                        sbs.get(row).append("#");
                    } else {
                        sbs.get(row).append(".");
                    }
                    cycle++;
                }
                register += cycleInstructions[1];

                // Do thing
				line = reader.readLine();
			}

            for(StringBuilder sb : sbs) {
                System.out.println(sb.toString());
            }

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day10a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day10a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day10a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day10a-input.txt");
        mainB.runScenarioB();

    }
}
