package com.stout.day14;

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
        List<String> instructions = new ArrayList<>();
        int maxCol = Integer.MIN_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE;
        int minRow = Integer.MAX_VALUE;

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
			while (line != null) {
                instructions.add(line);
                String[] coords = line.split(" -> ");
                for(int i=0; i<coords.length; i++) {
                    String[] xy = coords[i].split(",");
                    maxCol = Math.max(Integer.parseInt(xy[0]), maxCol);
                    maxRow = Math.max(Integer.parseInt(xy[1]), maxRow);
                    minCol = Math.min(Integer.parseInt(xy[0]), minCol);
                    minRow = Math.min(Integer.parseInt(xy[1]), minRow);
                }
                // Do thing
				line = reader.readLine();
			}

            char[][] field = buildField(maxCol, maxRow, instructions);

            printField(field, minCol, 0);

            while(!dropSand(field)) {
                ;
            }

            printField(field, minCol, 0);

            int counter = 0;
            for(int i=0; i<field.length; i++) {
                for(int j=0; j<field[i].length; j++) {
                    if(field[i][j] == 'o') {
                        counter++;
                    }
                }
            }

            System.out.println("Sand grains: " + counter);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private boolean dropSand(char[][] field) {
        int sCol = 500;
        int sRow = 0;

        while(!Thread.interrupted()) {
            if(sRow >= field[0].length - 1) {
                // Too far down = off field
                return true;
            }
            if(sCol == 0) {
                // Down and left = off field
                return true;
            }
            if(sCol == field.length - 1) {
                // Down and right = off field
                return true;
            }
            char d1 = field[sCol][sRow+1];
            char dl1 = field[sCol-1][sRow+1];
            char dr1 = field[sCol+1][sRow+1];
            if(d1 != '#' && d1 != 'o') {
                // Falls down
                sRow++;
                continue;
            }
            if(dl1 != '#' && dl1 != 'o') {
                // Falls down left
                sRow++;
                sCol--;
                continue;
            }
            if(dr1 != '#' && dr1 != 'o') {
                // Falls down right
                sRow++;
                sCol++;
                continue;
            }
            field[sCol][sRow] = 'o';
            break;
        }

        return false;
    }

    private void printField(char[][] field, int minCol, int minRow) {
        for(int y=minRow; y<field[0].length;y++) {
            for(int x=minCol; x<field.length; x++) {
                if(field[x][y] == '#' || field[x][y] == 'o') {
                    System.out.print(field[x][y]);
                } else {
                    System.out.print('-');
                }
            }
            System.out.print(System.lineSeparator());
        }
    }

    private char[][] buildField(int maxCol, int maxRow, List<String> instructions) {
        char[][] field = new char[maxCol+1][maxRow+1];
        for(String inst : instructions) {
            String[] coords = inst.split(" -> ");
            for(int i=0; i<coords.length-1; i++) {
                String[] pair1 = coords[i].split(",");
                String[] pair2 = coords[i+1].split(",");
                int[] xy1 = new int[] { Integer.parseInt(pair1[0]), Integer.parseInt(pair1[1]) };
                int[] xy2 = new int[] { Integer.parseInt(pair2[0]), Integer.parseInt(pair2[1]) };
                // Assign bigger to xy1
                if(xy1[0] < xy2[0] || xy1[1] < xy2[1]) {
                    int[] temp = xy1;
                    xy1 = xy2;
                    xy2 = temp;
                }

                for(int x=xy2[0]; x<=xy1[0]; x++) {
                    for(int y=xy2[1]; y<=xy1[1]; y++) {
                        field[x][y] = '#';
                    }
                }
            }
        }
        return field;
    } 
    

    private static final int MAX_SIZE = 10_000;

    private boolean dropSandB(char[][] field) {
        int sCol = 500;
        int sRow = 0;

        while(!Thread.interrupted()) {
            //System.out.println("Sand falling: " + sCol + "," + sRow);

            char d1 = field[getIndex(sCol)][sRow+1];
            char dl1 = field[getIndex(sCol-1)][sRow+1];
            char dr1 = field[getIndex(sCol+1)][sRow+1];
            if(d1 != '#' && d1 != 'o') {
                // Falls down
                sRow++;
                continue;
            }
            if(dl1 != '#' && dl1 != 'o') {
                // Falls down left
                sRow++;
                sCol--;
                continue;
            }
            if(dr1 != '#' && dr1 != 'o') {
                // Falls down right
                sRow++;
                sCol++;
                continue;
            }
            field[sCol][sRow] = 'o';
            if(sCol == 500 && sRow == 0) {
                return true;
            }
            break;
        }

        return false;
    }

    private int getIndex(int val) {
        return val % MAX_SIZE;
    }

    public void runScenarioB() {
        List<String> instructions = new ArrayList<>();

        int maxCol = Integer.MIN_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minCol = Integer.MAX_VALUE;
        int minRow = Integer.MAX_VALUE;
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
			while (line != null) {
                instructions.add(line);
                String[] coords = line.split(" -> ");
                for(int i=0; i<coords.length; i++) {
                    String[] xy = coords[i].split(",");
                    maxCol = Math.max(Integer.parseInt(xy[0]), maxCol);
                    maxRow = Math.max(Integer.parseInt(xy[1]), maxRow);
                    minCol = Math.min(Integer.parseInt(xy[0]), minCol);
                    minRow = Math.min(Integer.parseInt(xy[1]), minRow);
                }
				line = reader.readLine();
			}

            char[][] field = buildField(MAX_SIZE, maxRow+2, instructions);
            for(int x=0; x<MAX_SIZE; x++) {
                field[x][maxRow+2] = '#';
            }

            while(!dropSandB(field)) {
                ;
            }

            int counter = 0;
            for(int i=0; i<field.length; i++) {
                for(int j=0; j<field[i].length; j++) {
                    if(field[i][j] == 'o') {
                        counter++;
                    }
                }
            }

            System.out.println("Sand grains: " + counter);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        // Scenario testA = new Scenario("inputs/day14a-test.txt");
        // testA.runScenarioA();
        // Scenario mainA = new Scenario("inputs/day14a-input.txt");
        // mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day14a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day14a-input.txt");
        mainB.runScenarioB();

    }
}
