package game;

import java.awt.Color;
import java.awt.Graphics2D;

import graphs.GraphMatrix;

public class Map {
	
	private GraphMatrix<Integer, Integer> graph;
	private int numTilesH;
	private int numTilesW;
	private int width;
	private int height;
	
	public Map(GraphMatrix<Integer, Integer> graph, int width, int height ,int numTilesH, int numTilesW) {
		this.graph = graph; //Não usado ainda
		this.width = width;
		this.height = height;
		this.numTilesH = numTilesH;
		this.numTilesW = numTilesW;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.BLACK);
		for (int i = 0; i <= width; i += width/numTilesW) {
			g2d.drawLine(i, 0, i, width);
		}
		for (int i = 0; i <= height; i += height/numTilesH) {
			g2d.drawLine(0, i, height, i);
		}
	}
	
}
