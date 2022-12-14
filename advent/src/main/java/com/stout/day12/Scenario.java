package com.stout.day12;

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
        Node.nodes.clear();
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

        Node head = new Node(startRow, startCol, 'a', 0, null);
        Node.nodes.put(head.getKey(), head);

        attachNodes(field, head);

        System.out.println(Node.nodes.get(endRow + "-" + endCol));
    }

    private void attachNodes(String[][] field, Node startingNode) {
        String[] directions = new String[] { "North", "South", "East", "West" };
        for(String direction : directions) {
            Node node = startingNode.getNode(field, direction);
            if(node == null) {
                continue;
            }
            Node originalNode = Node.nodes.get(node.getKey());
            if(originalNode != null) {
                if(node.getDepth() < originalNode.getDepth()) {
                    // New wins
                    startingNode.addLink(node);
                    originalNode.remove();
                    Node.nodes.put(node.getKey(), node);
                    attachNodes(field, node);
                }
            } else {
                startingNode.addLink(node);
                Node.nodes.put(node.getKey(), node);
                attachNodes(field, node);
            }
        }        
    }

    public void runScenarioB() {
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
        List<int[]> startingPositions = new ArrayList<>();

        System.out.println(chars.toString());

        String[] rows = chars.toString().split(":");
        String[][] field = new String[rows.length][];
        for(int i=0; i<rows.length; i++) {
            field[i] = rows[i].split("");
            if(rows[i].contains("a") || rows[i].contains("S")) {
                String[] starts = rows[i].split("");
                for(int j=0; j<starts.length; j++) {
                    String start = starts[j];
                    if(start.equals("a") || start.equals("S")) {
                        startingPositions.add(new int[] { i, j });
                    }
                }
            }
            if(rows[i].contains("E")) {
                endRow = i;
                endCol = rows[i].indexOf("E");
                field[endRow][endCol] = "z";
            }
        }

        int lowest = Integer.MAX_VALUE;
        for(int[] start : startingPositions) {
            Node head = new Node(start[0], start[1], 'a', 0, null);
            Node.nodes.put(head.getKey(), head);
            attachNodes(field, head);
            lowest = Math.min(lowest, Node.nodes.get(endRow + "-" + endCol).getDepth());
        }

        System.out.println(lowest);
    }

    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day12a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day12a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day12a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day12a-input.txt");
        mainB.runScenarioB();

    }
}
