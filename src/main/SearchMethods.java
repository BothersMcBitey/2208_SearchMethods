package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import blocksworld.BlocksworldNode;
import blocksworld.BlocksworldProblem;
import blocksworld.Position;
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.DepthFirstSearch;
import search.IterativeDeepeningSearch;

public class SearchMethods {

	static final int MAX_PROBLEM_DEPTH = 10, MAX_PROBLEM_REPEATS = 5; 
	
	public static void main(String[] args) {
		BlocksworldNode startState = new BlocksworldNode(null, Integer.MAX_VALUE, new Position(0, 0), 
				new Position(1, 0), new Position(2, 0), new Position(3, 0));
		BlocksworldNode goalState = new BlocksworldNode(null, 0, new Position(1, 2), 
				new Position(1, 1), new Position(1, 0), new Position(0, 0));		
		
		DepthFirstSearch dfs = new DepthFirstSearch();
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		IterativeDeepeningSearch ids = new IterativeDeepeningSearch();
		AStarSearch ass = new AStarSearch();
//		
//		ArrayList<Result> dfsResults = new ArrayList<Result>();
//		ArrayList<Result> bfsResults = new ArrayList<Result>();
//		ArrayList<Result> idsResults = new ArrayList<Result>();
//		ArrayList<Result> assResults = new ArrayList<Result>();
		
		Problem p1 = new BlocksworldProblem(4,4,startState, goalState);
//		dfsResults.add(dfs.findSolution(p1));
//		bfsResults.add(bfs.findSolution(p1));
//		idsResults.add(ids.findSolution(p1));
//		assResults.add(ass.findSolution(p1));
		
//		System.out.println("Outputting results...");
		
//		outputSolution(dfs.findSolution(p1), "dfs");		
//		outputSolution(bfs.findSolution(p1), "bfs");		
//		outputSolution(ids.findSolution(p1), "ids");		
//		outputSolution(ass.findSolution(p1), "ass");
		
//		makeGif(dfsResults, "dfs", p1, false);		
//		makeGif(bfsResults, "bfs", p1, false);		
//		makeGif(idsResults, "ids", p1, false);		
//		makeGif(assResults, "ass", p1, true);
//		System.out.println("Done.");

//		dfsResults.clear();
//		bfsResults.clear();
//		idsResults.clear();
//		assResults.clear();
		
		Problem p = new BlocksworldProblem(5,5,startState, goalState);
//		try{
			for(int i = 5; i < MAX_PROBLEM_DEPTH; i++){
				for(int j = 0; j < MAX_PROBLEM_REPEATS; j++){
					System.out.println("=================================");
					System.out.println("Testing for problem depth " + i + ", count " + j);
					System.out.println("---------------------------------");			
					System.out.println("Generating problem...");
					
					startState = (BlocksworldNode) generateProblem(p, i);
					startState.convertToStartNode();
					
					p = new BlocksworldProblem(5,5,startState, goalState);
					
					
					System.out.println("Problem:");
					System.out.println(startState.toString());
					
					System.out.println("Finding solutions...");
					outputSolution(dfs.findSolution(p), "dfs_" + i + "_" + j);
					outputSolution(bfs.findSolution(p), "bfs_" + i + "_" + j);
					outputSolution(ids.findSolution(p), "ids_" + i + "_" + j);
					outputSolution(ass.findSolution(p), "ass_" + i + "_" + j);
					System.out.println("Finished testing depth " + i);
				}
			}
//		} finally {
//			System.out.println("Outputting results...");
//			outputSolution(dfsResults, "dfs_t");		
//			outputSolution(bfsResults, "bfs_t");		
//			outputSolution(idsResults, "ids_t");		
//			outputSolution(assResults, "ass_t");
////			makeGif(assResults, "ass_t",p, true);
//			System.out.println("Done.");
//		}
	}
	
	public static Node generateProblem(Problem p, int depth){		
		List<Node> candidates = new ArrayList<Node>();
		List<Node> discovered = new ArrayList<Node>();
		Stack<Node> undiscovered = new Stack<Node>();
		
		List<Node> goals = p.enumerateGoalStates();
		for(Node goal : goals){
			undiscovered.push(goal);
		}
		
		while(!undiscovered.empty()){
			Node n = undiscovered.pop();
			
			if(n.getDepth() == depth){
				candidates.add(n);
			} else {
				for(Node child : p.getChildren(n)){
					undiscovered.push(child);
				}
				discovered.add(n);
			}
		}
		
		Iterator<Node> it = candidates.iterator();
		while(it.hasNext()){
			Node n = it.next();
			
			boolean removed = false;
			Iterator<Node> dit = discovered.iterator();
			while(!removed && dit.hasNext()){
				Node d = dit.next();
				if(d.equals(n) || p.isGoalState(n)){
					it.remove();
					removed = true;
				}
			}
		}
		
		if(candidates.isEmpty()){
			return null;
		} else {
			Random r = new Random();
			return candidates.get(r.nextInt(candidates.size()));
		}		
	}
	
	public static void outputSolution(Result r, String filename){
		//Data
		File dataFile = new File(filename + ".csv");				
		if(!dataFile.exists()){
			try{Files.createFile(Paths.get(dataFile.getName()));}
			catch(IOException e){e.printStackTrace();}			
		}		
		try(FileWriter out = new FileWriter(dataFile)){
			out.append("problemDepth,nodesExpanded" + System.getProperty("line.separator"));			
//			for(Result r : results){
				out.append(r.getProblemDepth() + "," + r.getNodesExpanded() + System.getProperty("line.separator"));
//			}		
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		//Solution
		File solutionFile = new File(filename + ".txt");
		if(!solutionFile.exists()){
			try{Files.createFile(Paths.get(solutionFile.getName()));}
			catch(IOException e){e.printStackTrace();}			
		}		
		try(FileWriter out = new FileWriter(solutionFile)){
			int count = 0;
//			for(Result r : results){
				count++;
				out.append("===========================================" 
						+ System.getProperty("line.separator"));
				out.append("Solution " + count + System.getProperty("line.separator"));			
				for(Node node : reverse(r.getSolution().getPath())){
					out.append(node.toString() + System.getProperty("line.separator"));
//				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeGif(List<Result> results, String filename, Problem p, boolean showCosts){
		int count = 0; 
		Renderer g = new Renderer(1000, p, showCosts);
		for(Result r : results){
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			count++;
			e.start(filename + "_" + count + ".gif");
			int delay = Math.max( 1 , (5000 / r.getSolution().getPath().size()));
			e.setDelay(delay); 
			e.addFrame(g.startFrame(false));
			for(Node n : reverse(r.getSolution().getPath())){
				e.addFrame(g.generateFrame(n,true));
				e.addFrame(g.generateFrame(n,false));
			}
			e.addFrame(g.startFrame(true));
			e.finish();
		}
	}
	
	

	private static List<Node> reverse(Queue<Node> q){
		Stack<Node> s = new Stack<Node>();
		
		while(!q.isEmpty()){
			s.push(q.remove());
		}
		
		List<Node> l = new ArrayList<Node>();
		while(!s.empty()){
			l.add(s.pop());
		}
		
		return l;
	}
	
}