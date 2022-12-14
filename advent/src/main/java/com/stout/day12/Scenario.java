package com.stout.day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        StringBuilder chars = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
            boolean first = true;
			while (line != null) {
                if(!first) {
                    chars.append(":");
                }
                chars.append(line);
                first = false;

                // Do thing
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        int startRow = -1;
        int startCol = -1;
        int endRow = -1;
        int endCol = -1;

        System.out.println(chars.toString());

        String[] rows = chars.toString().split(":");
        String[][] field = new String[rows.length][];
        for(int i=0; i<rows.length; i++) {
            field[i] = rows[i].split("");
            if(rows[i].contains("S")) {
                startRow = i;
                startCol = rows[i].indexOf("S");
                field[startRow][startCol] = "a";
            }
            if(rows[i].contains("E")) {
                endRow = i;
                endCol = rows[i].indexOf("E");
                field[endRow][endCol] = "z";
            }
        }

        Node head = new Node(startRow, startCol, "a", 0);
        Node.nodes.add(head);

        attachNodes(field, head);
    }

    private void attachNodes(String[][] field, Node startingNode) {
        Node northNode = startingNode.getNode(field, "North");

        // North
        
    }

    private int calculatePath(int steps, int startRow, int startCol, int endRow, int endCol, String[][] array, Map<String,Integer> hitPaths) {
        if(startRow == endRow && startCol == endCol) {
            System.out.println("Found end!");
            return steps;
        }
        System.out.println("Calculating from " + startRow + "," + startCol + " to " + endRow + ", " + endCol);
        int minSteps = Integer.MAX_VALUE;
        if(hitPaths.containsKey(startRow + "-" + startCol) && hitPaths.get(startRow + "-" + startCol) < steps) {
            steps = hitPaths.get(startRow + "-" + startCol);
        } else if(hitPaths.containsKey(startRow + "-" + startCol)) {
            return minSteps;
        } else {
            hitPaths.put(startRow + "-" + startCol, steps);
        }
        
        // North
        if(startRow > 0 && array[startRow-1][startCol].charAt(0) <= array[startRow][startCol].charAt(0)+1) {
            System.out.println("Heading north to " + array[startRow-1][startCol].charAt(0));
            minSteps = Math.min(minSteps, calculatePath(steps + 1, startRow - 1, startCol, endRow, endCol, array, hitPaths));
        }

        // South
        if(startRow < array.length - 1 && array[startRow+1][startCol].charAt(0) <= array[startRow][startCol].charAt(0)+1) {
            System.out.println("Heading south to " + array[startRow+1][startCol].charAt(0));
            minSteps = Math.min(minSteps, calculatePath(steps + 1, startRow + 1, startCol, endRow, endCol, array, hitPaths));
        }

        // West
        if(startCol > 0 && array[startRow][startCol-1].charAt(0) <= array[startRow][startCol].charAt(0)+1) {
            
            System.out.println("Heading west to " + array[startRow][startCol-1].charAt(0));
            minSteps = Math.min(minSteps, calculatePath(steps + 1, startRow, startCol - 1, endRow, endCol, array, hitPaths));
        }

        // East
        if(startCol < array[0].length - 1 && array[startRow][startCol+1].charAt(0) <= array[startRow][startCol].charAt(0)+1) {
            System.out.println("Heading east to " + array[startRow][startCol+1].charAt(0));
            minSteps = Math.min(minSteps, calculatePath(steps + 1, startRow, startCol + 1, endRow, endCol, array, hitPaths));
        }

        return minSteps;
    }

    public void runScenarioB() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
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
        Scenario testA = new Scenario("inputs/day12a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day12a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day12b-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day12b-input.txt");
        mainB.runScenarioB();

    }
}
