package blocksworld;

import main.Node;

public class ProblemGenerationNode extends Node {

	private int goalDepth;
	
	public ProblemGenerationNode(int goalDepth) {
		super(null, 0);
		this.goalDepth = goalDepth;
	}

	public int getGoalDepth(){
		return goalDepth;
	}
	
	@Override
	public boolean equals(Node n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
