package blocksworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Node;
import main.Problem;

public class SimpleProblem extends Problem {

	protected int width, height;

	public SimpleProblem(int width, int height, Node startState, Node goalState) {
		super(startState, goalState);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean isGoalState(Node n){
		SimpleNode node = (SimpleNode) n;
		SimpleNode goal = (SimpleNode) goalState;
		
		if((goal.getA().equals(node.getA()))){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Node> getChildren(Node n) {
		SimpleNode node = (SimpleNode) n;
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
	
	protected SimpleNode Move(SimpleNode n, Direction d){
		Position newA = n.getA();
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
		int cost = calculateNodeCost(newA);
		cost += n.getDepth();
		//return new node
		return new SimpleNode(n, cost, newA, newAgent);
	}
	
	protected int calculateNodeCost(Position a){
		SimpleNode goal = (SimpleNode) goalState;
		int cost = 0;
		
		cost += Math.abs(a.getX() - goal.getA().getX());
		cost += Math.abs(a.getY() - goal.getA().getY());
		
		return cost;
	}
	
	//Manhatten distance of block plus distance from agent to closest block
	@Override
	public int getDepth() {
		SimpleNode n = (SimpleNode) startState;
		
		//Manhatten distance
		Position a = n.getA();
		int depth = calculateNodeCost(a);
		
		//Agent distance
		Position agent = n.getAgent();
		int dA = Math.abs(agent.getX() - a.getX()) + Math.abs(agent.getY() - a.getY());
		depth += dA;
		
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
		
		Position a, b, agent;
		
		a = new Position(1, 1);
		b = new Position(1, 0);
				
		agent = new Position(0, 0);
		goals.add(new SimpleNode(null, 0, a,  agent));	
		agent = new Position(0, 1);
		goals.add(new SimpleNode(null, 0, a,  agent));
		agent = new Position(0, 2);
		goals.add(new SimpleNode(null, 0, a,  agent));
		agent = new Position(1, 3);
		goals.add(new SimpleNode(null, 0, a, agent));
		agent = new Position(2, 0);
		goals.add(new SimpleNode(null, 0, a,  agent));
		agent = new Position(2, 1);
		goals.add(new SimpleNode(null, 0, a,  agent));
		agent = new Position(2, 2);							
		goals.add(new SimpleNode(null, 0, a,  agent));
		
		return goals;
	}

}
