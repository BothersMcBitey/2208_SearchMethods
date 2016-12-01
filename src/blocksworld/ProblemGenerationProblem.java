package blocksworld;

import main.Node;

public class ProblemGenerationProblem extends BlocksworldProblem {

	public ProblemGenerationProblem(int width, int height, Node startState, Node goalState) {
		super(width, height, startState, goalState);
	}
	
	@Override
	public boolean isGoalState(Node n){
		BlocksworldNode node = (BlocksworldNode) n;
		int goal = ((ProblemGenerationNode) goalState).getGoalDepth();
		
		if(calculateNodeProblemDepth(node) == goal){
			return true;
		} else {
			return false;
		}
	}

	private int calculateNodeProblemDepth(BlocksworldNode n){
		//Manhatten distance
		Position a = n.getA();
		Position b = n.getB();
		Position c = n.getC();
		int depth = calculateNodeCost(a, b, c);
		
		return depth;
	}
	
	@Override
	protected int calculateNodeCost(Position a, Position b, Position c){
		BlocksworldNode goal = (BlocksworldNode) startState;
		int cost = 0;
		
		cost += Math.abs(a.getX() - goal.getA().getX());
		cost += Math.abs(a.getY() - goal.getA().getY());
		cost += Math.abs(b.getX() - goal.getB().getX());
		cost += Math.abs(b.getY() - goal.getB().getY());
		cost += Math.abs(c.getX() - goal.getC().getX());
		cost += Math.abs(c.getY() - goal.getC().getY()); 
		
		return cost;
	}
}
