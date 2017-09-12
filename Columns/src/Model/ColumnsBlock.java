package Model;

public class ColumnsBlock {
	private int xPos;
	private int yPos;
	public final static int BLOCKHEIGHT = IColumns.BLOCKSIZE;
	private final int[] BLOCKCOLORS = new int[BLOCKHEIGHT]; // the array of block's colors -
							   // is an array of indexes at IColumn.color colors array
	/* After merging block into gameboard and searching for same colors
	we want to repaint board to indicate cells with common colors.
	We have to set a block to be invisible, since when canvas processes
	the repaintComponent() method it draws gameboard first and than draws
	the block after that. If block is visible all its colors will be displayed again,
	and any marked cells as common colors will be unmarked and replaced by a block colors. */
	private boolean visible;

	ColumnsBlock() {
		visible = true;
		xPos =  (int)(Math.ceil((double) IColumns.GAME_FIELD_WIDTH / 2)) - 1;
		yPos = 0;
		setColors();
	}

	// This method fills the array of blocks colors with indexes at IColumn.color colors array
	private void setColors() {
		for (int i = 0; i < BLOCKHEIGHT; i++) {
			// random from 2 since the index 0 in the array is for the background color
			// and index 1 is for the matched colors
			BLOCKCOLORS[i] = (int) (2 + Math.random() * (IColumns.COLORS.length - 2));
		}
	}

	public int[] getBlockColors() {
		return BLOCKCOLORS;
	}


	public int getColor(int i) {
		if (i < 0 || i > BLOCKCOLORS.length)
			throw new IllegalArgumentException("Argument must be positive " +
                    "and no more then " + (BLOCKCOLORS.length-1));
		return BLOCKCOLORS[i];
	}

	// changing colors in the block
	void turnColors() {
		int color0 = BLOCKCOLORS[0];

		for(int i = 0; i< BLOCKHEIGHT - 1; i++) {
			BLOCKCOLORS[i] = BLOCKCOLORS[i+1];
		}
		BLOCKCOLORS[BLOCKHEIGHT - 1] = color0;
	}

	public void move(int direction) {

		switch (direction) {
			case IColumns.LEFT:
				xPos--;
				break;
			case IColumns.RIGHT:
				xPos++;
				break;
			case IColumns.DOWN:
				yPos++;
				break;
		}
	}

	// defines if the block touches the edges of game field or touches the cells on the game board
	public boolean isTouchesBoard(ColumnsBoard board, int direction) {

        switch (direction) {
            case IColumns.LEFT:
                if (xPos-1 < 0 ||
                   board.getColor(xPos-1, yPos+IColumns.BLOCKSIZE-1) != IColumns.BACKGROUND_COLOR) return true;
                break;
            case IColumns.RIGHT:
                if (xPos+1 >= IColumns.GAME_FIELD_WIDTH ||
                   board.getColor(xPos+1, yPos+IColumns.BLOCKSIZE-1) != IColumns.BACKGROUND_COLOR) return true;
                break;
            case IColumns.DOWN:
                if (yPos + IColumns.BLOCKSIZE >= IColumns.GAME_FIELD_HEIGHT ||
                   board.getColor(xPos, yPos+IColumns.BLOCKSIZE) != IColumns.BACKGROUND_COLOR) return true;
                break;
        }
		return false;
	}


	public void displayColorsAsString() {
		for(int c: BLOCKCOLORS) {
			System.out.println(IColumns.BLOCK_COLORS[c]);
		}
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public void setX(int x){
		xPos = x;
	}

	public void setY(int y){
		yPos = y;
	}

	public void isVisible(boolean b) {visible = b;}
    public boolean isVisible() {return visible;}

    /*
    Due to true MVC i removed column block drawing routine into ColumnPanel class
	public void drawBlock(Graphics g) {
		if (!visible) return;

		for (int i=0; i < BLOCKHEIGHT; i++) {
			g.setColor(IColumns.COLORS[BLOCKCOLORS[i]]);
			g.fillRect(xPos * IColumns.CELLSIZE, yPos * IColumns.CELLSIZE + IColumns.CELLSIZE * i,
					IColumns.CELLSIZE, IColumns.CELLSIZE);
			g.setColor(Color.BLACK);
			g.drawRect(xPos * IColumns.CELLSIZE, yPos * IColumns.CELLSIZE + IColumns.CELLSIZE * i,
					IColumns.CELLSIZE-1, IColumns.CELLSIZE-1);
		}
	}
	*/

}
