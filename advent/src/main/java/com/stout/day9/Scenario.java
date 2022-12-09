package com.stout.day9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.AbstractDocument.BranchElement;

public class Scenario {

    private File inputFile;

    Set<String> visitedLocationsA;
    Set<String> visitedLocationsB;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
        this.visitedLocationsA = new HashSet<>();
        this.visitedLocationsB = new HashSet<>();
    }

    private void storeLocation(int x, int y, Set<String> locations) {
        locations.add(x + "," + y);
    }

    private boolean closeEnough(int hX, int hY, int tX, int tY) {
        return 1.5 > Math.sqrt(Math.pow(hX - tX, 2) + Math.pow(hY - tY, 2));
    }

    private int[] updateTailLocation(int hX, int hY, int tX, int tY) {
        if(!closeEnough(hX, hY, tX, tY)) {
            // Head moved far from the tail, let's catch up!
            if(hX == tX) {
                // same column
                if(hY > tY) tY++; else tY--;
            } else if(hY == tY) {
                // same row
                if(hX > tX) tX++; else tX--;
            } else {
                // diagonal, so do both!
                if(hY > tY) tY++; else tY--;
                if(hX > tX) tX++; else tX--;
            }
        }
        return new int[] { tX, tY };
    }

    public void runScenarioA() {

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            int hX, tX, hY, tY;
            hX = tX = hY = tY = 0;

            storeLocation(0,0, visitedLocationsA); // Starting point

			while (line != null) {
                String[] headDir = line.split(" ");
                // Move the head
                for(int i=1; i<=Integer.parseInt(headDir[1]); i++) {
                    switch(headDir[0]) {
                        case "R":
                            hX++;
                            break;
                        case "U":
                            hY++;
                            break;
                        case "D":
                            hY--;
                            break;
                        case "L":
                            hX--;
                            break;
                        default:
                            throw new RuntimeException("Unexpected input!");
                    }

                    int[] newTailLocation = updateTailLocation(hX, hY, tX, tY);
                    tX = newTailLocation[0];
                    tY = newTailLocation[1];

                    storeLocation(newTailLocation[0], newTailLocation[1], visitedLocationsA);

                    //System.out.println("Head: [" + hX + "," + hY + "] Tail: [" + tX + "," + tY + "]");
                }

                // Do thing
				line = reader.readLine();
			}

            System.out.println("Locations visited: " + visitedLocationsA.size());

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            int rope[][] = new int[10][2];

            storeLocation(0,0, visitedLocationsB); // Starting point

			while (line != null) {
                String[] headDir = line.split(" ");
                // Move the head
                for(int i=1; i<=Integer.parseInt(headDir[1]); i++) {
                    switch(headDir[0]) {
                        case "R":
                            rope[0][0]++;
                            break;
                        case "U":
                            rope[0][1]++;
                            break;
                        case "D":
                            rope[0][1]--;
                            break;
                        case "L":
                            rope[0][0]--;
                            break;
                        default:
                            throw new RuntimeException("Unexpected input!");
                    }

                    for(int k=1; k<rope.length; k++) {
                        rope[k] = updateTailLocation(rope[k-1][0], rope[k-1][1], rope[k][0], rope[k][1]);
                    }

                    storeLocation(rope[rope.length - 1][0], rope[rope.length - 1][1], visitedLocationsB);
                }

                // Do thing
				line = reader.readLine();
			}

            System.out.println("Locations visited: " + visitedLocationsB.size());

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day9a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day9a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day9b-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day9a-input.txt");
        mainB.runScenarioB();

    }
}
