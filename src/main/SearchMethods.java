package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import blocksworld.BlocksworldNode;
import blocksworld.BlocksworldProblem;
import blocksworld.Position;
import blocksworld.ProblemGenerationNode;
import blocksworld.ProblemGenerationProblem;
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.DepthFirstSearch;
import search.IterativeDeepeningSearch;

public class SearchMethods {

	static final int MAX_PROBLEM_DEPTH = 10; 
	
	public static void main(String[] args) {
//		BlocksworldNode startState = new BlocksworldNode(null, Integer.MAX_VALUE, new Position(0, 0), 
//				new Position(1, 0), new Position(2, 0), new Position(3, 0));
		BlocksworldNode goalState = new BlocksworldNode(null, 0, new Position(1, 2), 
				new Position(1, 1), new Position(1, 0), new Position(0, 0));		
		
		DepthFirstSearch dfs = new DepthFirstSearch();
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
		AStarSearch ass = new AStarSearch();
		
		ArrayList<Result> dfsResults = new ArrayList<Result>();
		ArrayList<Result> bfsResults = new ArrayList<Result>();
		ArrayList<Result> idsResults = new ArrayList<Result>();
		ArrayList<Result> assResults = new ArrayList<Result>();
		
		try{
			for(int i = 0; i < MAX_PROBLEM_DEPTH; i++){
				System.out.println("=================================");
				System.out.println("Testing for problem depth " + i);
				System.out.println("---------------------------------");			
				System.out.println("Generating problem...");
				Problem findProblemOfDepthI = new ProblemGenerationProblem(5, 5, goalState, new ProblemGenerationNode(i));
				Result problemOfDepthI = new DepthFirstSearch().findSolution(findProblemOfDepthI);
				
				BlocksworldNode startState = (BlocksworldNode) problemOfDepthI.getSolution();
				startState.convertToStartNode();
				
				Problem p = new BlocksworldProblem(5,5,startState, goalState);
				
				System.out.println("Problem:");
				System.out.println(startState.toString());
				
				System.out.println("Finding solutions...");
				dfsResults.add(dfs.findSolution(p));
				if(i<7) bfsResults.add(bfs.findSolution(p));
				if(i<8) idsResults.add(ids.findSolution(p));
				assResults.add(ass.findSolution(p));
				System.out.println("Finished testing depth " + i);
			}
		} finally {
			System.out.println("Outputting results...");
			outputSolution(dfsResults, "dfs");		
			outputSolution(bfsResults, "bfs");		
			outputSolution(idsResults, "ids");		
			outputSolution(assResults, "ass");
			System.out.println("Done.");
		}
	}
	
	public static void outputSolution(List<Result> results, String filename){
		//Data
		File dataFile = new File(filename + ".csv");				
		if(!dataFile.exists()){
			try{Files.createFile(Paths.get(dataFile.getName()));}catch(IOException e){e.printStackTrace();}			
		}		
		try(FileWriter out = new FileWriter(dataFile)){
			out.write("problemDepth,nodesExpanded");
			for(Result r : results){
				out.write(r.getProblemDepth() + "," + r.getNodesExpanded());
			}		
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		//Solution
		File solutionFile = new File(filename + ".txt");
		if(!solutionFile.exists()){
			try{Files.createFile(Paths.get(solutionFile.getName()));}catch(IOException e){e.printStackTrace();}			
		}		
		try(FileWriter out = new FileWriter(solutionFile)){
			int count = 0;
			for(Result r : results){
				count++;
				out.write("===========================================");
				out.write("Solution " + count);
				for(Node node : r.getSolution().getPath()){
					out.write(node.toString() + System.getProperty("line.separator"));
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}