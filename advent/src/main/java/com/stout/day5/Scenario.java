package com.stout.day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scenario {

    private File inputFile;
    private Stack<String>[] stacks;
    private Pattern pattern;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
        this.stacks = new Stack[9];
        this.pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    }

    public void runScenarioA() {

        stacks[0] = createStack("LNWTD");
        stacks[1] = createStack("CPH");
        stacks[2] = createStack("WPHNDGMJ");
        stacks[3] = createStack("CWSNTQL");
        stacks[4] = createStack("PHCN");
        stacks[5] = createStack("THNDMWQB");
        stacks[6] = createStack("MBRJGSL");
        stacks[7] = createStack("ZNWGVBRT");
        stacks[8] = createStack("WGDNPL");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                Integer[] commands = parseCommand(line);

                for(int i=0; i<commands[0]; i++) {
                    String box = stacks[commands[1]-1].pop();
                    stacks[commands[2]-1].push(box);
                }

                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        for(int i=0; i<stacks.length; i++) {
            System.out.println("Stack " + i + ": " + stacks[i].peek());
        }
    }

    private Integer[] parseCommand(String command) {
        Matcher matcher = pattern.matcher(command);
        if(matcher.matches()) {
            return new Integer[] {
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
            };
        }

        return null;
    }

    private Stack<String> createStack(String letters) {
        Stack<String> stack = new Stack<>();
        for(String letter : letters.split("")) {
            stack.push(letter);
        }
        return stack;
    }

    public void runScenarioB() {
 
        stacks[0] = createStack("LNWTD");
        stacks[1] = createStack("CPH");
        stacks[2] = createStack("WPHNDGMJ");
        stacks[3] = createStack("CWSNTQL");
        stacks[4] = createStack("PHCN");
        stacks[5] = createStack("THNDMWQB");
        stacks[6] = createStack("MBRJGSL");
        stacks[7] = createStack("ZNWGVBRT");
        stacks[8] = createStack("WGDNPL");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                Integer[] commands = parseCommand(line);

                Stack<String> interim = new Stack<>();
                for(int i=0; i<commands[0]; i++) {
                    String box = stacks[commands[1]-1].pop();
                    interim.push(box);
                    
                }
                while(!interim.isEmpty()) {
                    String box = interim.pop();
                    stacks[commands[2]-1].push(box);
                }

                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        for(int i=0; i<stacks.length; i++) {
            System.out.println("Stack " + i + ": " + stacks[i].peek());
        }
    }
    
    public static void main(String[] args) {
        // Scenario testA = new Scenario("inputs/day5a-test.txt");
        // testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day5a-input.txt");
        mainA.runScenarioA();

        // Scenario testB = new Scenario("inputs/day5b-test.txt");
        // testB.runScenarioB();
        // Scenario mainB = new Scenario("inputs/day5b-input.txt");
        // mainB.runScenarioB();

    }
}
