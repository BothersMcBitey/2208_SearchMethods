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
import java.util.Set;
import java.util.Stack;

import blocksworld.BlocksworldNode;
import blocksworld.BlocksworldProblem;
import blocksworld.Position;
import blocksworld.SimpleNode;
import blocksworld.SimpleProblem;
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.DepthFirstSearch;
import search.IterativeDeepeningSearch;
import search.SearchSet;

public class SearchMethods {
	
	public static void main(String[] args) {
		new SearchMethods().run();
	}

	final int MAX_PROBLEM_DEPTH = 13, MAX_PROBLEM_REPEATS = 10; 
	
	private File dfs_data = new File("dfs.csv");
	private File bfs_data = new File("bfs.csv");
	private File ids_data = new File("ids.csv");
	private File ass_data = new File("ass.csv");
	
	private FileWriter dfs_out, bfs_out, ids_out, ass_out;
	
	private BlocksworldNode goalState;
	private BlocksworldNode startState;
	
	private DepthFirstSearch dfs;
	private BreadthFirstSearch bfs;
	private IterativeDeepeningSearch ids;
	private AStarSearch ass;
	
	public SearchMethods() {
		goalState = new BlocksworldNode(null, 0, new Position(1, 2), new Position(1, 1), new Position(1, 0), new Position(0, 0));
		startState = new BlocksworldNode(null, Integer.MAX_VALUE, new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0));
		dfs = new DepthFirstSearch();
		bfs = new BreadthFirstSearch();
		ids = new IterativeDeepeningSearch();
		ass = new AStarSearch();
	}
	
	public void run(){					
		//----------------------------------------------------------------------------------
		//This bit solves the puzzle as given in the spec
//		Problem p1 = new BlocksworldProblem(4,4,startState, goalState);
//		Result dfsResult = dfs.findSolution(p1);
//		Result bfsResult = bfs.findSolution(p1);
//		Result idsResult = ids.findSolution(p1);
//		Result assResult = ass.findSolution(p1);
//		
//		System.out.println("Outputting results...");
//		
//		outputSolution(dfsResult, "dfs");		
//		outputSolution(bfsResult, "bfs");		
//		outputSolution(idsResult, "ids");		
//		outputSolution(assResult, "ass");
//		
//		makeGif(dfsResult, "dfs", p1, false);		
//		makeGif(bfsResult, "bfs", p1, false);		
//		makeGif(idsResult, "ids", p1, false);	
//		makeGif(assResult, "ass", p1, true);
//		
//		System.out.println("Done.");			
		
		//----------------------------------------------------------------------------------
		//This bit just gathers time complextity data
//		openFiles();
//		Problem p = new BlocksworldProblem(5,5,startState, goalState);
//		try{
//			for(int i = 1; i < MAX_PROBLEM_DEPTH; i++){
//				for(int j = 0; j < MAX_PROBLEM_REPEATS; j++){
//					System.out.println("=================================");
//					System.out.println("Testing for problem depth " + i + ", count " + j);
//					System.out.println("---------------------------------");			
//					System.out.println("Generating problem...");
//					
//					startState = (BlocksworldNode) generateProblem(p, i);
//					startState.convertToStartNode();
//					
//					p = new BlocksworldProblem(5,5,startState, goalState);
//					
//					
//					System.out.println("Problem:");
//					System.out.println(startState.toString());
//					
//					System.out.println("Finding solutions...");
//					outputData(dfs.findSolution(p).getNodesExpanded(), i, dfs_out);
//					outputData(bfs.findSolution(p).getNodesExpanded(), i, bfs_out);
//					outputData(ids.findSolution(p).getNodesExpanded(), i, ids_out);
//					outputData(ass.findSolution(p).getNodesExpanded(), i, ass_out);
//					System.out.println("Finished testing depth " + i);
//				}
//			}
//		} finally {
//			closeFiles();
//		}
		//----------------------------------------------------------------------------------
		//This bit generates whole search GIFs
		SimpleNode gS = new SimpleNode(null, 0, new Position(1, 1), new Position(0, 0));
		SimpleNode sS = new SimpleNode(null, Integer.MAX_VALUE, new Position(0, 1), new Position(3, 1));
		
		SimpleProblem s = new SimpleProblem(3, 3, sS, gS);
		
		dfs.findSolution(s);
		bfs.findSolution(s);
		ids.findSolution(s);
		ass.findSolution(s);
	}	
	
	public Node generateProblem(Problem p, int depth){		
		Set<Node> candidates = new SearchSet<Node>();
		Set<Node> discovered = new SearchSet<Node>();
		Stack<Node> undiscovered = new Stack<Node>();
		
		List<Node> goals = p.enumerateGoalStates();
		for(Node goal : goals){
			undiscovered.push(goal);
		}
		
		System.out.println("discovering nodes...");
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
		
		System.out.println(candidates.size());
		System.out.println(discovered.size());
		
		System.out.println("checking candidates...");
		Iterator<Node> it = candidates.iterator();
		while(it.hasNext()){
			Node n = it.next();			
			if(p.isGoalState(n)){
				it.remove();
			} else {
				boolean removed = false;
				Iterator<Node> dit = discovered.iterator();
				while(!removed && dit.hasNext()){
					Node d = dit.next();
					if(d.equals(n)){
						it.remove();
						removed = true;
					}
				}
			}
		}
		
		System.out.println("picking start state...");
		if(candidates.isEmpty()){
			return null;
		} else {
			Random r = new Random();
			return candidates.toArray(new Node[0])[r.nextInt(candidates.size())];
		}		
	}
	
	public void openFiles(){
		try{
			if(!dfs_data.exists()){
				Files.createFile(Paths.get(dfs_data.getName()));
			}
			dfs_out = new FileWriter(dfs_data);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			if(!bfs_data.exists()){
				Files.createFile(Paths.get(bfs_data.getName()));
			}
			bfs_out = new FileWriter(bfs_data);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			if(!ids_data.exists()){
				Files.createFile(Paths.get(ids_data.getName()));
			}
			ids_out = new FileWriter(ids_data);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			if(!ass_data.exists()){
				Files.createFile(Paths.get(ass_data.getName()));
			}
			ass_out = new FileWriter(ass_data);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void outputData(int nodesExpanded, int problemDepth, FileWriter out){
		//Data	
		try{		
			out.append(problemDepth + "," + nodesExpanded + System.getProperty("line.separator"));	
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}

	public void closeFiles(){
		try {
			dfs_out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bfs_out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ids_out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ass_out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void outputSolution(Result r, String filename){
		File solutionFile = new File(filename + ".txt");
		if(!solutionFile.exists()){
			try{Files.createFile(Paths.get(solutionFile.getName()));}
			catch(IOException e){e.printStackTrace();}			
		}		
		try(FileWriter out = new FileWriter(solutionFile)){									
			for(Node node : reverse(r.getSolution().getPath())){
				out.write(node.toString() + System.getProperty("line.separator"));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeGif(Result result, String filename, Problem p, boolean showCosts){
		int count = 0; 
		Renderer g = new Renderer(1000, p, showCosts);
		AnimatedGifEncoder e = new AnimatedGifEncoder();
		count++;
		e.start(filename + "_" + count + ".gif");
		int delay = Math.max( 1 , (5000 / result.getSolution().getPath().size()));
		e.setDelay(delay); 
		e.addFrame(g.startFrame(false));
		for(Node n : reverse(result.getSolution().getPath())){
			e.addFrame(g.generateFrame(n,true));
			e.addFrame(g.generateFrame(n,false));
		}
		e.addFrame(g.startFrame(true));
		e.finish();
	}
	
	private List<Node> reverse(Queue<Node> q){
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