package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import blocksworld.BlocksworldNode;
import blocksworld.BlocksworldProblem;
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.DepthFirstSearch;
import search.IterativeDeepeningSearch;

public class SearchMethods {

	public static void main(String[] args) {
		BlocksworldNode startState = new BlocksworldNode(null, Integer.MAX_VALUE, new Position(0, 0), 
				new Position(1, 0), new Position(2, 0), new Position(3, 0));
		BlocksworldNode goalState = new BlocksworldNode(null, 0, new Position(1, 2), 
				new Position(1, 1), new Position(1, 0), new Position(0, 0));
		Problem p = new BlocksworldProblem(4,4,startState, goalState);
		
		System.out.println("Starting Depth First Search...");
		Node dfsSolution = new DepthFirstSearch().findSolution(p);
		System.out.println("Done.");
		System.out.println("Starting Breadth First Search...");
		Node bfsSolution = new BreadthFirstSearch().findSolution(p);
		System.out.println("Done.");
		System.out.println("Starting Iterative Deepening Search...");
		Node idsSolution = new IterativeDeepeningSearch().findSolution(p);
		System.out.println("Done.");
		System.out.println("Starting A* Search...");
		Node assSolution = new AStarSearch().findSolution(p);
		System.out.println("Done.");
		
		System.out.println("Outputting results...");
		outputSolution(dfsSolution, "dfs.txt");		
		outputSolution(bfsSolution, "bfs.txt");		
		outputSolution(idsSolution, "ids.txt");		
		outputSolution(assSolution, "ass.txt");
		System.out.println("Done.");
	}
	
	public static void outputSolution(Node n, String filename){
		File solutionFile = new File(filename);
		
		if(!solutionFile.exists()){
			try{Files.createFile(Paths.get(filename));}catch(IOException e){e.printStackTrace();}			
		}
		
		try(FileWriter out = new FileWriter(solutionFile)){
			for(Node node : n.getPath()){
				out.write(node.toString() + System.getProperty("line.separator"));
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}