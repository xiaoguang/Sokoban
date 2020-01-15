package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import component.BoardState;
import component.Coordinate2D;

public class Sokoban {

	private BoardState board;
	private String inputString = "";

	public Sokoban() {
	}

	/**
	 * Reads each line from the input file and adds each character to walls,
	 * goals, player or boxes
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 * @throws NumberFormatException
	 * @throws NoSuchElementException
	 */
	public void loadFile(String filename) throws FileNotFoundException,
			NumberFormatException, NoSuchElementException {
		int X = 0;
		int Y = 0;
		HashSet<Coordinate2D> boxes = new HashSet<Coordinate2D>();
		HashSet<Coordinate2D> targets = new HashSet<Coordinate2D>();
		HashSet<Coordinate2D> walls = new HashSet<Coordinate2D>();
		Coordinate2D player = null;

		List<String> input = new ArrayList<String>();
		Scanner s = new Scanner(new File(filename));
		while (s.hasNext()) {
			String next = s.nextLine();
			Y += 1;
			if (next.length() > X)
				X = next.length();
			input.add(next);
			inputString = inputString.concat(next);
			inputString = inputString.concat(System.lineSeparator());
		}

		for (int i = 0; i < Y; i++) {
			String next = input.get(i);
			for (int j = 0; j < next.length(); j++) {
				char c = next.charAt(j);
				if (c == '#') // walls
					walls.add(new Coordinate2D(j, i));
				if (c == 'P' || c == 'p') { // player
					player = new Coordinate2D(j, i);
				}
				if (c == 'T' || c == 'b' || c == 'p') // targets
					targets.add(new Coordinate2D(j, i));
				if (c == 'B' || c == 'b') // boxes
					boxes.add(new Coordinate2D(j, i));
			}
			if (next.length() > X)
				X = next.length();
		}
		s.close();

		this.board = new BoardState(boxes, targets, walls, player, Y, X);
	}

	public BoardState getBoard() {
		return board;
	}

	public String getInputString() {
		return inputString;
	}
}
