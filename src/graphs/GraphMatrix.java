package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GraphMatrix<T> {
	final private Integer sizeX;
	final private Integer sizeY;
	final private T VISITED;
	final private T EMPTY;
	final private T FORBIDDEN;

	private List<List<T>> matrix;

	public GraphMatrix(Integer lines, Integer columns, T emptyValue, T visitedValue, T forbiddenValue) {
		this.sizeX = lines;
		this.sizeY = columns;
		this.EMPTY = emptyValue;
		this.VISITED = visitedValue;
		this.FORBIDDEN = forbiddenValue;

		initMatrix();
	}

	private void initMatrix() {
		matrix = new ArrayList<List<T>>(getSizeX());

		for (int i = 0; i < getSizeX(); i++) {
			matrix.add(i, new ArrayList<T>(getSizeY()));
			for (int j = 0; j < getSizeY(); j++) {
				matrix.get(i).add(j, EMPTY);
			}
		}
	}

	public void clearMatrix() {
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
				matrix.get(i).set(j, EMPTY);
			}
		}
	}

	public void setLine(Integer lineIndex, T value) {
		if (!validLineIndex(lineIndex)) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < matrix.get(lineIndex).size(); i++) {
			matrix.get(lineIndex).set(i, value);
		}
	}

	public void setColumn(Integer columnIndex, T value) {
		if (!validColumnIndex(columnIndex)) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < matrix.size(); i++) {
			matrix.get(i).set(columnIndex, value);
		}
	}

	public void setElement(Integer lineIndex, Integer columnIndex, T value) {
		if (!validLineIndex(lineIndex)) {
			throw new IndexOutOfBoundsException();
		}
		if (!validColumnIndex(columnIndex)) {
			throw new IndexOutOfBoundsException();
		}

		matrix.get(lineIndex).set(columnIndex, value);
	}

	private Boolean validLineIndex(Integer lineIndex) {
		return lineIndex >= 0 && lineIndex < getSizeX();
	}

	private Boolean validColumnIndex(Integer columnIndex) {
		return columnIndex >= 0 && columnIndex < getSizeY();
	}

	private Boolean validElement(Integer lineIndex, Integer columnIndex) {
		return validLineIndex(lineIndex) && validColumnIndex(columnIndex);
	}

	private Boolean visitable(Integer lineIndex, Integer columnIndex) {
		return validElement(lineIndex, columnIndex) ? matrix.get(lineIndex).get(columnIndex).equals(EMPTY) : false;
	}

	private Position north(Integer lineIndex, Integer columnIndex) {
		if (validElement(lineIndex, columnIndex + 1)) {
			return new Position(lineIndex, columnIndex + 1);
		}
		return null;
	}

	private Position south(Integer lineIndex, Integer columnIndex) {
		if (validElement(lineIndex, columnIndex - 1)) {
			return new Position(lineIndex, columnIndex - 1);
		}
		return null;
	}

	private Position west(Integer lineIndex, Integer columnIndex) {
		if (validElement(lineIndex - 1, columnIndex)) {
			return new Position(lineIndex - 1, columnIndex);
		}
		return null;
	}

	private Position east(Integer lineIndex, Integer columnIndex) {
		if (validElement(lineIndex + 1, columnIndex)) {
			return new Position(lineIndex + 1, columnIndex);
		}
		return null;
	}

	List<Position> neighbours(Integer lineIndex, Integer columnIndex) {
		List<Position> nbrs = new ArrayList<Position>();

		Position n = north(lineIndex, columnIndex);
		Position s = south(lineIndex, columnIndex);
		Position e = east(lineIndex, columnIndex);
		Position w = west(lineIndex, columnIndex);

		if (n != null) {
			nbrs.add(n);
		}

		if (s != null) {
			nbrs.add(s);
		}

		if (e != null) {
			nbrs.add(e);
		}

		if (w != null) {
			nbrs.add(w);
		}

		if (nbrs.isEmpty()) {
			nbrs = null;
		}

		return nbrs;
	}

	List<Position> visitableNeighbours(Integer lineIndex, Integer columnIndex) {
		List<Position> ns = neighbours(lineIndex, columnIndex);

		if (ns == null) {
			return null;
		}

		List<Position> vns = new ArrayList<Position>();

		for (Position neighbour : ns) {
			if (visitable(neighbour.getPosX(), neighbour.getPosY())) {
				vns.add(neighbour);
			}
		}

		if (vns.isEmpty()) {
			vns = null;
		}

		return vns;
	}

	public List<Position> bfs() {
		List<Position> visited = new ArrayList<Position>();
		Queue<Position> nodeQueue = new LinkedList<Position>();

		for (int line = 0; line < getSizeX(); line++) {
			for (int column = 0; column < getSizeY(); column++) {
				if (visitable(line, column)) {
					nodeQueue.add(new Position(line, column));
					visited.add(new Position(line, column));
					setElement(line, column, VISITED);

					while (!nodeQueue.isEmpty()) {
						Position p = nodeQueue.remove();

						List<Position> ns = visitableNeighbours(p.getPosX(), p.getPosY());
						if (ns == null) {
							continue;
						} else {
							for (Position n : ns) {
								visited.add(n);
								setElement(n.getPosX(), n.getPosY(), VISITED);
								nodeQueue.add(n);
							}
						}
					}
				}
			}
		}

		return visited;
	}

	public List<Position> bfs(Position start) {
		if (!validElement(start.getPosX(), start.getPosY())) {
			throw new ArrayIndexOutOfBoundsException();
		}

		List<Position> visited = new ArrayList<Position>();
		Queue<Position> nodeQueue = new LinkedList<Position>();

		if (!visitable(start.getPosX(), start.getPosY())) {
			return visited;
		}

		nodeQueue.add(new Position(start.getPosX(), start.getPosY()));
		setElement(start.getPosX(), start.getPosY(), VISITED);
		visited.add(new Position(start.getPosX(), start.getPosY()));

		while (!nodeQueue.isEmpty()) {
			Position p = nodeQueue.remove();

			List<Position> ns = visitableNeighbours(p.getPosX(), p.getPosY());
			if (ns == null) {
				continue;
			} else {
				for (Position n : ns) {
					visited.add(n);
					setElement(n.getPosX(), n.getPosY(), VISITED);
					nodeQueue.add(n);
				}
			}
		}

		return visited;
	}

	public List<Position> bfs(Position start, Position end) {
		if (!validElement(start.getPosX(), start.getPosY())) {
			throw new ArrayIndexOutOfBoundsException();
		}

		if (!validElement(end.getPosX(), end.getPosY())) {
			throw new ArrayIndexOutOfBoundsException();
		}

		List<Position> visited = new ArrayList<Position>();
		List<Position> parent = new ArrayList<Position>();
		List<Position> path = new ArrayList<Position>();
		Queue<Position> nodeQueue = new LinkedList<Position>();

		if (!visitable(start.getPosX(), start.getPosY())) {
			return visited;
		}

		if (start.equals(end)) {
			visited.add(start);
			return visited;
		}

		Boolean foundEndNode = false;

		nodeQueue.add(new Position(start.getPosX(), start.getPosY()));
		setElement(start.getPosX(), start.getPosY(), VISITED);
		visited.add(new Position(start.getPosX(), start.getPosY()));
		parent.add(new Position(start.getPosX(), start.getPosY()));

		while (!nodeQueue.isEmpty() && !foundEndNode) {
			Position p = nodeQueue.remove();

			List<Position> ns = visitableNeighbours(p.getPosX(), p.getPosY());
			if (ns == null) {
				continue;
			} else {
				for (Position n : ns) {
					visited.add(n);
					setElement(n.getPosX(), n.getPosY(), VISITED);
					nodeQueue.add(n);

					parent.add(p);

					if (n.equals(end)) {
						foundEndNode = true;
						break;
					}
				}
			}
		}

		if (foundEndNode) {
			Position first = end;
			Position last = parent.get(visited.indexOf(end));

			Stack<Position> revPath = new Stack<Position>();

			while (!last.equals(start)) {
				revPath.push(first);
				first = last;
				last = parent.get(visited.indexOf(last));
			}
			revPath.push(first);
			revPath.push(last);

			while (!revPath.isEmpty()) {
				path.add(revPath.pop());
			}
		}

		return path;
	}

	public void printMatrix() {
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
				System.out.print(matrix.get(i).get(j).toString() + " ");
			}
			System.out.println();
		}
	}

	public Integer getSizeX() {
		return sizeX;
	}

	public Integer getSizeY() {
		return sizeY;
	}

	public T getVISITED() {
		return VISITED;
	}

	public T getEMPTY() {
		return EMPTY;
	}

	public T getFORBIDDEN() {
		return FORBIDDEN;
	}

	public List<List<T>> getMatrix() {
		return matrix;
	}

}
