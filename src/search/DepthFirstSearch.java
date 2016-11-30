package search;

import java.util.Stack;

import main.Node;
import main.Problem;
import main.Search;

public class DepthFirstSearch extends Search {	
	
	@Override
	public Node findSolution(Problem p) {
		//initialise solution
		Node solution = null;
		boolean solutionFound = false;
		
		//initialise stack
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(p.getStartState());
		
		//search until solution found or tree exhausted
		while(!fringe.empty() && !solutionFound){
			Node n = fringe.pop();			
			
//			System.out.println(n.toString());
			
			if(p.isGoalState(n)){
				solutionFound = true;
				solution = n;
			}
				
			for(Node child : p.getChildren(n)){
				fringe.push(child);			
			}			
		}
		
		return solution;		
	}

}
