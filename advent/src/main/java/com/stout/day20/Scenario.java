package com.stout.day20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        List<PositionedNumber<Integer>> numbers = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                numbers.add(new PositionedNumber<Integer>(Integer.parseInt(line)));
                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        List<PositionedNumber<Integer>> cycled = new LinkedList<>(numbers);
        System.out.println(cycled);
        int size = cycled.size();
        PositionedNumber<Integer> zero = null;
        for(PositionedNumber<Integer> number : numbers) {
            if(number.get() == 0) {
                zero = number;
            }
            int position = cycled.indexOf(number);
            cycled.remove(number);
            int newPosition = mod(position + number.get(), size - 1);
            cycled.add(newPosition, number);
            // System.out.println(cycled);
        }

        System.out.println("Position 1000: " + cycled.get(mod(cycled.indexOf(zero) + 1_000, size)).get());
        System.out.println("Position 2000: " + cycled.get(mod(cycled.indexOf(zero) + 2_000, size)).get());
        System.out.println("Position 3000: " + cycled.get(mod(cycled.indexOf(zero) + 3_000, size)).get());
    }

    private int mod(int a, int b) {
        int m = a % b;
        if(m < 0) {
            return m + b;
        } else {
            return m;
        }
    }

    public void runScenarioB() {
        List<PositionedNumber<Long>> numbers = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                numbers.add(new PositionedNumber<Long>(Long.parseLong(line) * 811589153L));
                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        List<PositionedNumber<Long>> cycled = new LinkedList<>(numbers);
        System.out.println(cycled);
        int size = cycled.size();
        PositionedNumber<Long> zero = null;
        for(int i=0; i<10; i++) {
            for(PositionedNumber<Long> number : numbers) {
                if(number.get() == 0) {
                    zero = number;
                }
                int position = cycled.indexOf(number);
                cycled.remove(number);
                int newPosition = (int)mod(position + number.get(), size - 1);
                cycled.add(newPosition, number);
                // System.out.println(cycled);
            }
        }

        System.out.println("Position 1000: " + cycled.get(mod(cycled.indexOf(zero) + 1_000, size)).get());
        System.out.println("Position 2000: " + cycled.get(mod(cycled.indexOf(zero) + 2_000, size)).get());
        System.out.println("Position 3000: " + cycled.get(mod(cycled.indexOf(zero) + 3_000, size)).get());
    }

    private long mod(long a, long b) {
        long m = a % b;
        if(m < 0) {
            return m + b;
        } else {
            return m;
        }
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day20a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day20a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day20a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day20a-input.txt");
        mainB.runScenarioB();

    }
}
