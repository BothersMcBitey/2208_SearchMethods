package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import blocksworld.SimpleNode;
import blocksworld.SimpleProblem;

public class SimpleRenderer {

	private final int GIF_SIZE;
	private Problem p;
	private boolean showCosts;
	
	public SimpleRenderer(int GIF_SIZE, Problem p,  boolean showCosts) {
		this.GIF_SIZE = GIF_SIZE;
		this.p = p;
		this.showCosts = showCosts;
	}

	public BufferedImage startFrame(boolean end){		
		BufferedImage img = new BufferedImage(GIF_SIZE, GIF_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		
		//background		
		if(end)g.setColor(Color.RED); else g.setColor(Color.GREEN);
		g.fillRect(0, 0, GIF_SIZE, GIF_SIZE);
		
		return img;
	}
	
	public BufferedImage generateFrame(Node move, boolean showMove){
		SimpleProblem prob = (SimpleProblem) p;
		int w = prob.getWidth();
		int h = prob.getHeight();	
		int tileW = GIF_SIZE / w;
		int tileH = GIF_SIZE / h;
		BufferedImage img = new BufferedImage(GIF_SIZE, GIF_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		
		//background		
		g.setColor(Color.white);
		g.fillRect(0, 0, GIF_SIZE, GIF_SIZE);
		
		//lines
		g.setColor(Color.BLACK);
		for(int i = 0; i < w; i++){
			g.drawLine(0, i * tileW, GIF_SIZE, i * tileW);		
		}
		
		for(int i = 0; i < h; i++){
			g.drawLine(i*tileH, 0, i*tileH, GIF_SIZE);			
		}
		
		//goal positions
		g.setColor(new Color(250, 150, 0, 100));
		g.fillRect(1 * tileW + 1, (GIF_SIZE - tileH) - (1 * tileH) + 1, tileW - 2, tileH - 2);
//		g.fillRect(1 * tileW + 1, (GIF_SIZE - tileH) - (0 * tileH) + 1, tileW - 2, tileH - 2);		
		
		//node positions
		SimpleNode node = null;
		if(move.getParent() != null && showMove){
			node = (SimpleNode) move.getParent();
		} else {
			 node = (SimpleNode) move;
		}
		
		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, g.getFont().getSize()*2));
		
		g.setColor(Color.BLUE);
		g.fillRect(node.getAgent().getX() * tileW + 5, (GIF_SIZE - tileH) - (node.getAgent().getY() * tileH) + 5, (tileW - 10), (tileH - 10));
		if(showCosts){
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(node.getCost()), node.getAgent().getX() * tileW + 20 , (GIF_SIZE - tileH) - (node.getAgent().getY() * tileH) + 40);
		}
						
		for(Node child : prob.getChildren(node)){
			SimpleNode c = (SimpleNode) child;
			if(showMove && c.equals(move)){
				g.setColor(new Color(0, 250, 0, 250));
			} else g.setColor(new Color(250, 0, 0, 50));
			
			g.fillRect(c.getAgent().getX() * tileW + 1, (GIF_SIZE - tileH) - (c.getAgent().getY() * tileH) + 1, tileW - 2, tileH - 2);	
		}
		
		g.setColor(Color.GRAY);
		g.fillRect(node.getA().getX() * tileW + 5, (GIF_SIZE - tileH) - (node.getA().getY() * tileH) + 5, (tileW - 10), (tileH - 10));
				
		
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(node.getCost()), 20, 20);
		g.drawString(Integer.toString(node.getDepth()), 20, 40);
		
		if(showCosts){
			for(Node child : prob.getChildren(node)){
				SimpleNode c = (SimpleNode) child;
				g.setColor(Color.BLUE);
				g.drawString(Integer.toString(c.getCost()), c.getAgent().getX() * tileW + 10 , (GIF_SIZE - tileH) - (c.getAgent().getY() * tileH) + 20);	
			}		
		}
		
		g.setColor(Color.WHITE);
		g.drawString("a", node.getA().getX() * tileW + 100, GIF_SIZE - (node.getA().getY() * tileH + 100));
		
		g.drawString("agent", node.getAgent().getX() * tileW + 100, GIF_SIZE - (node.getAgent().getY() * tileH + 100));
		
		return img;
	}
}
