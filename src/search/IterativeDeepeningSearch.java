package search;

import java.util.ArrayList;
import java.util.Stack;

import main.AnimatedGifEncoder;
import main.Node;
import main.Problem;
import main.Result;
import main.Search;
import main.SimpleRenderer;

public class IterativeDeepeningSearch extends Search {

	@Override
	public Result findSolution(Problem p) {
		System.out.println("Starting Iterative Deepening Search...");
		//initialise solution	
		boolean solutionFound = false;
		Node solution = null;
		int depth = -1;		
		int nodesExpanded = 0;
		
		try{
			SimpleRenderer r = new SimpleRenderer(1000, p, false);
			ArrayList<Node> list = new ArrayList<Node>();
			
			while(!solutionFound && (depth <= p.getTreeMaxDepth())){
				depth++;				
				
				//initialise stack
				Stack<Node> fringe = new Stack<Node>();
				fringe.push(p.getStartState());
				
				//search until solution found or tree exhausted
				while(!fringe.empty() && !solutionFound){
					Node n = fringe.pop();	
					
					list.add(n);
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
			
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			e.start("ids_simple.gif");
			int delay = Math.max( 1 , (5000 / list.size()));
			e.setDelay(delay); 
			e.addFrame(r.startFrame(false));
			for(Node n : list){
//				e.addFrame(r.generateFrame(n,true));
				e.addFrame(r.generateFrame(n,false));
			}
			e.addFrame(r.startFrame(true));
			e.finish();
			
		} catch (OutOfMemoryError e){
			System.out.println("IDS ran out of memory, aborting search.");
		}
		
		System.out.println("Nodes expanded: " + nodesExpanded);
		System.out.println("Done.");
		
		return new Result(solution, nodesExpanded, p.getDepth());
	}

}
