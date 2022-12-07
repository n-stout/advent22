package com.stout.day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Scenario {

    private File inputFile;

    public Scenario(String inputFile) {
        this.inputFile = new File(inputFile);
    }

    public void runScenarioA() {
        Map<String,Directory> directories = new HashMap<>();
        Stack<String> directoryPath = new Stack<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
            Directory currentDir = new Directory("/");
            String currentPath = "";
            directories.put("", currentDir);
            directoryPath.push(currentPath);
            boolean listingFiles = false;

			while (line != null) {
                if(line.startsWith("$ ")) {
                    listingFiles = false;
                    // Command
                    String command = line.substring(2);
                    if(command.startsWith("cd")) {
                        String dir = command.substring(3);
                        switch(dir) {
                            case "..":
                                directoryPath.pop();
                                if(directoryPath.isEmpty()) {
                                    directoryPath.push("");
                                }

                                break;
                            case "/":
                                directoryPath.clear();
                                directoryPath.push("");
                                break;
                            default:
                                directoryPath.push(dir);
                                String name = directoryPath.stream().collect(Collectors.joining("/")) + "/" + dir;
                                directories.putIfAbsent(name, new Directory(name));
                                currentDir.getDirectories().add(directories.get(name));
                                
                        }
                        currentPath = directoryPath.stream().collect(Collectors.joining("/"));
                        currentDir = directories.get(currentPath);
                        System.out.println("Changing to " + currentPath);
                    }
                } else {
                    if(line.startsWith("dir")) {
                        String listedDir = currentPath + "/" + line.substring(4);
                        Directory directory = new Directory(listedDir);
                        directories.putIfAbsent(listedDir, directory);
                        currentDir.getDirectories().add(directories.get(listedDir));
                    } else {
                        String[] splitFile = line.split(" ");
                        int size = Integer.parseInt(splitFile[0]);
                        currentDir.getFiles().put(splitFile[1], size);
                    }
                }
                
				line = reader.readLine();
			}

            int sum = directories.values().stream().filter( d -> d.getSize() <= 100_000)
                .map(d -> d.getSize()).reduce(0, Integer::sum);

            System.out.println("Total sum: " + sum);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void runScenarioB() {
        Map<String,Directory> directories = new HashMap<>();
        Stack<String> directoryPath = new Stack<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			String line = reader.readLine();
            Directory currentDir = new Directory("/");
            String currentPath = "";
            directories.put("", currentDir);
            directoryPath.push(currentPath);

			while (line != null) {
                if(line.startsWith("$ ")) {
                    // Command
                    String command = line.substring(2);
                    if(command.startsWith("cd")) {
                        String dir = command.substring(3);
                        switch(dir) {
                            case "..":
                                directoryPath.pop();
                                if(directoryPath.isEmpty()) {
                                    directoryPath.push("");
                                }

                                break;
                            case "/":
                                directoryPath.clear();
                                directoryPath.push("");
                                break;
                            default:
                                directoryPath.push(dir);
                                String name = directoryPath.stream().collect(Collectors.joining("/"));
                                directories.putIfAbsent(name, new Directory(name));
                                currentDir.getDirectories().add(directories.get(name));
                                
                        }
                        currentPath = directoryPath.stream().collect(Collectors.joining("/"));
                        currentDir = directories.get(currentPath);
                        System.out.println("Changing to " + currentPath);
                    }
                } else {
                    if(line.startsWith("dir")) {
                        String listedDir = currentPath + "/" + line.substring(4);
                        Directory directory = new Directory(listedDir);
                        directories.putIfAbsent(listedDir, directory);
                        currentDir.getDirectories().add(directories.get(listedDir));
                    } else {
                        String[] splitFile = line.split(" ");
                        int size = Integer.parseInt(splitFile[0]);
                        currentDir.getFiles().put(splitFile[1], size);
                    }
                }
                
				line = reader.readLine();
			}

            int totalSize = directories.get("").getSize();
            int totalDiskSpace = 70_000_000;
            int neededSpace = 30_000_000;
            int delta = neededSpace - (totalDiskSpace - totalSize);
            System.out.println("Total size on disk: " + totalSize);
            System.out.println("Need " + delta + " space");

            int minSize = directories.values().stream().map(d -> d.getSize()).filter(s -> s > delta).min(Integer::compare).get();
            System.out.println("Min size: " + minSize);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        Scenario testA = new Scenario("inputs/day7a-test.txt");
        testA.runScenarioA();
        Scenario mainA = new Scenario("inputs/day7a-input.txt");
        mainA.runScenarioA();

        Scenario testB = new Scenario("inputs/day7a-test.txt");
        testB.runScenarioB();
        Scenario mainB = new Scenario("inputs/day7a-input.txt");
        mainB.runScenarioB();

    }
}
