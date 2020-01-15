package solver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import component.Node;
import component.SolutionSpaceSearch;

public class Driver {

	protected static void TestAStar() throws Exception {
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

	protected static void TestUCS() throws Exception {
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

	public static void main(String[] args) throws Exception {
		// TestAStar();
		// TestUCS();
		String input = args[0];
		String output = args[1];
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));

		{
			Sokoban s = new Sokoban();
			System.out.println("Test : " + input);
			s.loadFile(input);

			SolutionSpaceSearch search = new SolutionSpaceSearch(s.getBoard());
			Node solution = search.searchFrame(s.getBoard(), "UCS");
			String result = search.visualizer(solution, null);

			writer.write(result);

		}

		{
			Sokoban s = new Sokoban();
			System.out.println("Test : " + input);
			s.loadFile(input);

			SolutionSpaceSearch search = new SolutionSpaceSearch(s.getBoard());
			Node solution = search.searchFrame(s.getBoard(), "AStar");
			String result = search.visualizer(solution, null);

			writer.write(result);
			writer.close();
		}

		writer.close();
	}

}
