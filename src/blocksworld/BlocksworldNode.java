package blocksworld;

import main.Node;

public class BlocksworldNode extends Node {

	private Position a, b, c, agent;
	
	public BlocksworldNode(/*Path path*/Node parent, int cost, Position a, 
			Position b, Position c, Position agent) {
		super(parent, cost);
		this.a = a;
		this.b = b;
		this.c = c;
		this.agent = agent;
	}
	
	@Override
	public boolean equals(Node n){
		BlocksworldNode node = (BlocksworldNode) n;
		
		if((a.equals(node.getA())) && (b.equals(node.getB())) 
				&& (c.equals(node.getC())) && (agent.equals(node.getAgent()))){
			return true;
		} else {
			return false;
		}
	}

	public Position getA() {
		return a;
	}

	public Position getB() {
		return b;
	}

	public Position getC() {
		return c;
	}

	public Position getAgent() {
		return agent;
	}

	@Override
	public String toString() {
		return "A:" + a.toString() + ", " + 
				"B:" + b.toString() + ", " +
				"C:" + c.toString() + ", " +
				"Agent:" + agent.toString();
	}

}
