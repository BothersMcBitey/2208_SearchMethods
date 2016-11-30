package main;

import java.util.List;

public abstract class Problem {

	private Node startState, goalState;
	
	public Problem(Node startState, Node goalState){
		this.startState = startState;
		this.goalState = goalState;
	}
	public abstract List<Node> getChildren(Node n);
	
	public Node getGoalState() {
		return goalState;
	}
	public Node getstartState(){
		return startState;
	}
}