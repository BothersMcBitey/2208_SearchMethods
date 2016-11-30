package main;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {

	private Queue<Node> solutionPath;
	
	public Solution(){
		solutionPath = new LinkedList<Node>();
	}
	
	public void addNode(Node n){
		solutionPath.add(n);
	}
	
	public Node getNextNode(){
		return solutionPath.remove();
	}
	
	public void printSolution(){
		while(!solutionPath.isEmpty()){
			System.out.println(solutionPath.remove().x);
		}
	}
}
