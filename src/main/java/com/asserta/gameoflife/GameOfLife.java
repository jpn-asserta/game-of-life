package com.asserta.gameoflife;

import org.springframework.stereotype.Component;

@Component
public class GameOfLife {
	private int rows = 0;
	private int cols = 0;
	private int[][] grid = null;

	public GameOfLife() {
		rows = 20;
		cols = 40;
		grid = new int[rows][cols];
	}

	public GameOfLife(int[][] grid) {
		this.grid = grid;
		this.rows = grid.length;
		this.cols = grid[0].length;
	}

	public void initGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = Math.random() < 0.3 ? 1 : 0;
			}
		}
	}

	public int[][] getGrid() {
		return grid;
	}

	public void updateGrid() {
		int[][] newGrid = new int[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int veinesVives = countNeighbors(i, j);
				newGrid[i][j] = (grid[i][j] == 1) ? (veinesVives == 2 || veinesVives == 3 ? 1 : 0)
				        : (veinesVives == 3 ? 1 : 0);
			}
		}

		grid = newGrid;
	}

	private int countNeighbors(int x, int y) {
		int counter = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				int fila = x + i;
				int col = y + j;
				if (fila >= 0 && fila < rows && col >= 0 && col < cols) {
					counter += grid[fila][col];
				}
			}
		}
		return counter;
	}
}
