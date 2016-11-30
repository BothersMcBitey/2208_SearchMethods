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
	
	//Return a numerical measure of how complex the problem is from the given start state
	public abstract int getDepth();
	
	public Node getGoalState() {
		return goalState;
	}
	public Node getStartState(){
		return startState;
	}
}