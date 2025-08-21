package com.asserta.gameoflife;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
	private final GameOfLife game;

	public GameController(GameOfLife joc) {
		this.game = joc;
	}

	@PostMapping("/init")
	public String initGrid() {
		game.initGrid();
		return "Initialized grid";
	}

	@GetMapping("/get")
	public int[][] getGrid() {
		return game.getGrid();
	}

	@PostMapping("/update")
	public String updateGrid() {
		game.updateGrid();
		return "Updated grid";
	}
}
