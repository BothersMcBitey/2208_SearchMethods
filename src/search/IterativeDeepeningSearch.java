package search;

import java.util.Stack;

import main.Node;
import main.Problem;
import main.Result;
import main.Search;

public class IterativeDeepeningSearch extends Search {

	@Override
	public Result findSolution(Problem p) {
		//initialise solution	
		boolean solutionFound = false;
		Node solution = null;
		int depth = -1;		
		int nodesExpanded = 0;
		
		while(!solutionFound){
			depth++;
			
			//initialise stack
			Stack<Node> fringe = new Stack<Node>();
			fringe.push(p.getStartState());
			
			//search until solution found or tree exhausted
			while(!fringe.empty() && !solutionFound){
				Node n = fringe.pop();	
				
				nodesExpanded++;
				
				if(p.isGoalState(n)){
					solutionFound = true;
					solution = n;
				}
			
				//check children within depth
				if(n.getDepth() < depth){
					for(Node child : p.getChildren(n)){
						fringe.push(child);			
					}	
				}
			}
		}
		
		System.out.println("Nodes expanded: " + nodesExpanded);
		
		return new Result(solution, nodesExpanded, p.getDepth());
	}

}
