package game;

import java.awt.Color;
import java.awt.Graphics2D;

import graphs.GraphMatrix;

public class Map {
	
	private GraphMatrix<Integer, Integer> graph;
	private int numTilesW;
	private int numTilesH;
	private int width;
	private int height;
	private int tileSizeW;
	private int tileSizeH;
	
	public Map(GraphMatrix<Integer, Integer> graph, int width, int height ,int numTilesH, int numTilesW) {
		this.graph = graph;
		this.width = width;
		this.height = height;
		this.numTilesH = numTilesH;
		this.numTilesW = numTilesW;
		this.tileSizeH = height/numTilesH;
		this.tileSizeW = width/numTilesW;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.GREEN);
		for (int i = 0; i < numTilesW; i++) {
			for (int j = 0; j < numTilesH; j++) {
				g2d.setColor(Color.BLACK);
				g2d.fillRect(i*tileSizeW, j*tileSizeH, tileSizeW+1, tileSizeH+1);
				g2d.setColor(checkColor(i, j));
				g2d.fillRect(i*tileSizeW+1, j*tileSizeH+1, tileSizeW-2, tileSizeH-2);
			}
		}
		
	}

	private Color checkColor(int i, int j) {
		// Cor Default(Custo padrão)
		Color color = Color.GREEN;
		// Outro custo(Temporário)
		if (graph.getElementCost(i, j) != 0)
			color = Color.YELLOW;
		return color;
	}
	
}
