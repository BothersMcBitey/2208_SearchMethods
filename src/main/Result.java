package main;

public class Result {

	private Node solution;
	private int nodesExpanded;
	//Manhatten distance of start problem plus distance of agent from nearest block
	private int problemDepth;
	
	public Result(Node solution, int nodesExpanded, int problemDepth) {
		this.solution = solution;
		this.nodesExpanded = nodesExpanded;
		this.problemDepth = problemDepth;
	}

	public Node getSolution() {
		return solution;
	}

	public int getNodesExpanded() {
		return nodesExpanded;
	}

	public int getProblemDepth() {
		return problemDepth;
	}
}