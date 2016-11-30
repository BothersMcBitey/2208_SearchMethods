package main;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Node {
	
	private int cost;
	private Node parent;
	private int depth;
	
	protected Node(Node parent, int cost){
		this.cost = cost;
		this.parent = parent;
		if(parent == null){
			depth = 0;
		}else {
			depth = parent.getDepth() + 1;
		}
	}

	public abstract boolean equals(Node n);
	
	@Override
	public abstract String toString();

	public Queue<Node> getPath() {
		Queue<Node> path = new LinkedList<Node>();
		
		path.add(this);
		
		Node parent = this.parent;		
		while(parent != null){
			path.add(parent);
			parent = parent.getParent();
		}
		
		return path;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public int getCost(){
		return cost;
	}
	
	public int getDepth(){
		return depth;
	}
}
