package component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.GlobalConfig;

public class BoardState {
	// mutable elements
	HashSet<Coordinate2D> boxes;
	Coordinate2D player;

	// immutable elements
	public final int row;
	public final int col;
	final HashSet<Coordinate2D> targets;
	final HashSet<Coordinate2D> walls;

	public BoardState(HashSet<Coordinate2D> b, HashSet<Coordinate2D> t,
			HashSet<Coordinate2D> w, Coordinate2D p, int r, int c) {
		this.boxes = b;
		this.targets = t;
		this.walls = w;
		this.player = p;
		this.row = r;
		this.col = c;
	}

	// targets are static -> not included in hash
	// walls are static -> not included in hash
	@Override
	public int hashCode() {
		int result = GlobalConfig.startPrime;
		for (Coordinate2D b : boxes) {
			result += GlobalConfig.firstPrime * b.hashCode();
		}
		result += GlobalConfig.secondPrime * player.hashCode();
		return result;
	}

	// targets are static -> not included in equal
	// walls are static -> not included in equal
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object == this)
			return true;
		if (this.getClass() != object.getClass())
			return false;
		BoardState bs = (BoardState) object;
		// check reference first
		if ((this.boxes == bs.boxes) && (this.player == bs.player))
			return true;
		// check value second
		if (this.boxes.size() != bs.boxes.size())
			return false;
		if (!this.player.equals(bs.player))
			return false;
		for (Coordinate2D b : boxes) {
			if (!bs.boxes.contains(b))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String str = "";

		for (int y = 0; y < row; y++) {
			for (int x = 0; x < col; x++) {
				Coordinate2D coord = new Coordinate2D(x, y);
				if (this.walls.contains(coord)) {
					str = str.concat(Character.toString('#'));
					continue;
				}
				if (this.boxes.contains(coord)) {
					if (this.targets.contains(coord))
						str = str.concat(Character.toString('b'));
					else
						str = str.concat(Character.toString('B'));
					continue;
				}
				if (this.player.equals(coord)) {
					if (this.targets.contains(coord))
						str = str.concat(Character.toString('p'));
					else
						str = str.concat(Character.toString('P'));
					continue;
				}
				if (this.targets.contains(coord)) {
					str = str.concat(Character.toString('T'));
					continue;
				}
				str = str.concat(Character.toString(' '));
			}
			str = str.concat(System.lineSeparator());
		}

		return str;
	}

	public boolean targetTest() {
		for (Coordinate2D box : this.boxes)
			if (!this.targets.contains(box))
				return false;
		return true;
	}

	public List<Movement> possibleMovement() {
		List<Movement> movementList = new ArrayList<Movement>();

		Coordinate2D player = this.player;
		Set<Coordinate2D> walls = this.walls;
		Set<Coordinate2D> boxes = this.boxes;

		// test move up
		this.teatAndAdd(Movement.UP, player, walls, boxes, movementList);
		// test move down
		this.teatAndAdd(Movement.DOWN, player, walls, boxes, movementList);
		// test move left
		this.teatAndAdd(Movement.LEFT, player, walls, boxes, movementList);
		// test move right
		this.teatAndAdd(Movement.RIGHT, player, walls, boxes, movementList);

		return movementList;
	}

	public boolean deadlockTest() {
		Set<Coordinate2D> walls = this.walls;
		Set<Coordinate2D> boxes = this.boxes;
		Set<Coordinate2D> targets = this.targets;

		for (Coordinate2D box : boxes) {
			if (targets.contains(box))
				continue;

			// test all cornered cases
			// up-left corner -> can't move up or left anymore
			if (this.cornered(walls, boxes, box, Movement.UP, Movement.LEFT))
				return true;

			// up-right corner -> can't move up or right anymore
			if (this.cornered(walls, boxes, box, Movement.UP, Movement.RIGHT))
				return true;

			// bottom-left corner -> can't move down or left anymore
			if (this.cornered(walls, boxes, box, Movement.DOWN, Movement.LEFT))
				return true;

			// bottom-right corner -> can't move down or right anymore
			if (this.cornered(walls, boxes, box, Movement.DOWN, Movement.RIGHT))
				return true;

			// test four sides with 1 more look ahead
			// on the top side
			if (!this.validMovement(walls, box, Movement.UP)
					&& !this.validMovement(walls, box, Movement.UP,
							Movement.LEFT)
					&& !this.validMovement(walls, box, Movement.UP,
							Movement.RIGHT)
					&& !this.hitTarget(targets, box, Movement.LEFT)
					&& !this.hitTarget(targets, box, Movement.RIGHT)
					&& !this.validMovement(walls, box, Movement.LEFT,
							Movement.LEFT)
					&& !this.validMovement(walls, box, Movement.RIGHT,
							Movement.RIGHT))
				return true;

			// on the bottom side
			if (!this.validMovement(walls, box, Movement.DOWN)
					&& !this.validMovement(walls, box, Movement.DOWN,
							Movement.LEFT)
					&& !this.validMovement(walls, box, Movement.DOWN,
							Movement.RIGHT)
					&& !this.hitTarget(targets, box, Movement.LEFT)
					&& !this.hitTarget(targets, box, Movement.RIGHT)
					&& !this.validMovement(walls, box, Movement.LEFT,
							Movement.LEFT)
					&& !this.validMovement(walls, box, Movement.RIGHT,
							Movement.RIGHT))
				return true;

			// on the left side
			if (!this.validMovement(walls, box, Movement.LEFT)
					&& !this.validMovement(walls, box, Movement.LEFT,
							Movement.UP)
					&& !this.validMovement(walls, box, Movement.LEFT,
							Movement.DOWN)
					&& !this.hitTarget(targets, box, Movement.UP)
					&& !this.hitTarget(targets, box, Movement.DOWN)
					&& !this.validMovement(walls, box, Movement.UP, Movement.UP)
					&& !this.validMovement(walls, box, Movement.DOWN,
							Movement.DOWN))
				return true;

			// on the right side
			if (!this.validMovement(walls, box, Movement.RIGHT)
					&& !this.validMovement(walls, box, Movement.RIGHT,
							Movement.UP)
					&& !this.validMovement(walls, box, Movement.RIGHT,
							Movement.DOWN)
					&& !this.hitTarget(targets, box, Movement.UP)
					&& !this.hitTarget(targets, box, Movement.DOWN)
					&& !this.validMovement(walls, box, Movement.UP, Movement.UP)
					&& !this.validMovement(walls, box, Movement.DOWN,
							Movement.DOWN))
				return true;
		}

		return false;
	}

	private boolean cornered(Set<Coordinate2D> walls, Set<Coordinate2D> boxes,
			Coordinate2D box, Movement movement1, Movement movement2) {
		boolean cornered = false;
		Coordinate2D direction1 = movement1.direction();
		Coordinate2D direction2 = movement2.direction();
		Coordinate2D boxMove1 = new Coordinate2D(box.X + direction1.X,
				box.Y + direction1.Y);
		Coordinate2D boxMove2 = new Coordinate2D(box.X + direction2.X,
				box.Y + direction2.Y);
		Coordinate2D boxMove3 = new Coordinate2D(
				box.X + direction1.X + direction2.X,
				box.Y + direction1.Y + direction2.Y);

		cornered = walls.contains(boxMove1) && walls.contains(boxMove2);
		if (cornered)
			return true;

		cornered = boxes.contains(boxMove1) && walls.contains(boxMove2)
				&& walls.contains(boxMove3);
		if (cornered)
			return true;

		cornered = walls.contains(boxMove1) && boxes.contains(boxMove2)
				&& walls.contains(boxMove3);
		if (cornered)
			return true;

		cornered = boxes.contains(boxMove1) && boxes.contains(boxMove2)
				&& boxes.contains(boxMove3);
		if (cornered)
			return true;

		cornered = boxes.contains(boxMove1) && boxes.contains(boxMove2)
				&& walls.contains(boxMove3);
		if (cornered)
			return true;

		return false;
	}

	private boolean hitTarget(Set<Coordinate2D> targets, Coordinate2D box,
			Movement movement) {
		Coordinate2D direction = movement.direction();
		Coordinate2D boxMove = new Coordinate2D(box.X + direction.X,
				box.Y + direction.Y);
		return targets.contains(boxMove);
	}

	private boolean validMovement(Set<Coordinate2D> walls, Coordinate2D box,
			Movement movement) {
		Coordinate2D direction = movement.direction();
		Coordinate2D boxMove = new Coordinate2D(box.X + direction.X,
				box.Y + direction.Y);
		return !walls.contains(boxMove);
	}

	private boolean validMovement(Set<Coordinate2D> walls, Coordinate2D box,
			Movement movement1, Movement movement2) {
		Coordinate2D direction1 = movement1.direction();
		Coordinate2D direction2 = movement2.direction();
		Coordinate2D boxMove = new Coordinate2D(
				box.X + direction1.X + direction2.X,
				box.Y + direction1.Y + direction2.Y);
		return !walls.contains(boxMove);
	}

	private void teatAndAdd(Movement movement, Coordinate2D player,
			Set<Coordinate2D> walls, Set<Coordinate2D> boxes,
			List<Movement> movementList) {
		Coordinate2D direction = movement.direction();
		Coordinate2D move2 = new Coordinate2D(player.X + direction.X,
				player.Y + direction.Y);
		Coordinate2D movemove = new Coordinate2D(
				player.X + direction.X + direction.X,
				player.Y + direction.Y + direction.Y);

		if (!walls.contains(move2)) {
			if (boxes.contains(move2)
					&& (boxes.contains(movemove) || walls.contains(movemove)))
				;
			else
				movementList.add(movement);
		}
	}

	@Override
	public BoardState clone() {
		HashSet<Coordinate2D> b = new HashSet<Coordinate2D>(boxes);
		Coordinate2D p = new Coordinate2D(player.X, player.Y);
		return new BoardState(b, targets, walls, p, row, col);
	}

}
