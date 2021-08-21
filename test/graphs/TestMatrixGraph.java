package graphs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class TestMatrixGraph {

	@Test
	void simpleMatrixAllDFS(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		
		final int sizeX = 3;
		final int sizeY = 3;
		final int EMPTY = 0;
		final int VISITED = 1;
		final int FORBIDDEN = 2;
		
		GraphMatrix<Integer> gm = new GraphMatrix<Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN);
		
		assertEquals(EMPTY, gm.getEMPTY());
		assertEquals(VISITED, gm.getVISITED());
		assertEquals(FORBIDDEN, gm.getFORBIDDEN());
		assertEquals(sizeX, gm.getSizeX());
		assertEquals(sizeY, gm.getSizeY());
		
		gm.printMatrix();
		List<Position> visited = gm.bfs();
		gm.printMatrix();
		
		assertNotNull(visited);
		System.out.println("visited");
		for (Position p : visited) {
			System.out.println(p.getPosX() + " " + p.getPosY());
		}
		
		assertEquals(sizeX*sizeY, visited.size());
	}

	
	@Test
	void simpleMatrixDFSStart(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		
		final int sizeX = 10;
		final int sizeY = 10;
		final int EMPTY = 0;
		final int VISITED = 1;
		final int FORBIDDEN = 2;
		
		GraphMatrix<Integer> gm = new GraphMatrix<Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN);
		
		assertEquals(EMPTY, gm.getEMPTY());
		assertEquals(VISITED, gm.getVISITED());
		assertEquals(FORBIDDEN, gm.getFORBIDDEN());
		assertEquals(sizeX, gm.getSizeX());
		assertEquals(sizeY, gm.getSizeY());
		
		gm.printMatrix();
		List<Position> visited = gm.bfs(new Position(sizeX/2, sizeY/2));
		gm.printMatrix();
		
		assertNotNull(visited);
		System.out.println("visited");
		for (Position p : visited) {
			System.out.println(p.getPosX() + " " + p.getPosY());
		}
		
		assertEquals(sizeX*sizeY, visited.size());
	}
	
	@Test
	void simpleMatrixDFSStartEnd(TestInfo testInfo) {
		System.out.println(testInfo.getDisplayName());
		
		final int sizeX = 10;
		final int sizeY = 10;
		final int EMPTY = 0;
		final int VISITED = 1;
		final int FORBIDDEN = 2;
		
		GraphMatrix<Integer> gm = new GraphMatrix<Integer>(sizeX, sizeY, EMPTY, VISITED, FORBIDDEN);
		
		assertEquals(EMPTY, gm.getEMPTY());
		assertEquals(VISITED, gm.getVISITED());
		assertEquals(FORBIDDEN, gm.getFORBIDDEN());
		assertEquals(sizeX, gm.getSizeX());
		assertEquals(sizeY, gm.getSizeY());
		
		gm.printMatrix();
		List<Position> path = gm.bfs(new Position(0, 0), new Position(sizeX/2, sizeY/2));
		gm.printMatrix();
		
		assertNotNull(path);
		System.out.println("visited");
		for (Position p : path) {
			System.out.println(p.getPosX() + " " + p.getPosY());
		}
		
		assertEquals(false, path.isEmpty());
		
		System.out.println("path");
		gm.clearMatrix();
		
		for (Position p : path) {
			gm.setElement(p.getPosX(), p.getPosY(), gm.getVISITED());
		}
		
		gm.printMatrix();
	}
}
