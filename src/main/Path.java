package main;

import java.util.LinkedList;
import java.util.Queue;

public class Path {

	private Queue<Node> path;
	
	public Path(){
		path = new LinkedList<Node>();
	}
	
	public void addNode(Node n){
		path.add(n);
	}
	
	public Node getNextNode(){
		return path.remove();
	}
	
	public void printSolution(){
		while(!path.isEmpty()){
			System.out.println(path.remove().toString());
		}
	}
}
