package graphs;

public class Position {
	final private Integer posX;
	final private Integer posY;

	/**
	 * @param posX
	 * @param posY
	 */
	public Position(Integer posX, Integer posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Position)) {
			return false;
		} 
		
		Position p = (Position) obj;
		
		return getPosX().equals(p.getPosX()) && getPosY().equals(p.getPosY());
	}

	public Integer getPosX() {
		return posX;
	}

	public Integer getPosY() {
		return posY;
	}
}
