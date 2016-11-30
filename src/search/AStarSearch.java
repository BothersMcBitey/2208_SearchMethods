package search;

import java.util.HashSet;
import java.util.Set;

import main.Node;
import main.Problem;
import main.Search;

public class AStarSearch extends Search {

	@Override
	public Node findSolution(Problem p) {
		//initialise solution
		Node solution = null;
		boolean solutionFound = false;
		
		//initialise fringe
		Set<Node> fringe = new HashSet<Node>();
		fringe.add(p.getStartState());
		
		//search until solution found or tree exhausted
		while(!fringe.isEmpty() && !solutionFound){
			Node n = null;
			int currentCost = Integer.MAX_VALUE;
			for(Node node : fringe){
				if(node.getCost() < currentCost){
					n = node;
					currentCost = n.getCost();
				}
			}
			
//			System.out.println(n.toString());
			
			if(p.isGoalState(n)){
				solutionFound = true;
				solution = n;
			}
				
			for(Node child : p.getChildren(n)){
				fringe.add(child);			
			}			
		}
		
		return solution;		
	}

}
