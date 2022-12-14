package com.stout.day12;

import java.util.HashSet;
import java.util.Set;

public class Node {
    public final static Set<Node> nodes = new HashSet<>();

    private Set<Node> links;
    private int row;
    private int col;
    private char height;
    private int depth;

    public Node(int row, int col, char height, int depth) {
        this.links = new HashSet<>();
        this.row = row;
        this.col = col;
        this.height = height;
        this.depth = depth;
    }

    public Node getNode(String[][] field, String direction) {
        int[] coords = new int[] { this.row, this.col };
        switch(direction) {
            case "North":
                if(coords[0] == 0) {
                    return null;
                } else {
                    int newRow = coords[0]-1;
                    int newCol = coords[1];
                    char height = field[newRow][newCol].charAt(0);
                    if(this.height >= height - 1) {
                        return new Node(newRow, newCol, height, this.depth + 1);
                    } else {
                        return null;
                    }
                }
            case "South":
                if(coords[0] == field.length - 1) {
                    return null;
                } else {
                    return new Node(coords[0]+1, coords[1], field[coords[0]+1][coords[1]], this.depth + 1);
                }
            case "West":
                if(coords[1] == 0) {
                    return null;
                } else {
                    return new Node(coords[0], (coords[1]-1), field[coords[0]][coords[1]-1], this.depth + 1);
                }
            case "East":
                if(coords[1] == field[0].length - 1) {
                    return null;
                } else {
                    return new Node(coords[0], (coords[1]+1), field[coords[0]][coords[1]+1], this.depth + 1);
                }
            default:
                throw new RuntimeException("Boom");
        }
    }

    public void addLink(Node link) {
        this.links.add(link);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getHeight() {
        return height;
    }

    public int getDepth() {
        return this.depth;
    }

    public String getKey() {
        return this.row + "-" + this.col;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + row;
        result = prime * result + col;
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (row != other.row)
            return false;
        if (col != other.col)
            return false;
        if (height == null) {
            if (other.height != null)
                return false;
        } else if (!height.equals(other.height))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Node [links=" + links + ", row=" + row + ", col=" + col + ", height=" + height + ", depth=" + depth
                + "]";
    }

    
}
