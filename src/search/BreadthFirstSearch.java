package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import main.AnimatedGifEncoder;
import main.Node;
import main.Problem;
import main.Result;
import main.Search;
import main.SimpleRenderer;

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
			SimpleRenderer r = new SimpleRenderer(1000, p, false);
			ArrayList<Node> list = new ArrayList<Node>();
			
			//search until solution found or tree exhausted
			while(!fringe.isEmpty() && !solutionFound){
				Node n = fringe.remove();	
				
				list.add(n);
				nodesExpanded++;
				
				if(p.isGoalState(n)){
					solutionFound = true;
					solution = n;
				}
					
				for(Node child : p.getChildren(n)){
					fringe.add(child);			
				}			
			}
			
			System.out.println("Nodes expanded: " + nodesExpanded);
			
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			e.start("bfs_simple.gif");
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
			System.out.println("BFS ran out of memory, aborting search.");
		}
		
		System.out.println("Nodes expanded: " + nodesExpanded);
		System.out.println("Done.");
		
		return new Result(solution, nodesExpanded, p.getDepth());	
	}

}
