package com.stout.day11;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class Monkey {

    public static Map<Integer, Monkey> monkies = new TreeMap<>();

    private int id;
    private Queue<BigInteger> itemWorryLevels;

    private Function<BigInteger, BigInteger> itemInspectionFunc;
    private Function<BigInteger, Integer> targetMonkeyFunc;
    private int inspectionCount;
    private int worryMod;

    public Monkey(int id, int worryMod, List<Long> initialWorryLevels, Function<BigInteger, BigInteger> itemInspectFunc,
            Function<BigInteger, Integer> targetMonkeyFunc) {
        this.id = id;
        this.itemWorryLevels = new LinkedBlockingQueue<>();
        for(Long initialWorry : initialWorryLevels) {
            this.itemWorryLevels.add(new BigInteger("" + initialWorry));
        }
        this.itemInspectionFunc = itemInspectFunc;
        this.targetMonkeyFunc = targetMonkeyFunc;
        this.inspectionCount = 0;
        this.worryMod = worryMod;
    }

    /**
     * @return true when more to inspect, otherwise false
     */
    public boolean inspect() {
        if (!itemWorryLevels.isEmpty()) {
            BigInteger item = itemWorryLevels.poll();
            item = itemInspectionFunc.apply(item); // Increase worry levels
            item = item.divide(new BigInteger("" + worryMod)); // Gets bored
            Integer targetMonkey = targetMonkeyFunc.apply(item);
            monkies.get(targetMonkey).getItemWorryLevels().add(item);
            inspectionCount++;
        }

        return !itemWorryLevels.isEmpty();
    }

    public int getId() {
        return id;
    }

    public Queue<BigInteger> getItemWorryLevels() {
        return itemWorryLevels;
    }

    public int getInspectionCount() {
        return this.inspectionCount;
    }

    public int getWorryMod() {
        return this.worryMod;
    }

    public Function<BigInteger, BigInteger> getItemWorryFunc() {
        return this.itemInspectionFunc;
    }

    public void setItemWorryFunc(Function<BigInteger, BigInteger> func) {
        this.itemInspectionFunc = func;
    }

    @Override
    public String toString() {
        return "Monkey [id=" + id + ", inspectionCount=" + inspectionCount
                + "]";
    }

}
