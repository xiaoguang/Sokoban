package utils;

public class AllocationSolver {

	private int[][] cost;
	private boolean[][] allocaton;
	private int rows;
	private int cols;

	public AllocationSolver(int[][] cost, boolean[][] allocaton, int boxes,
			int targets) {
		this.cost = cost;
		this.allocaton = allocaton;
		this.rows = boxes;
		this.cols = targets;
	}

	private boolean isSafe(int row, int col) {
		for (int i = 0; i < row; i++) {
			// if taken
			if (this.allocaton[i][col] == true)
				return false;
		}
		return true;
	}

	private boolean solve(int row) {
		if (row == this.rows) {
			System.out.println("Solved");
			this.printHelper(this.allocaton, this.rows, this.cols);
			return true;
		}
		for (int j = 0; j < this.cols; j++) {
			// returns if not safe
			if (this.isSafe(row, j)) {
				this.allocaton[row][j] = true;
				solve(row + 1);
				this.allocaton[row][j] = false;
			}
		}
		System.out.println("Not Solved");
		this.printHelper(this.allocaton, this.rows, this.cols);
		return false;
	}

	public void solve() {
		this.solve(0);
	}

	private void printHelper(boolean[][] array2D, int r, int c) {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				System.out.print(array2D[i][j] + " ");
			}
			System.out.println();
		}
	}

}
