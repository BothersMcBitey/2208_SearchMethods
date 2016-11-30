package main;

public abstract class Node {
	
	private Path path;
	private int cost;
	
	protected Node(Path path, int cost){
		this.path = path;
		this.cost = cost;
	}

	public abstract boolean equals(Node n);
	
	@Override
	public abstract String toString();

	public Path getPath() {
		return path;
	}
	
	public int getCost(){
		return cost;
	}
}
