package com.asserta.gameoflife;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.asserta.savegrid.SaveGrid;

@Component
public class GameOfLife {
	private int rows = 0;
	private int cols = 0;
	private int[][] grid = null;
	private Path saveFile = null;

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
	
	private Path initSaveFile() {
		
		Path tempFile = null;
		
		try {
			
			Path tempDir = Files.createTempDirectory("gameoflife");
			tempFile = Files.createTempFile(tempDir, "game_", ".txt");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tempFile;
	}
	
	private void saveGrid() {
		
		if (saveFile == null) {
			return;
		}
		
		SaveGrid.saveBinary(grid, saveFile);
	}
}
