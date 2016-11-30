package blocksworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Node;
import main.Path;
import main.Position;
import main.Problem;

public class BlocksworldProblem extends Problem {

	private int width = 4, height = 4;
	
	private enum Direction{
		UP, DOWN, LEFT, RIGHT
	}

	public BlocksworldProblem(Node startState, Node goalState) {
		super(startState, goalState);
	}
	
	@Override
	public boolean isGoalState(Node n){
		BlocksworldNode node = (BlocksworldNode) n;
		BlocksworldNode goal = (BlocksworldNode) goalState;
		
		if((goal.getA() == node.getA()) && (goal.getB() == node.getB())
				&& (goal.getC() == node.getC())){
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
	
	private BlocksworldNode Move(BlocksworldNode n, Direction d){
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

		//Add previous node to path
		Path path = n.getPath();
		path.addNode(n);
		//calculate cost
		int cost = calculateNodeCost(newA, newB, newC);
		//return new node
		return new BlocksworldNode(path, cost, newA, newB, newC, newAgent);
	}
	
	private int calculateNodeCost(Position a, Position b, Position c){
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

}
