package search;

import java.util.LinkedList;
import java.util.Queue;

import main.Node;
import main.Problem;
import main.Result;
import main.Search;

public class BreadthFirstSearch extends Search {	
	
	@Override
	public Result findSolution(Problem p) {	
		System.out.println("Starting Breadth First Search...");
		//initialise search
		Queue<Node> fringe = new LinkedList<Node>();
		fringe.add(p.getStartState());
		boolean solutionFound = false;
		Node solution = null;
		int nodesExpanded = 0;
		
		try{
			//search until solution found or tree exhausted
			while(!fringe.isEmpty() && !solutionFound){
				Node n = fringe.remove();	
				
				nodesExpanded++;
				
				if(p.isGoalState(n)){
					solutionFound = true;
					solution = n;
				}
					
				for(Node child : p.getChildren(n)){
					fringe.add(child);			
				}			
			}
		} catch (OutOfMemoryError e){
			System.out.println("BFS ran out of memory, aborting search.");
		}
		
		System.out.println("Nodes expanded: " + nodesExpanded);
		System.out.println("Done.");
		
		return new Result(solution, nodesExpanded, p.getDepth());	
	}

}
