package component;

public class Node {

	Node parent;
	BoardState board;
	Movement move;
	int cost;

	Node(Node p, BoardState b, Movement m, int c) {
		this.parent = p;
		this.board = b;
		this.move = m;
		this.cost = c;
	}

	@Override
	public int hashCode() {
		return this.board.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object == this)
			return true;
		if (this.getClass() != object.getClass())
			return false;
		if (this.board == ((Node) object).board) {
			return true;
		}
		if (this.board.equals(((Node) object).board)) {
			return true;
		}
		return false;
	}

}
