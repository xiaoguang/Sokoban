package component;

import java.util.HashMap;
import java.util.Map;

public enum Movement {

	// x, y coordination movement for up, down, left, right
	// directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } }
	UP('u'), DOWN('d'), LEFT('l'), RIGHT('r');

	private String representation;

	private static final Map<String, Coordinate2D> directions = createMovementMap();

	private static Map<String, Coordinate2D> createMovementMap() {
		Map<String, Coordinate2D> movementMap = new HashMap<String, Coordinate2D>();
		movementMap.put("u", new Coordinate2D(0, -1));
		movementMap.put("d", new Coordinate2D(0, 1));
		movementMap.put("l", new Coordinate2D(-1, 0));
		movementMap.put("r", new Coordinate2D(1, 0));
		return movementMap;
	}

	Movement(char move) {
		this.representation = Character.toString(move);
	}

	Movement(String move) {
		this.representation = new String(move);
	}

	public Coordinate2D direction() {
		return directions.get(this.representation);
	}

	public String represent() {
		return this.representation;
	}

	public String toString() {
		String str = "Name : ";
		str = str.concat(this.name() + " -> ");
		str = str.concat(this.representation);
		str = str.concat("\n");
		str = str.concat("Move : ");
		str = str.concat("X -> " + this.direction().X + "\n");
		str = str.concat("Move : ");
		str = str.concat("Y -> " + this.direction().Y + "\n");
		return str;
	}

}
