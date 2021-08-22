package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GraphMatrix<ValueT, CostT> {
	final private Integer sizeX;
	final private Integer sizeY;
	final private ValueT VISITED;
	final private ValueT EMPTY;
	final private ValueT FORBIDDEN;
	final private CostT INITIALCOST;

	private List<List<DefaultMatrixElement<ValueT, CostT>>> matrix;

	public GraphMatrix(Integer lines, Integer columns, ValueT emptyValue, ValueT visitedValue, ValueT forbiddenValue,
			CostT initialCost) {
		this.sizeX = lines;
		this.sizeY = columns;
		this.EMPTY = emptyValue;
		this.VISITED = visitedValue;
		this.FORBIDDEN = forbiddenValue;
		this.INITIALCOST = initialCost;

		initMatrix();
	}

	private void initMatrix() {
		matrix = new ArrayList<List<DefaultMatrixElement<ValueT, CostT>>>(getSizeX());

		for (int i = 0; i < getSizeX(); i++) {
			matrix.add(i, new ArrayList<DefaultMatrixElement<ValueT, CostT>>(getSizeY()));
			for (int j = 0; j < getSizeY(); j++) {
				matrix.get(i).add(j, new DefaultMatrixElement<ValueT, CostT>(EMPTY, INITIALCOST));
			}
		}
	}

	public void clearMatrix() {
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
				matrix.get(i).get(j).setElement(EMPTY, INITIALCOST);
			}
		}
	}

	public void changeValues(ValueT oldValue, ValueT newValue) {
		for (int i = 0; i < getSizeX(); i++) {
			for (int j = 0; j < getSizeY(); j++) {
				if (matrix.get(i).get(j).getValue().equals(oldValue)) {
					matrix.get(i).get(j).setValue(newValue);
				}
			}
		}
	}

	public void setVisitedToEmpty() {
		changeValues(VISITED, EMPTY);
	}

	public void setLineValue(Integer lineIndex, ValueT value) {
		for (int i = 0; i < matrix.get(lineIndex).size(); i++) {
			setElementValue(lineIndex, i, value);
		}
	}

	public void setColumnValue(Integer columnIndex, ValueT value) {
		for (int i = 0; i < matrix.size(); i++) {
			setElementValue(i, columnIndex, value);
		}
	}

	public void setLineCost(Integer lineIndex, CostT cost) {
		for (int i = 0; i < matrix.get(lineIndex).size(); i++) {
			setElementCost(lineIndex, i, cost);
		}
	}

	public void setColumnCost(Integer columnIndex, CostT cost) {
		for (int i = 0; i < matrix.size(); i++) {
			setElementCost(i, columnIndex, cost);
		}
	}

	public void setElementValue(Position p, ValueT value) {
		setElementValue(p.getPosX(), p.getPosY(), value);
	}

	public void setElementCost(Position p, CostT cost) {
		setElementCost(p.getPosX(), p.getPosY(), cost);
	}

	public void setElement(Position p, ValueT value, CostT cost) {
		setElement(p.getPosX(), p.getPosY(), value, cost);
	}

	public void setElementValue(Integer lineIndex, Integer columnIndex, ValueT value) {
		if (!isLineIndexValid(lineIndex)) {
			throw new IndexOutOfBoundsException();
		}
		if (!isColumnIndexValid(columnIndex)) {
			throw new IndexOutOfBoundsException();
		}

		matrix.get(lineIndex).get(columnIndex).setValue(value);
	}

	public void setElementCost(Integer lineIndex, Integer columnIndex, CostT cost) {
		if (!isLineIndexValid(lineIndex)) {
			throw new IndexOutOfBoundsException();
		}
		if (!isColumnIndexValid(columnIndex)) {
			throw new IndexOutOfBoundsException();
		}

		matrix.get(lineIndex).get(columnIndex).setCost(cost);
	}

	public void setElement(Integer lineIndex, Integer columnIndex, ValueT value, CostT cost) {
		setElementValue(lineIndex, columnIndex, value);
		setElementCost(lineIndex, columnIndex, cost);
	}

	private Boolean isLineIndexValid(Integer lineIndex) {
		return lineIndex >= 0 && lineIndex < getSizeX();
	}

	private Boolean isColumnIndexValid(Integer columnIndex) {
		return columnIndex >= 0 && columnIndex < getSizeY();
	}

	private Boolean isElementValid(Integer lineIndex, Integer columnIndex) {
		return isLineIndexValid(lineIndex) && isColumnIndexValid(columnIndex);
	}

	private Boolean visitable(Integer lineIndex, Integer columnIndex) {
		return isElementValid(lineIndex, columnIndex) ? matrix.get(lineIndex).get(columnIndex).getValue().equals(EMPTY)
				: false;
	}

	private Position north(Integer lineIndex, Integer columnIndex) {
		if (isElementValid(lineIndex, columnIndex + 1)) {
			return new Position(lineIndex, columnIndex + 1);
		}
		return null;
	}

	private Position south(Integer lineIndex, Integer columnIndex) {
		if (isElementValid(lineIndex, columnIndex - 1)) {
			return new Position(lineIndex, columnIndex - 1);
		}
		return null;
	}

	private Position west(Integer lineIndex, Integer columnIndex) {
		if (isElementValid(lineIndex - 1, columnIndex)) {
			return new Position(lineIndex - 1, columnIndex);
		}
		return null;
	}

	private Position east(Integer lineIndex, Integer columnIndex) {
		if (isElementValid(lineIndex + 1, columnIndex)) {
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
					setElementValue(line, column, VISITED);

					while (!nodeQueue.isEmpty()) {
						Position p = nodeQueue.remove();

						List<Position> ns = visitableNeighbours(p.getPosX(), p.getPosY());
						if (ns == null) {
							continue;
						} else {
							for (Position n : ns) {
								visited.add(n);
								setElementValue(n.getPosX(), n.getPosY(), VISITED);
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
		if (!isElementValid(start.getPosX(), start.getPosY())) {
			throw new ArrayIndexOutOfBoundsException();
		}

		List<Position> visited = new ArrayList<Position>();
		Queue<Position> nodeQueue = new LinkedList<Position>();

		if (!visitable(start.getPosX(), start.getPosY())) {
			return visited;
		}

		nodeQueue.add(new Position(start.getPosX(), start.getPosY()));
		setElementValue(start.getPosX(), start.getPosY(), VISITED);
		visited.add(new Position(start.getPosX(), start.getPosY()));

		while (!nodeQueue.isEmpty()) {
			Position p = nodeQueue.remove();

			List<Position> ns = visitableNeighbours(p.getPosX(), p.getPosY());
			if (ns == null) {
				continue;
			} else {
				for (Position n : ns) {
					visited.add(n);
					setElementValue(n.getPosX(), n.getPosY(), VISITED);
					nodeQueue.add(n);
				}
			}
		}

		return visited;
	}

	public List<Position> bfs(Position start, Position end) {
		if (!isElementValid(start.getPosX(), start.getPosY())) {
			throw new ArrayIndexOutOfBoundsException();
		}

		if (!isElementValid(end.getPosX(), end.getPosY())) {
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
		setElementValue(start.getPosX(), start.getPosY(), VISITED);
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
					setElementValue(n.getPosX(), n.getPosY(), VISITED);
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

	public Boolean isPathValid(List<Position> ps) {
		for (Position p : ps) {
			if (!isElementValid(p.getPosX(), p.getPosY())) {
				return false;
			}
		}
		return true;
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

	public ValueT getVISITED() {
		return VISITED;
	}

	public ValueT getEMPTY() {
		return EMPTY;
	}

	public ValueT getFORBIDDEN() {
		return FORBIDDEN;
	}

	public CostT getINITIALCOST() {
		return INITIALCOST;
	}

	public ValueT getElementValue(Integer lineIndex, Integer columnIndex) {
		return isElementValid(lineIndex, columnIndex) ? matrix.get(lineIndex).get(columnIndex).getValue() : null;
	}

	public ValueT getElementValue(Position p) {
		return getElementValue(p.getPosX(), p.getPosY());
	}

	public CostT getElementCost(Integer lineIndex, Integer columnIndex) {
		return isElementValid(lineIndex, columnIndex) ? matrix.get(lineIndex).get(columnIndex).getCost() : null;
	}

	public CostT getElementCost(Position p) {
		return getElementCost(p.getPosX(), p.getPosY());
	}

	public List<List<DefaultMatrixElement<ValueT, CostT>>> getMatrix() {
		return matrix;
	}

}
