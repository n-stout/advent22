package com.stout.day7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Directory {
    private Map<String,Integer> files;
    private Set<Directory> directories;
    private String name;

    public Directory(String name) {
        this.files = new HashMap<>();
        this.directories = new HashSet<>();
        this.name = name;
    }

    public Map<String, Integer> getFiles() {
        return files;
    }

    public Set<Directory> getDirectories() {
        return directories;
    }

    public Integer getSize() {
        int fileSum = files.values().stream().reduce(0, Integer::sum);
        int dirSum = directories.stream().map((d) -> d.getSize()).reduce(0, Integer::sum);
        return fileSum + dirSum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Directory other = (Directory) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Directory [name=" + name + ", size= " + getSize() + ", files=" + files.keySet() + "]";
    }
}
