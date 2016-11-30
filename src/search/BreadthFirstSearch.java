package search;

import java.util.LinkedList;
import java.util.Queue;

import main.Node;
import main.Problem;
import main.Search;

public class BreadthFirstSearch extends Search {	
	
	@Override
	public Node findSolution(Problem p) {		
		//initialise search
		Queue<Node> fringe = new LinkedList<Node>();
		fringe.add(p.getStartState());
		boolean solutionFound = false;
		Node solution = null;
		int expandedNodes = 0;
		
		//search until solution found or tree exhausted
		while(!fringe.isEmpty() && !solutionFound){
			Node n = fringe.remove();			
			expandedNodes++;
			if(p.isGoalState(n)){
				solutionFound = true;
				solution = n;
			}
				
			for(Node child : p.getChildren(n)){
				fringe.add(child);			
			}			
		}
		
		System.out.println("Nodes expanded: " + expandedNodes);
		
		return solution;		
	}

}
