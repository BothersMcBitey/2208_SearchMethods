package main;

import java.util.List;

public abstract class Problem {

	protected Node startState, goalState;
	
	protected Problem(Node startState, Node goalState){
		this.startState = startState;
		this.goalState = goalState;
	}
	
	public abstract List<Node> getChildren(Node n);
	
	public abstract boolean isGoalState(Node n);
	
	public Node getGoalState() {
		return goalState;
	}
	public Node getStartState(){
		return startState;
	}
}