package com.stout.day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            int totalIndex = 0;
            int index = 0;
			while (line != null) {

                String line1 = line;
                line = reader.readLine(); // Second Packet
                String line2 = line;
                index++;
                boolean isOrdered = isOrdered(line1, line2);
                System.out.println(line1 + " : " + line2 + "->" + isOrdered);
                if(isOrdered) {
                    totalIndex += index;
                }

				line = reader.readLine(); // Blank
                line = reader.readLine(); // Next First Packet
			}
            
            System.out.println("Sum of indices: " + totalIndex);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    private boolean isOrdered(String l1, String l2) {
        if(l1.length() == 0 && l2.length() >= 0) {
            return true;
        }

        if(l1.startsWith(",") && !l2.startsWith(",")) {
            return false;
        } else if(l1.startsWith(",")) {
            l1 = l1.substring(1);
            l2 = l2.substring(1);
        }

        // Check for start of list
        if(l1.charAt(0) == '[') {
            if(l2.charAt(0) == '[') {
                return isOrdered(l1.substring(1), l2.substring(1));
            } else {
                // Check for numbers first
                if(startsWithInteger(l2)) {
                    String newL2 = replaceWithBracketed(0, l2);
                    return isOrdered(l1.substring(1), newL2.substring(1));
                } else if(l2.charAt(0) == ']') {
                    // Right side ran out
                    return false;
                }
            }
        } else if(startsWithInteger(l1)) {
            if(l2.charAt(0) == '[') {
                String newL1 = replaceWithBracketed(0, l1);
                return isOrdered(newL1.substring(1), l2.substring(1));
            } else {
                // Check for numbers first
                if(startsWithInteger(l2)) {
                    int l1Int = Integer.parseInt(l1.split("[^\\d]")[0]);            
                    int l2Int = Integer.parseInt(l2.split("[^\\d]")[0]);        
                    if(l1Int > l2Int) {
                        // l1 is larger, so exit
                        return false;
                    } else if(l1Int < l2Int) {
                        return true;
                    } else {
                        // Prune the numbers and continue
                        String newL1 = l1.replaceFirst(l1Int + "","");
                        String newL2 = l2.replaceFirst(l2Int + "", "");
                        if(newL1.startsWith(",")) {
                            newL1 = newL1.substring(1);
                        }
                        if(newL2.startsWith(",")) {
                            newL2 = newL2.substring(1);
                        }
                        return isOrdered(newL1, newL2);
                    }
                } else if(l2.charAt(0) == ']') {
                    // Right side ran out
                    return false;
                }
            }
        } else if(l1.charAt(0) == ']') {
            if(l2.charAt(0) == ']') {
                return isOrdered(l1.substring(1), l2.substring(1));
            }
        }

        return true;
    }

    private String replaceWithBracketed(int index, String string) {
        Pattern pattern = Pattern.compile("(.{" + index +"})([^,\\]]+)");
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()) {
            String newString = matcher.replaceFirst("$1[$2]");
            return newString;
        }

        return string;
    }

    private boolean startsWithInteger(String val) {
        return val.matches("^\\d.*");
    }

    public void runScenarioB() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();

            List<String> packets = new ArrayList<>();

			while (line != null) {
                if(!line.isBlank()) {
                    packets.add(line);
                }
                line = reader.readLine(); // Second Packet
			}

            packets.add("[[2]]");
            packets.add("[[6]]");

            Collections.sort(packets, new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    if(o1.equals(o2)) {
                        return 0;
                    } if(isOrdered(o1,o2)) {
                        return -1;
                    } else {
                        return 1;
                    }

                }
            });

            for(String packet : packets) {
                System.out.println(packet);
            }

            System.out.println("Decoder key: " + (packets.indexOf("[[2]]") + 1) * (packets.indexOf("[[6]]") + 1));

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day13a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day13a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day13a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day13a-input.txt");
        mainB.runScenarioB();

    }
}
