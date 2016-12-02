package blocksworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Node;
import main.Problem;

public class BlocksworldProblem extends Problem {

	protected int width, height;

	public BlocksworldProblem(int width, int height, Node startState, Node goalState) {
		super(startState, goalState);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean isGoalState(Node n){
		BlocksworldNode node = (BlocksworldNode) n;
		BlocksworldNode goal = (BlocksworldNode) goalState;
		
		if((goal.getA().equals(node.getA())) && (goal.getB().equals(node.getB()))
				&& (goal.getC().equals(node.getC()))){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Node> getChildren(Node n) {
		BlocksworldNode node = (BlocksworldNode) n;
		List<Node> children = new ArrayList<Node>();
		
		for(Direction d : Direction.values()){
			Node child = Move(node, d);
			if(child != null){
				children.add(child);
			}
		}
		
		Collections.shuffle(children);
		
		return children;
	}
	
	protected BlocksworldNode Move(BlocksworldNode n, Direction d){
		Position newA = n.getA();
		Position newB = n.getB();
		Position newC = n.getC();
		Position newAgent = null;
		
		switch (d) {
		case UP:
			//If not at top of world
			if((n.getAgent().getY() + 1) < height){
				//Move agent
				newAgent = new Position(n.getAgent().getX(), n.getAgent().getY() + 1);		
				//Look for blocks above move them if there
				if(n.getA().equals(newAgent)){
					newA = new Position(n.getA().getX(), n.getA().getY() - 1);
				} else if(n.getB().equals(newAgent)){
					newB = new Position(n.getB().getX(), n.getB().getY() - 1);
				} else if(n.getC().equals(newAgent)){
					newC = new Position(n.getC().getX(), n.getC().getY() - 1);
				}				
			} else {
				//invalid move, return null
				return null;
			}
			break;

		case DOWN:
			//If not at bottom of world
			if((n.getAgent().getY() - 1) >= 0){
				//Move agent
				newAgent = new Position(n.getAgent().getX(), n.getAgent().getY() - 1);						
				//Look for blocks below, move them if there
				if(n.getA().equals(newAgent)){
					newA = new Position(n.getA().getX(), n.getA().getY() + 1);
				} else if(n.getB().equals(newAgent)){
					newB = new Position(n.getB().getX(), n.getB().getY() + 1);
				} else if(n.getC().equals(newAgent)){
					newC = new Position(n.getC().getX(), n.getC().getY() + 1);
				}
				
			} else {
				//invalid move, return null
				return null;
			}
			break;
			
		case LEFT:
			//If not at left edge of world
			if((n.getAgent().getX() - 1) >= 0){
				//Move agent
				newAgent = new Position(n.getAgent().getX() - 1, n.getAgent().getY());						
				//Look for blocks below, move them if there
				if(n.getA().equals(newAgent)){
					newA = new Position(n.getA().getX() + 1, n.getA().getY());
				} else if(n.getB().equals(newAgent)){
					newB = new Position(n.getB().getX() + 1, n.getB().getY());
				} else if(n.getC().equals(newAgent)){
					newC = new Position(n.getC().getX() + 1, n.getC().getY());
				}
				
			} else {
				//invalid move, return null
				return null;
			}
			break;
			
		case RIGHT:
			//If not at right edge of world
			if((n.getAgent().getX() + 1) < width){
				//Move agent
				newAgent = new Position(n.getAgent().getX() + 1, n.getAgent().getY());						
				//Look for blocks below, move them if there
				if(n.getA().equals(newAgent)){
					newA = new Position(n.getA().getX() - 1, n.getA().getY());
				} else if(n.getB().equals(newAgent)){
					newB = new Position(n.getB().getX() - 1, n.getB().getY());
				} else if(n.getC().equals(newAgent)){
					newC = new Position(n.getC().getX() - 1, n.getC().getY());
				}
				
			} else {
				//invalid move, return null
				return null;
			}
			break;

		default:
			//invalid move, return null
			return null;
		}

		//calculate cost
		int cost = calculateNodeCost(newA, newB, newC);
		cost += n.getDepth();
		//return new node
		return new BlocksworldNode(n, cost, newA, newB, newC, newAgent);
	}
	
	protected int calculateNodeCost(Position a, Position b, Position c){
		BlocksworldNode goal = (BlocksworldNode) goalState;
		int cost = 0;
		
		cost += Math.abs(a.getX() - goal.getA().getX());
		cost += Math.abs(a.getY() - goal.getA().getY());
		cost += Math.abs(b.getX() - goal.getB().getX());
		cost += Math.abs(b.getY() - goal.getB().getY());
		cost += Math.abs(c.getX() - goal.getC().getX());
		cost += Math.abs(c.getY() - goal.getC().getY()); 
		
		return cost;
	}
	
	//Manhatten distance of block plus distance from agent to closest block
	@Override
	public int getDepth() {
		BlocksworldNode n = (BlocksworldNode) startState;
		
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

	@Override
	public int getTreeMaxDepth() {
		//tree is infinite, so this is just to stop integer overflows
		return Integer.MAX_VALUE;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public List<Node> enumerateGoalStates() {
		List<Node> goals = new ArrayList<Node>();
		
		Position a, b, c, agent;
		
		a = new Position(1, 2);
		b = new Position(1, 1);
		c = new Position(1, 0);
				
		agent = new Position(0, 0);
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));	
		agent = new Position(0, 1);
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));
		agent = new Position(0, 2);
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));
		agent = new Position(1, 3);
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));
		agent = new Position(2, 0);
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));
		agent = new Position(2, 1);
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));
		agent = new Position(2, 2);							
		goals.add(new BlocksworldNode(null, 0, a, b, c, agent));
		
		return goals;
	}

}
