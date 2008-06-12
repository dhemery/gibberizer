package com.dhemery.gibberizer;

public class NGraph {
	private String digraph;
	private int occurrenceCount;
	
	public NGraph(String digraph) {
		this.digraph = digraph;
		occurrenceCount = 1;
	}

	@Override
	public boolean equals(Object o) {
		return digraph.equals(((NGraph)o).digraph);
	}

	public int getOccurrenceCount() {
		return occurrenceCount;
	}

	public void incrementOccurrenceCount() {
		occurrenceCount++;
	}
	
	public String toString() {
		return digraph;
	}
}
