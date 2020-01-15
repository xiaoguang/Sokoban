package unit;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import component.Coordinate2D;
import component.Movement;
import component.Node;
import component.SolutionSpaceSearch;
import solver.Sokoban;

public class UnitTest {

	@Test
	public void MovementTest() throws IOException {
		List<String> files = new ArrayList<String>();
		files.add("input/test0");
		files.add("input/test1");
		files.add("input/testcases_1box_m1.txt");
		files.add("input/testcases_1box_m2.txt");
		files.add("input/testcases_1box_m3.txt");
		files.add("input/testcases_2box_m1.txt");
		files.add("input/testcases_2box_m2.txt");
		files.add("input/testcases_2box_m3.txt");
		files.add("input/testcases_3box_m1.txt");
		files.add("input/testcases_3box_m2.txt");
		files.add("input/testcases_4box_m1.txt");
		files.add("input/testcases_4box_m2.txt");
		files.add("input/testcases_4box_m3.txt");
		files.add("input/testcases_6box_m2.txt");

		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);
			String str1 = s.getBoard().toString();
			String str2 = s.getInputString();
			assertTrue(str1.equals(str2));
		}
	}

	@Test
	public void EnumMovementTest() {
		assertTrue(Movement.UP.direction().equals(new Coordinate2D(0, -1)));
		assertTrue(Movement.UP.represent().equals("u"));

		assertTrue(Movement.DOWN.direction().equals(new Coordinate2D(0, 1)));
		assertTrue(Movement.DOWN.represent().equals("d"));

		assertTrue(Movement.LEFT.direction().equals(new Coordinate2D(-1, 0)));
		assertTrue(Movement.LEFT.represent().equals("l"));

		assertTrue(Movement.RIGHT.direction().equals(new Coordinate2D(1, 0)));
		assertTrue(Movement.RIGHT.represent().equals("r"));
	}

	@Test
	public void DeadLockTest() throws IOException {
		List<String> files = new ArrayList<String>();
		files.add("input/deadlock_0.txt");
		files.add("input/deadlock_1.txt");
		files.add("input/deadlock_2.txt");
		files.add("input/deadlock_3.txt");
		files.add("input/deadlock_4.txt");
		files.add("input/deadlock_5.txt");
		files.add("input/deadlock_6.txt");
		files.add("input/deadlock_7.txt");
		files.add("input/deadlock_8.txt");
		files.add("input/deadlock_9.txt");
		files.add("input/deadlock_10.txt");
		files.add("input/deadlock_11.txt");
		files.add("input/deadlock_12.txt");
		files.add("input/deadlock_13.txt");
		files.add("input/deadlock_14.txt");
		files.add("input/deadlock_15.txt");

		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);
			assertTrue(s.getBoard().deadlockTest());
		}
	}

	@Test
	public void NotDeadLockTest() throws IOException {
		List<String> files = new ArrayList<String>();
		files.add("input/non_deadlock_0.txt");
		files.add("input/non_deadlock_1.txt");
		files.add("input/non_deadlock_2.txt");
		files.add("input/non_deadlock_3.txt");

		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);
			assertTrue(!s.getBoard().deadlockTest());
		}
	}

	@Test
	public void CloneBoardStateTest() throws IOException {
		List<String> files = new ArrayList<String>();
		files.add("input/testcases_1box_m1.txt");
		files.add("input/testcases_1box_m2.txt");
		files.add("input/testcases_1box_m3.txt");
		files.add("input/testcases_2box_m1.txt");
		files.add("input/testcases_2box_m2.txt");
		files.add("input/testcases_2box_m3.txt");
		files.add("input/testcases_3box_m1.txt");
		files.add("input/testcases_3box_m2.txt");
		files.add("input/testcases_4box_m1.txt");
		files.add("input/testcases_4box_m2.txt");
		files.add("input/testcases_4box_m3.txt");
		files.add("input/testcases_6box_m2.txt");

		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);
			assertTrue(s.getBoard().equals(s.getBoard().clone()));
		}
	}

	@Ignore
	@Test
	public void SolverUCSTest() throws IOException {
		List<String> files = new ArrayList<String>();
		files.add("input/testcases_1box_m1.txt");
		files.add("input/testcases_1box_m2.txt");
		files.add("input/testcases_1box_m3.txt");
		files.add("input/testcases_2box_m1.txt");
		files.add("input/testcases_2box_m2.txt");
		files.add("input/testcases_2box_m3.txt");
		files.add("input/testcases_3box_m1.txt");
		files.add("input/testcases_3box_m2.txt");
		files.add("input/testcases_4box_m1.txt");
		files.add("input/testcases_4box_m2.txt");
		files.add("input/testcases_4box_m3.txt");
		files.add("input/testcases_6box_m2.txt");
		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);

			SolutionSpaceSearch search = new SolutionSpaceSearch(s.getBoard());
			Node solution = search.searchFrame(s.getBoard(), "UCS");
			search.visualizer(solution, null);
		}
	}

	@Ignore
	@Test
	public void SolverAStarTest() throws IOException {
		List<String> files = new ArrayList<String>();
		files.add("input/testcases_1box_m1.txt");
		files.add("input/testcases_1box_m2.txt");
		files.add("input/testcases_1box_m3.txt");
		files.add("input/testcases_2box_m1.txt");
		files.add("input/testcases_2box_m2.txt");
		files.add("input/testcases_2box_m3.txt");
		files.add("input/testcases_3box_m1.txt");
		files.add("input/testcases_3box_m2.txt");
		files.add("input/testcases_4box_m1.txt");
		files.add("input/testcases_4box_m2.txt");
		files.add("input/testcases_4box_m3.txt");
		files.add("input/testcases_6box_m2.txt");
		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);

			SolutionSpaceSearch search = new SolutionSpaceSearch(s.getBoard());
			Node solution = search.searchFrame(s.getBoard(), "AStar");
			search.visualizer(solution, null);
		}
	}

	@Test
	public void allocationSolverTest() throws IOException {
		List<String> files = new ArrayList<String>();
		/*-
		files.add("input/testcases_1box_m1.txt");
		files.add("input/testcases_1box_m2.txt");
		files.add("input/testcases_1box_m3.txt");
		files.add("input/testcases_2box_m1.txt");
		files.add("input/testcases_2box_m2.txt");
		files.add("input/testcases_2box_m3.txt");
		files.add("input/testcases_3box_m1.txt");
		files.add("input/testcases_3box_m2.txt");
		files.add("input/testcases_4box_m1.txt");
		files.add("input/testcases_4box_m2.txt");
		files.add("input/testcases_4box_m3.txt");
		files.add("input/testcases_6box_m2.txt");
		*/
		files.add("input/testcases_3box_m1.txt");

		for (String file : files) {
			Sokoban s = new Sokoban();
			System.out.println("Test : " + file);
			s.loadFile(file);

			SolutionSpaceSearch search = new SolutionSpaceSearch(s.getBoard());
			search.heuristic.solver.solve();
		}
	}

}
