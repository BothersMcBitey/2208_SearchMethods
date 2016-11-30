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

public class Main {

	public static void main(String[] args) {
		BlocksworldNode startState = new BlocksworldNode(new Path(), Integer.MAX_VALUE, new Position(0, 0), 
				new Position(1, 0), new Position(2, 0), new Position(3, 0));
		BlocksworldNode goalState = new BlocksworldNode(new Path(), 0, new Position(1, 2), 
				new Position(1, 1), new Position(1, 0), new Position(0, 0));
		Problem p = new BlocksworldProblem(startState, goalState);
		
		Node dfsSolution = new DepthFirstSearch().findSolution(p);
		Node bfsSolution = new BreadthFirstSearch().findSolution(p);
		Node idsSolution = new IterativeDeepeningSearch().findSolution(p);
		Node assSolution = new AStarSearch().findSolution(p);
		
		outputSolution(dfsSolution, "dfs.txt");
		outputSolution(bfsSolution, "bfs.txt");
		outputSolution(idsSolution, "ids.txt");
		outputSolution(assSolution, "ass.txt");
	}
	
	public static void outputSolution(Node n, String filename){
		File solutionFile = new File(filename);
		
		if(!solutionFile.exists()){
			try{Files.createFile(Paths.get(filename));}catch(IOException e){e.printStackTrace();}			
		}
		
		try(FileWriter out = new FileWriter(solutionFile)){
			out.write(n.getPath().toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}