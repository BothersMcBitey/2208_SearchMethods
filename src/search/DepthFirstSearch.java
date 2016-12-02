package search;

import java.util.Stack;

import main.Node;
import main.Problem;
import main.Result;
import main.Search;

public class DepthFirstSearch extends Search {	
	
	@Override
	public Result findSolution(Problem p) {			
		System.out.println("Starting Depth First Search...");
		//initialise solution
		Node solution = null;
		boolean solutionFound = false;
		int nodesExpanded = 0;
		
		//initialise stack
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(p.getStartState());
				
		try{
			//search until solution found or tree exhausted
			while(!fringe.empty() && !solutionFound){
				Node n = fringe.pop();
				
				nodesExpanded++;

				if(p.isGoalState(n)){
					solutionFound = true;
					solution = n;
				}
					
				for(Node child : p.getChildren(n)){
					fringe.push(child);			
				}			
			}
		} catch (OutOfMemoryError e){
			System.out.println("DFS ran out of memory, aborting search.");
		}
		
		System.out.println("Nodes expanded: " + nodesExpanded);
		System.out.println("Done.");
		
		return new Result(solution, nodesExpanded, p.getDepth());			
	}

}
