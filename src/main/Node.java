package main;

public abstract class Node {
	
	private Path path;
	
	protected Node(Path path){
		this.path = path;
	}

	public abstract boolean equals(Node n);
	
	@Override
	public abstract String toString();

	public Path getPath() {
		return path;
	}
}
