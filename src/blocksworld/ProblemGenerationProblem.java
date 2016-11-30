package blocksworld;

import main.Node;
import main.Position;

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
		
		//Agent distance
		Position agent = n.getAgent();
		int dA = Math.abs(agent.getX() - a.getX()) + Math.abs(agent.getY() - a.getY());
		int dB = Math.abs(agent.getX() - b.getX()) + Math.abs(agent.getY() - b.getY());
		int dC = Math.abs(agent.getX() - c.getX()) + Math.abs(agent.getY() - c.getY());
		depth += Math.min(dA, Math.min(dB, dC));
		
		return depth;
	}
}
