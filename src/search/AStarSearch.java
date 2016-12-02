package search;

import java.util.ArrayList;
import java.util.Set;

import main.AnimatedGifEncoder;
import main.Node;
import main.Problem;
import main.Result;
import main.Search;
import main.SimpleRenderer;

public class AStarSearch extends Search {

	@Override
	public Result findSolution(Problem p) {
		System.out.println("Starting A* Search...");
		//initialise solution
		Node solution = null;
		boolean solutionFound = false;
		int nodesExpanded = 0;
		
		//initialise fringe
		Set<Node> fringe = new SearchSet<Node>();
		fringe.add(p.getStartState());			
		try{
			SimpleRenderer r = new SimpleRenderer(1000, p, true);
			ArrayList<Node> list = new ArrayList<Node>();
			
			//search until solution found or tree exhausted
			while(!fringe.isEmpty() && !solutionFound){
				Node n = null;
				int currentCost = Integer.MAX_VALUE;
				for(Node node : fringe){
					if(node.getCost() < currentCost){
						n = node;
						currentCost = n.getCost();				
					} else if(n == null){
						n = node;
					}
				}
				
//				System.out.println("Fringe: " + fringe.size() + "\n" + "current cost: " + currentCost + "\n" + "nodes expanded: " + nodesExpanded);
				
				fringe.remove(n);
				nodesExpanded++;		
				
				list.add(n);
				
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
			e.start("ass_simple.gif");
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
			System.out.println("A* ran out of memory, aborting search.");
		}
		
		System.out.println("Nodes expanded: " + nodesExpanded);		
		System.out.println("Done.");
		
		return new Result(solution, nodesExpanded, p.getDepth());		
	}

}
