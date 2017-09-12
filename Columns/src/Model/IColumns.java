package Model;

import java.awt.Color;

public interface IColumns {
	// the size of game board - number of cells high and width
	int GAME_FIELD_HEIGHT = 18;
	int GAME_FIELD_WIDTH = 7;
	
	// the height of the falling block in the game - the size value is the number of cells
	int BLOCKSIZE = 3;

	// size of a cell in pixels
	int CELLSIZE = 30;

	/** basic colors in the game
	 * color[0] = Color.GRAY - is a board background
	 * color[1] = Color.BLACK - is color of matched cells */
	Color[] COLORS = {Color.GRAY, new Color(210, 254, 207), Color.MAGENTA, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN};
	int BACKGROUND_COLOR = 0;
	int MATCH_COLOR = 1;


	// an array of names of the colors for testing purposes - to display name of color
    String[] BLOCK_COLORS = new String[] {"GRAY", "WHITE", "MAGENTA", "BLUE", "RED", "GREEN", "YELLOW", "CYAN" };

	// values for directions of falling block
	int DOWN = 0;
	int LEFT = 1;
	int RIGHT = 2;

	int TIME_DELAY = 750;

	int[][] GAME_OVER_MSG = {
			{0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
			{1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
			{1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
			{1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
			{0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
			{1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
			{1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
			{1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
			{0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}};

}
