package com.stout.day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
            Set<String> visible = new HashSet<>();

            ArrayList<ArrayList<Integer>> trees = new ArrayList<>();

			while (line != null) {
                String[] treeRow = line.split("");
                ArrayList<Integer> treeRowHeight = new ArrayList<>();
                for(int i=0; i<treeRow.length; i++) {
                    treeRowHeight.add(Integer.parseInt(treeRow[i]));
                }
                trees.add(treeRowHeight);

                // Do thing
				line = reader.readLine();
			}

            // Rows
            for(int r=0; r<trees.size(); r++) {
                // From left
                int currentHighest = 0;
                for(int c=0; c<trees.get(r).size(); c++) {
                    if(r == 0 || c == 0 || c == trees.get(r).size() - 1 || r == trees.size() - 1) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                        continue;
                    }
                    if(trees.get(r).get(c) > currentHighest) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                    }
                }

                // From right
                currentHighest = 0;
                for(int c=trees.get(r).size()-1; c>=0; c--) {
                    if(r == 0 || c == 0 || c == trees.get(r).size() - 1 || r == trees.size() - 1) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                        continue;
                    }
                    if(trees.get(r).get(c) > currentHighest) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                    }
                }
            }

            // Cols
            for(int c=0; c<trees.size(); c++) {
                // From top
                int currentHighest = 0;
                for(int r=0; r<trees.get(c).size(); r++) {
                    if(r == 0 || c == 0 || c == trees.get(r).size() - 1 || r == trees.size() - 1) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                        continue;
                    }
                    if(trees.get(r).get(c) > currentHighest) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                    }
                }

                // From bottom
                currentHighest = 0;
                for(int r=trees.get(c).size()-1; r>=0; r--) {
                    if(r == 0 || c == 0 || c == trees.get(r).size() - 1 || r == trees.size() - 1) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                        continue;
                    }
                    if(trees.get(r).get(c) > currentHighest) {
                        visible.add(r + "-" + c);
                        currentHighest = trees.get(r).get(c);
                    }
                }
            }

            System.out.println(visible.size());

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
            Set<String> visible = new HashSet<>();

            ArrayList<ArrayList<Integer>> trees = new ArrayList<>();

			while (line != null) {
                String[] treeRow = line.split("");
                ArrayList<Integer> treeRowHeight = new ArrayList<>();
                for(int i=0; i<treeRow.length; i++) {
                    treeRowHeight.add(Integer.parseInt(treeRow[i]));
                }
                trees.add(treeRowHeight);

                // Do thing
				line = reader.readLine();
			}

            // Rows
            int maxScore = 0;
            for(int r=0; r<trees.size(); r++) {
                for(int c=0; c<trees.get(r).size(); c++) {
                    // Find the score for this tree
                    maxScore = Math.max(calculateViewingScore(trees.get(r).get(c), r, c, trees), maxScore);
                }
            }

            System.out.println(maxScore);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int calculateViewingScore(int treeHeight, int row, int col, ArrayList<ArrayList<Integer>> heights) {
        int treesNorth = 0;
        for(int i=row-1; i>=0; i--) {
            treesNorth++;
            if(heights.get(i).get(col) >= treeHeight) {
                break;
            }
        }

        int treesSouth = 0;
        for(int i=row+1; i<heights.size(); i++) {
            treesSouth++;
            if(heights.get(i).get(col) >= treeHeight) {
                break;
            }
        }

        int treesWest = 0;
        for(int i=col-1; i>=0; i--) {
            treesWest++;
            if(heights.get(row).get(i) >= treeHeight) {
                break;
            }
        }

        int treesEast = 0;
        for(int i=col+1; i<heights.get(row).size(); i++) {
            treesEast++;
            if(heights.get(row).get(i) >= treeHeight) {
                break;
            }
        }

        System.out.println("[" + col + "," + row + "] Got: " + treesNorth + " * " + treesWest + " * " + treesSouth + " * " + treesEast);

        return treesNorth * treesSouth * treesEast * treesWest;
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day8a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day8a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day8a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day8a-input.txt");
        mainB.runScenarioB();

    }
}
