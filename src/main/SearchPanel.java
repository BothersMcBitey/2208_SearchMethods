package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import blocksworld.BlocksworldNode;

public class SearchPanel extends JPanel {

	Node n;
	
	public void update(Node n){
		this.n = n;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		drawState(g);
	}
	
	private void drawState(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 800);
		
		g.setColor(Color.BLACK);
		
		g.drawLine(0, 200, 800, 200);
		g.drawLine(0, 400, 800, 400);
		g.drawLine(0, 600, 800, 600);
		
		g.drawLine(200, 0, 200, 800);
		g.drawLine(400, 0, 400, 800);
		g.drawLine(600, 0, 600, 800);
		
		BlocksworldNode node = (BlocksworldNode) n;
		
		g.drawString(Integer.toString(node.getCost()), 10, 10);
		g.drawString(Integer.toString(node.getDepth()), 10, 20);
		g.drawString("a", node.getA().getX() * 200 + 100, 800 - (node.getA().getY() * 200 + 100));
		g.drawString("b", node.getB().getX() * 200 + 100, 800 - (node.getB().getY() * 200 + 100));
		g.drawString("c", node.getC().getX() * 200 + 100, 800 - (node.getC().getY() * 200 + 100));
		g.drawString("agent", node.getAgent().getX() * 200 + 100, 800 - (node.getAgent().getY() * 200 + 100));
	}
}
