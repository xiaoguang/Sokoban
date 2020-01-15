package component;

import utils.GlobalConfig;

public class Coordinate2D {

	int X;
	int Y;

	public Coordinate2D(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	@Override
	public int hashCode() {
		return GlobalConfig.bigPrime * X + GlobalConfig.smallPrime * Y;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object == this)
			return true;
		if (this.getClass() != object.getClass())
			return false;
		Coordinate2D c = (Coordinate2D) object;
		return ((this.X == c.X) && (this.Y == c.Y));
	}

}
