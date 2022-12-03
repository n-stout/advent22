package com.stout.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Day2 
{
    private Map<String, Function<String,Integer>> calculator1 = new HashMap<>();

    private final static int ROCK_SCORE = 1;
    private final static int PAPER_SCORE = 2;
    private final static int SCISSORS_SCORE = 3;

    private final static int LOSE_SCORE = 0;
    private final static int TIE_SCORE = 3;
    private final static int WIN_SCORE = 6;

    public static void main( String[] args )
    {
        Day2 day2 = new Day2();
        System.out.println(day2.resultsFirst());
        System.out.println(day2.resultsSecond());
    }

    public Day2() {
        this.calculator1 = new HashMap<>();
    }

    public int resultsFirst() {
        this.calculator1.put("A", (response) -> { // Rock case
            switch(response) {
                /* shape + outcome */
                case "Z": // Scissors
                    return SCISSORS_SCORE + LOSE_SCORE;
                case "Y": // Paper
                    return PAPER_SCORE + WIN_SCORE;
                case "X": // Rock
                    return ROCK_SCORE + TIE_SCORE;
                default:
                    throw new RuntimeException();
            }
        });

        this.calculator1.put("B", (response) -> { // Paper case
            switch(response) {
                /* shape + outcome */
                case "Z": // Scissors
                    return SCISSORS_SCORE + WIN_SCORE;
                case "Y": // Paper
                    return PAPER_SCORE + TIE_SCORE;
                case "X": // Rock
                    return ROCK_SCORE + LOSE_SCORE;
                default:
                    throw new RuntimeException();
            }
        });

        this.calculator1.put("C", (response) -> { // Scissors case
            switch(response) {
                /* shape + outcome */
                case "Z": // Scissors
                    return SCISSORS_SCORE + TIE_SCORE;
                case "Y": // Paper
                    return PAPER_SCORE + LOSE_SCORE;
                case "X": // Rock
                    return ROCK_SCORE + WIN_SCORE;
                default:
                    throw new RuntimeException();
            }
        });
        
        int total = 0;
		try(BufferedReader reader = new BufferedReader(new FileReader("inputs/day2-input.txt"))) {
			String line = reader.readLine();

			while (line != null) {
                String[] split = line.split(" ");
                int points = this.calculator1.get(split[0]).apply(split[1]);
                total += points;
                System.out.println("Entry: " + split[0] + " " + split[1] + ", got " + points + " points; new total score: " + total);
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return total;
    }

    public int resultsSecond() {
        this.calculator1.put("A", (response) -> { // Rock case
            switch(response) {
                /* shape + outcome */
                case "Z": // Need to win
                    return PAPER_SCORE + WIN_SCORE;
                case "Y": // Need to draw
                    return ROCK_SCORE + TIE_SCORE;
                case "X": // Need to lose
                    return SCISSORS_SCORE + LOSE_SCORE;
                default:
                    throw new RuntimeException();
            }
        });

        this.calculator1.put("B", (response) -> { // Paper case
            switch(response) {
                /* shape + outcome */
                case "Z": // Need to win
                    return SCISSORS_SCORE + WIN_SCORE;
                case "Y": // Need to draw
                    return PAPER_SCORE + TIE_SCORE;
                case "X": // Need to lose
                    return ROCK_SCORE + LOSE_SCORE;
                default:
                    throw new RuntimeException();
            }
        });

        this.calculator1.put("C", (response) -> { // Scissors case
            switch(response) {
                /* shape + outcome */
                case "Z": // Scissors
                    return ROCK_SCORE + WIN_SCORE;
                case "Y": // Need to draw
                    return SCISSORS_SCORE + TIE_SCORE;
                case "X": // Need to lose
                    return PAPER_SCORE + LOSE_SCORE;
                default:
                    throw new RuntimeException();
            }
        });
        
        int total = 0;
		try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			String line = reader.readLine();

			while (line != null) {
                String[] split = line.split(" ");
                int points = this.calculator1.get(split[0]).apply(split[1]);
                total += points;
                System.out.println("Entry: " + split[0] + " " + split[1] + ", got " + points + " points; new total score: " + total);
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return total;
    }
}
