package blocksworld;

import main.Node;

public class SimpleNode extends Node {

	private Position a, agent;
	
	public SimpleNode(Node parent, int cost, Position a, Position agent) {
		super(parent, cost);
		this.a = a;
		this.agent = agent;
	}
	
	@Override
	public boolean equals(Node n){
		SimpleNode node = (SimpleNode) n;
		
		if((a.equals(node.getA())) && (agent.equals(node.getAgent()))){
			return true;
		} else {
			return false;
		}
	}

	public Position getA() {
		return a;
	}

	public Position getAgent() {
		return agent;
	}

	@Override
	public String toString() {
		return "A:" + a.toString() + ", " + 
				"Agent:" + agent.toString();
	}

}
