package com.asserta.gameoflife;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameOfLifeTest {

	private GameOfLife game;

	@BeforeEach
	public void setup() {
		game = new GameOfLife();
	}

	@Test
	public void testInitGrid() {
		game.initGrid();
		int[][] grid = game.getGrid();

		boolean thereAreLivingCells = false;
		for (int[] fila : grid) {
			for (int cela : fila) {
				if (cela == 1) {
					thereAreLivingCells = true;
					break;
				}
			}
		}

		assertTrue(thereAreLivingCells, "The grid should have at least one live cell");
	}

	@Test
	public void testUpdateGrid() {
		game.initGrid();
		int[][] before = game.getGrid();
		game.updateGrid();
		int[][] after = game.getGrid();

		assertNotNull(after);
		assertEquals(before.length, after.length);
		assertEquals(before[0].length, after[0].length);
	}

	@Test
	public void testCellDiesOfLoneliness() {
		// Configure a single cell
		int[][] grid = new int[5][5];
		grid[2][2] = 1;
		game = new GameOfLifePersonalitzat(grid);
		game.updateGrid();

		assertEquals(0, game.getGrid()[2][2], "The cell should die of loneliness");
	}

	// Inner class to inject custom grid
	static class GameOfLifePersonalitzat extends GameOfLife {
		public GameOfLifePersonalitzat(int[][] grid) {
			super(grid);
		}
	}
}
