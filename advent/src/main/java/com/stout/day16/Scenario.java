package com.stout.day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }


    public void runScenarioA(int maxMinutes) {

        Map<String, TunnelNode> nodes = new TreeMap<>();
        Map<String, String> children = new TreeMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            Pattern pattern = Pattern.compile("Valve (\\w+).*=(\\d+).*valves* (.*)");

			while (line != null) {
                //Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
                Matcher m = pattern.matcher(line);
                if(m.find()) {
                    String name = m.group(1);
                    nodes.putIfAbsent(name,new TunnelNode(name, Integer.parseInt(m.group(2))));
                    children.putIfAbsent(name, m.group(3));
                }

                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        for(Entry<String,String> child : children.entrySet()) {
            String name = child.getKey();
            TunnelNode parent = nodes.get(name);
            for(String ch : child.getValue().split(", ")) {
                TunnelNode childNode = nodes.get(ch);
                parent.addChild(childNode);
            }
        }

        boolean changed = true;
        while(changed) {
            changed = false;
            for(TunnelNode node : nodes.values()) {
                if(node.reduceNodes()) {
                    changed = true;
                }
            }
        }

        // Graph is complete!
        System.out.println("Now for tracking...");
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
        Scenario testA = new Scenario("inputs/day16a-test.txt");
        testA.runScenarioA(30);
        // Scenario mainA = new Scenario("inputs/day16a-input.txt");
        // mainA.runScenarioA(30);

        // Scenario testB = new Scenario("inputs/dayXb-test.txt");
        // testB.runScenarioB();
        // Scenario mainB = new Scenario("inputs/dayXb-input.txt");
        // mainB.runScenarioB();

    }
}
