package com.stout.day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
        Monkey.monkies.clear();
    }

    public void runScenarioA() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                String[] monkeyNum = line.split("[\\s:]");
                int monkeyId = Integer.parseInt(monkeyNum[1]);
                line = reader.readLine();
                String[] items = line.split("  Starting items: ")[1].split(", ");
                List<Long> itemWorries = Arrays.asList(items).stream().map(i -> Long.parseLong(i)).collect(Collectors.toList());
                line = reader.readLine();
                String[] operation = line.substring(19).split(" ");
                Function<BigInteger, BigInteger> itemWorryFunc = ( old -> {
                    BigInteger value = new BigInteger("0");
                    if(operation[0].equals("old")) {
                        value = old;
                    } else {
                        value = new BigInteger(operation[0]);
                    }

                    BigInteger modifier = new BigInteger("0");
                    if(operation[2].equals("old")) {
                        modifier = old;
                    } else {
                        modifier = new BigInteger(operation[2]);
                    }

                    switch(operation[1]) {
                        case "+":
                            return value.add(modifier);
                        case "-":
                            return value.subtract(modifier);
                        case "*":
                            return value.multiply(modifier);
                        case "/":
                            return value.divide(modifier);
                        default:
                            throw new RuntimeException("wat.");
                    }
                });

                line = reader.readLine();
                int modulo = Integer.parseInt(line.split("  Test: divisible by ")[1]);
                line = reader.readLine();
                int targetMonkeyTrue = Integer.parseInt(line.split("    If true: throw to monkey ")[1]);
                line = reader.readLine();
                int targetMonkeyFalse = Integer.parseInt(line.split("    If false: throw to monkey ")[1]);
                Function<BigInteger, Integer> targetMonkeyFunc = (worry -> {
                    if(worry.mod(new BigInteger(modulo + "")).longValue() == 0) {
                        return targetMonkeyTrue;
                    } else {
                        return targetMonkeyFalse;
                    }
                });

                Monkey monkey = new Monkey(monkeyId, 3, itemWorries, itemWorryFunc, targetMonkeyFunc);

                Monkey.monkies.put(monkeyId, monkey);

                // Do thing
				line = reader.readLine();

                if(line != null) {
                    line = reader.readLine();
                }
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        for(int i=0; i<20; i++) {
            for(Monkey monkey : Monkey.monkies.values()) {
                while(monkey.inspect()) {
                    ; // Inspect multiple
                }
            }
            for(Monkey monkey : Monkey.monkies.values()) {
                 System.out.println(i + ": " + monkey);
            }
        }
    }

    public void runScenarioB() {
        
        int worryModulo = 1;
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                String[] monkeyNum = line.split("[\\s:]");
                int monkeyId = Integer.parseInt(monkeyNum[1]);
                line = reader.readLine();
                String[] items = line.split("  Starting items: ")[1].split(", ");
                List<Long> itemWorries = Arrays.asList(items).stream().map(i -> Long.parseLong(i)).collect(Collectors.toList());
                line = reader.readLine();
                String[] operation = line.substring(19).split(" ");
                Function<BigInteger, BigInteger> itemWorryFunc = ( old -> {
                    BigInteger value = new BigInteger("0");
                    if(operation[0].equals("old")) {
                        value = old;
                    } else {
                        value = new BigInteger(operation[0]);
                    }

                    BigInteger modifier = new BigInteger("0");
                    if(operation[2].equals("old")) {
                        modifier = old;
                    } else {
                        modifier = new BigInteger(operation[2]);
                    }

                    switch(operation[1]) {
                        case "+":
                            return value.add(modifier);
                        case "-":
                            return value.subtract(modifier);
                        case "*":
                            return value.multiply(modifier);
                        case "/":
                            return value.divide(modifier);
                        default:
                            throw new RuntimeException("wat.");
                    }
                });

                line = reader.readLine();
                int modulo = Integer.parseInt(line.split("  Test: divisible by ")[1]);
                worryModulo *= modulo;
                line = reader.readLine();
                int targetMonkeyTrue = Integer.parseInt(line.split("    If true: throw to monkey ")[1]);
                line = reader.readLine();
                int targetMonkeyFalse = Integer.parseInt(line.split("    If false: throw to monkey ")[1]);
                Function<BigInteger, Integer> targetMonkeyFunc = (worry -> {
                    if(worry.mod(new BigInteger(modulo + "")).longValue() == 0) {
                        return targetMonkeyTrue;
                    } else {
                        return targetMonkeyFalse;
                    }
                });

                Monkey monkey = new Monkey(monkeyId, 1, itemWorries, itemWorryFunc, targetMonkeyFunc);

                Monkey.monkies.put(monkeyId, monkey);

                // Do thing
				line = reader.readLine();

                if(line != null) {
                    line = reader.readLine();
                }
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        final int total = worryModulo;

        for(Monkey monkey : Monkey.monkies.values()) {
            Function<BigInteger, BigInteger> origWorryFunc = monkey.getItemWorryFunc();
            monkey.setItemWorryFunc(i -> {
                i = i.mod(new BigInteger("" + total));
                return origWorryFunc.apply(i);
            });
        }

        for(int i=0; i<10_000; i++) {
            for(Monkey monkey : Monkey.monkies.values()) {
                while(monkey.inspect()) {
                    ; // Inspect multiple
                }
            }
            for(Monkey monkey : Monkey.monkies.values()) {
                 System.out.println(i + ": " + monkey);
            }
        }

        System.out.println("Total " + total);
    }
    
    public static void main(String[] args) {
    //     Scenario testA = new Scenario("inputs/day11a-test.txt");
    //     testA.runScenarioA();
    //     Scenario mainA = new Scenario("inputs/day11a-input.txt");
    //     mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day11a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day11a-input.txt");
        mainB.runScenarioB();

    }
}
