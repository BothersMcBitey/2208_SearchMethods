package main;

import blocksworld.BlocksworldNode;
import blocksworld.BlocksworldProblem;
import search.DepthFirstSearch;

public class Main {

	public static void main(String[] args) {
		BlocksworldNode startState = new BlocksworldNode(new Path(), new Position(0, 0), 
				new Position(1, 0), new Position(2, 0), new Position(3, 0));
		BlocksworldNode goalState = new BlocksworldNode(new Path(), new Position(1, 2), 
				new Position(1, 1), new Position(1, 0), new Position(0, 0));
		Problem p = new BlocksworldProblem(startState, goalState);
		
		Node s = new DepthFirstSearch().findSolution(p);
		
		s.getPath().toString();
	}

}