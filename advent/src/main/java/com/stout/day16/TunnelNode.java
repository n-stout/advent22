package com.stout.day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class TunnelNode {
    private Map<TunnelNode,Integer> childNodePaths;
    private int flowRate;
    private String name;

    public TunnelNode(String name, int flowRate) {
        this.flowRate = flowRate;
        this.childNodePaths = new HashMap<>();
        this.name = name;
    }

    public boolean reduceNodes() {
        boolean isChanged = false;
        for(Entry<TunnelNode,Integer> childEntry : new HashSet<>(childNodePaths.entrySet())) {
            TunnelNode child = childEntry.getKey();
            int distance = childEntry.getValue();

            if(child.flowRate == 0) {
                isChanged = true;
                childNodePaths.remove(child);
                for(Entry<TunnelNode,Integer> grandChild : child.childNodePaths.entrySet()) {
                    if(grandChild.getKey() == this) {
                        continue;
                    }
                    int newDist = grandChild.getValue()+distance;
                    childNodePaths.put(grandChild.getKey(), newDist);
                    grandChild.getKey().childNodePaths.put(this, newDist);
                }
            }
        }
        return isChanged;
    }

    public void addChild(TunnelNode node) {
        this.childNodePaths.put(node, 1);
    }

    @Override
    public String toString() {
        return "TunnelNode [flowRate=" + flowRate + ", name=" + name + "]";
    }

    
}
