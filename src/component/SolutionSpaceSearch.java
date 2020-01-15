package component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SolutionSpaceSearch {

	public static final String UCS = "UCS";
	public static final String AStar = "AStar";
	public static final String VERBOSE = "Verbose";

	public static Heuristic heuristic;

	public SolutionSpaceSearch(BoardState board) {
		heuristic = new Heuristic(board);
	}

	public Node searchFrame(BoardState board, String algorithm) {
		if (algorithm == null
				|| !(algorithm.equals(UCS) || algorithm.equals(AStar))) {
			return null;
		}

		long startTime = System.currentTimeMillis();
		int totalNode = 1;
		int redundant = 0;
		Node node = new Node(null, board, null, 0);

		Set<BoardState> explored = new HashSet<BoardState>();
		// LinkedHashSet for BFS
		// LinkedHashSet<Node> frontier = new LinkedHashSet<Node>();
		HashSet<Node> indexedFrontier = new HashSet<Node>();
		Queue<Node> frontier;
		if (algorithm.equals(UCS))
			frontier = new PriorityQueue<Node>(ucsComparator);
		else
			frontier = new PriorityQueue<Node>(aStarComparator);
		indexedFrontier.add(node);
		frontier.add(node);
		List<Movement> actions;
		Node child;

		while (!frontier.isEmpty()) {
			node = frontier.poll();
			indexedFrontier.remove(node);
			explored.add(node.board);

			// A solution
			if (node.board.targetTest()) {
				String solution = getSolution(algorithm, node, totalNode,
						redundant, frontier.size(), explored.size(),
						System.currentTimeMillis() - startTime);
				System.out.println(solution);
				return node;
			}

			// boolean nodeDeadLock = true;
			actions = node.board.possibleMovement();
			for (Movement act : actions) {
				child = this.getChild(node, act);
				totalNode++;
				if ((!explored.contains(child.board))
						&& (!indexedFrontier.contains(child))) {
					if (!child.board.deadlockTest()) {
						frontier.add(child);
						indexedFrontier.add(child);
					}
				} else
					redundant++;
			}
		}

		// Not solvable
		String solution = getSolution(algorithm, null, totalNode, redundant,
				frontier.size(), explored.size(),
				System.currentTimeMillis() - startTime);
		System.out.println(solution);

		return null;
	}

	private String getSolution(String method, Node n, int totalNode,
			int redundant, int fringeSize, int exploredSize, long totalTime) {
		String result = "";
		int steps = 0;
		if (n == null)
			result = "Failed to solve the puzzle";
		else {
			result = "Find a solution for the puzzle";
			while (n.parent != null) {
				n = n.parent;
				steps++;
			}
		}
		result = "Using " + method + ":\n" + result + "\n(total of " + steps
				+ " steps)" + "\na) Number of nodes generated: " + totalNode
				+ "\nb) Number of nodes containing states that were generated previously: "
				+ redundant
				+ "\nc) Number of nodes on the fringe when termination occurs: "
				+ fringeSize
				+ "\nd) Number of nodes on the explored list (if there is one) when termination occurs: "
				+ exploredSize
				+ "\ne) The run time of the algorithm milli seconds: "
				+ totalTime + "ms" + System.lineSeparator();
		return result;
	}

	private Node getChild(Node n, Movement m) {
		BoardState board = n.board.clone();
		Set<Coordinate2D> boxes = board.boxes;
		Coordinate2D player = board.player;
		Coordinate2D moveTo = board.player;
		int cost = n.cost + 1;

		// update player coordinate
		moveTo = new Coordinate2D(player.X + m.direction().X,
				player.Y + m.direction().Y);
		// check if player is pushing a box
		if (boxes.contains(moveTo)) {
			Coordinate2D moveBox = new Coordinate2D(
					player.X + m.direction().X + m.direction().X,
					player.Y + m.direction().Y + m.direction().Y);
			// update box coordinate
			boxes.remove(moveTo);
			boxes.add(moveBox);
		}
		board.player = moveTo;

		return new Node(n, board, m, cost);
	}

	public String visualizer(Node solution, String verbose) {
		// no solution
		if (solution == null)
			return "No Solution For Puzzle\n";

		Node currNode = solution;
		Stack<Node> movements = new Stack<Node>();

		while (currNode != null) {
			movements.add(currNode);
			currNode = currNode.parent;
		}

		String str = "Find A Solution For Puzzle\n";
		while (!movements.isEmpty()) {
			Node node = movements.pop();
			if (node.move != null)
				str = str.concat(node.move.represent());
			if (node.move != null && !movements.isEmpty())
				str = str.concat(", ");
			if (verbose != null && VERBOSE.equals(verbose))
				str = str.concat("\n" + node.board.toString());
		}
		str = str.concat(System.lineSeparator());

		return str;
	}

	// A*
	public static Comparator<Node> aStarComparator = new Comparator<Node>() {
		@Override
		public int compare(Node n1, Node n2) {
			// System.out.println("N1 Value : " +
			// heuristic.calculate(n1.board));
			// System.out.println("N2 Value : " +
			// heuristic.calculate(n2.board));
			return (int) ((n1.cost + heuristic.calculate(n1.board))
					- (n2.cost + heuristic.calculate(n2.board)));
		}
	};

	// UCS
	public static Comparator<Node> ucsComparator = new Comparator<Node>() {
		@Override
		public int compare(Node n1, Node n2) {
			return (int) (n1.cost - n2.cost);
		}
	};

}
