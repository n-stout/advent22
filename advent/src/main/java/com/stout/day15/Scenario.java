package com.stout.day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA(int affectedRow) {

        List<int[]> affectedRanges = new ArrayList<>();

        Pattern pattern = Pattern.compile("x=([-\\d]+), y=([-\\d]+).*x=([-\\d]+), y=([-\\d]+)");

        SortedSet<Integer> positions = new TreeSet<>();
        List<int[]> beaconPositions = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

			while (line != null) {
                int[] sXY = new int[2];
                int[] bXY = new int[2];
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    sXY = new int[] { Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) };
                    bXY = new int[] { Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)) };
                    beaconPositions.add(bXY);

                    int[] range = coveredRange(affectedRow, sXY, bXY);
                    if(range != null) {
                        System.out.println("Range found: " + range[0] + ":" + range[1]);
                        affectedRanges.add(range);
                    }
                }

                // Do thing
				line = reader.readLine();
			}

            for(int[] range : affectedRanges) {
                for(int i=range[0]; i<=range[1]; i++) {
                    boolean foundBeacon = false;
                    for(int[] beaconPosition : beaconPositions) {
                        if(beaconPosition[0] == i && beaconPosition[1] == affectedRow) {
                            foundBeacon = true;
                        }
                    }
                    if(!foundBeacon) {
                        positions.add(i);
                    }
                }
            }

            System.out.println("Position count: " + positions.size());

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int[] coveredRange(int row, int[] sXY, int[] bXY) {
        int dist = getDist(bXY, sXY);
        int offset = Math.abs(sXY[1] - row);
        int delta = dist - offset;
        if(delta >= 0) {
            return new int[] { sXY[0] - delta, sXY[0] + delta };
        } else {
            return null;
        }
    }

    private int getDist(int[] aXY, int[] bXY) {
        return Math.abs(aXY[0] - bXY[0]) + Math.abs(aXY[1] - bXY[1]);
    }

    public void runScenarioB(int low, int high) {
        Pattern pattern = Pattern.compile("x=([-\\d]+), y=([-\\d]+).*x=([-\\d]+), y=([-\\d]+)");

        SortedSet<Integer> positions = new TreeSet<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            Map<Integer, List<int[]>> gapMap = new TreeMap<>();

            for(int y=low; y<=high; y++) {
                gapMap.put(y, new ArrayList<>());
                gapMap.get(y).add(new int[] { low, high });
            }

			while (line != null) {
                int[] sXY = new int[2];
                int[] bXY = new int[2];
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    sXY = new int[] { Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) };
                    bXY = new int[] { Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)) };

                    int dist = getDist(sXY, bXY);
                    int lowY = Math.max(low, sXY[1]-dist);
                    int highY = Math.min(high, sXY[1]+dist);
                    for(int i=lowY; i<=highY; i++) {
                        int[] range = coveredRange(i, sXY, bXY);
                        if(range != null) {
                            removeRange(gapMap.get(i), range);
                        }
                    }

                }

                // Do thing
				line = reader.readLine();
			}

            printGaps(gapMap);

            System.out.println("Position count: " + positions.size());

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void printGaps(Map<Integer, List<int[]>> gapMap) {
        for(Integer y : gapMap.keySet()) {
            if(gapMap.get(y).size() >= 1) {
                for(int[] gap : gapMap.get(y)) {
                    System.out.println("Gap found in row " + y + ": " + gap[0] + " to " + gap[1]);
                }
            }
        }
    }

    private void removeRange(List<int[]> ranges, int[] coveredRange) {
        List<int[]> newRanges = new ArrayList<>();
        for(int[] range : ranges) {
            if(intersect(range, coveredRange)) {
                if(range[0] < coveredRange[0]) {
                    newRanges.add(new int[] { range[0], coveredRange[0] - 1 });
                }
                if(coveredRange[1] < range[1]) {
                    newRanges.add(new int[] { coveredRange[1] + 1, range[1] });
                }
            } else {
                newRanges.add(range);
            }
        }
        ranges.clear();
        ranges.addAll(newRanges);
    }

    private boolean intersect(int[] a, int[] b) {
        return a[0] < b[1] && b[0] < a[1];
    }
    
    public static void main(String[] args) {
        // Scenario testA = new Scenario("inputs/day15a-test.txt");
        // testA.runScenarioA(10);
        // Scenario mainA = new Scenario("inputs/day15a-input.txt");
        // mainA.runScenarioA(2_000_000);

        Scenario testB = new Scenario("inputs/day15a-test.txt");
        testB.runScenarioB(0,20);
        Scenario mainB = new Scenario("inputs/day15a-input.txt");
        mainB.runScenarioB(0, 4_000_000);

    }
}
