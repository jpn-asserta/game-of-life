package com.asserta.gameoflife;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Component;

@Component
public class GameOfLife {
	private int rows = 0;
	private int cols = 0;
	private int[][] grid = null;
	private File saveFile = null;

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
		saveFile = initSaveFile();
		saveGrid();
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
		saveGrid();
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
	
	private File initSaveFile() {
		try {
			// BAD: -rw-r--r--
			return File.createTempFile("/tmp/game_" + System.currentTimeMillis(), ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void saveGrid() {
		
		if (saveFile == null) {
			return;
		}
		
		try {
			
			StringBuilder gridST = new StringBuilder(rows * cols * 2 + rows + 1);
			
			for (int[] row : grid) {
				for (int cell : row) {
					gridST.append(cell).append("|");
				}
				gridST.append("\n");
			}
			gridST.append("\n");
			
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile, true))) {
				writer.write(gridST.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
