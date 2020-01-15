package component;

import java.util.Arrays;

import utils.AllocationSolver;

public class Heuristic {

	private int[][] cost;
	private boolean[][] allocaton;
	private BoardState board;
	public AllocationSolver solver;

	public Heuristic(BoardState board) {
		this.board = board;
		this.cost = new int[this.board.boxes.size()][this.board.targets.size()];
		this.allocaton = new boolean[this.board.boxes.size()][this.board.targets
				.size()];
		for (int i = 0; i < this.board.boxes.size(); i++) {
			Arrays.fill(this.cost[i], 0);
			Arrays.fill(this.allocaton[i], false);
		}
		this.solver = new AllocationSolver(this.cost, this.allocaton,
				this.board.boxes.size(), this.board.targets.size());
	}

	private static int manhattan(Coordinate2D c1, Coordinate2D c2) {
		return Math.abs(c1.X - c2.X) + Math.abs(c1.Y - c2.Y);
	}

	public int calculate(BoardState bs) {
		int i = 0;
		for (Coordinate2D b : bs.boxes) {
			int j = 0;
			int playerCost = manhattan(bs.player, b);
			for (Coordinate2D t : bs.targets) {
				this.cost[i][j] = manhattan(b, t);
				this.cost[i][j] += playerCost;
				j++;
			}
			i++;
		}

		int max = 0;
		/*-
		int[] result = this.hungary.execute(cost);
		
		for (int k = 0; k < this.bs.targets.size(); k++) {
			int goalCol = result[k];
			if (goalCol > -1)
				max += cost[k][goalCol];
		}
		*/

		return max;
	}

}
