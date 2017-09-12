package Model;

import java.awt.*;
import java.util.*;

public class ColumnsBoard {

    private final int HEIGHT = IColumns.GAME_FIELD_HEIGHT;
    private final int WIDTH = IColumns.GAME_FIELD_WIDTH;
    private final int[][] GAMEBOARD = new int[HEIGHT][WIDTH];

    // this array to store found matches. handy to use when delete same cells and shift upper ones.
    private boolean[][] matches;
    // this collection to store unique matches and for calculating score based on how many matches was found
    private Set<ColumnCell> matchedColorsCells = new HashSet<>();

    ColumnsBoard() { }

    public int getColor(int x, int y) {
        return GAMEBOARD[y][x];
    }

    void setColor(int x, int y, int c) {
        GAMEBOARD[y][x] = c;
    }

    public void mergeBlock(ColumnsBlock block) {
        for (int i = 0; i < IColumns.BLOCKSIZE; i++) {
            setColor(block.getX(), block.getY()+i, block.getColor(i));
        }
    }

    // method defines if there is enough space for new block
    public boolean isEnoughRoomForNewBlock() {
        // define the center of the board
        int x = (int)(Math.ceil((double) IColumns.GAME_FIELD_WIDTH / 2)) - 1;

        return (getColor(x, 2) == IColumns.BACKGROUND_COLOR);
    }

    public int findMatchedColors() {
        matches = new boolean[HEIGHT][WIDTH];

        for (int y = IColumns.GAME_FIELD_HEIGHT-1; y >= 0; y--) {
            for (int x = 0; x < IColumns.GAME_FIELD_WIDTH; x++) {
                if (getColor(x, y) != IColumns.BACKGROUND_COLOR) {

                    // looking for 3 same colors into one direction

                    search_Left(x, y);
                    search_Right(x, y);
                    search_Down(x, y);
                    search_Up(x, y);

                    search_UpLeft(x, y);
                    search_UpRight(x, y);
                    search_DownLeft(x, y);
                    search_DownRight(x, y);

                    //looking for the cases when same colors could be at both sides of block cell which is in the center
                    search_Right(x - 1, y); // equal to search_Left in this case
                    search_DownRight(x - 1, y - 1); // equal to search_UpLeft in this case
                    search_UpRight(x - 1, y + 1); // equal to search_DownLeft in this case
                    search_Down(x, y-1);
                }
            }
        }

        return matchedColorsCells.size() / 3;
    }

    private boolean cellIsValid(int x, int y) {
        if (x < 0) return false;
        if (x >= IColumns.GAME_FIELD_WIDTH) return false;
        if (y >= IColumns.GAME_FIELD_HEIGHT) return false;
        if (y < 0) return false;

        return true;
    }

    // return value is not used
    private boolean search_Left(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (x-2 < 0) return false;

        /* we have to check colors of three cells. if colors of cells are equal
        the value of result variable will be more then 0;
        */
        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y][x-i]) ? 1 : 0;
        }
        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                // mark the cell in array of matched colors
                matches[y][x-i] = true;
                matchedColorsCells.add(new ColumnCell(x-i, y));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_UpLeft(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (x-2 < 0) return false;
        if (y-2 < 0) return false;

        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y-i][x-i]) ? 1 : 0;
        }
        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                // mark the cell in array of matched colors
                matches[y-i][x-i] = true;
                matchedColorsCells.add(new ColumnCell(x-i, y-i));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_DownLeft(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (x-2 < 0) return false;
        if (y+2 > IColumns.GAME_FIELD_HEIGHT-1) return false;

        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y+i][x-i]) ? 1 : 0;
        }
        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                // mark the cell in array of matched colors
                matches[y+i][x-i] = true;
                matchedColorsCells.add(new ColumnCell(x-i, y+i));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_Right(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (x+2 > IColumns.GAME_FIELD_WIDTH-1) return false;

        /* we have to check colors of three cells. if colors of cells are equal
        the value of result variable will be more then 0;
        */
        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y][x+i]) ? 1 : 0;
        }

        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                matches[y][x+i] = true;
                matchedColorsCells.add(new ColumnCell(x+i, y));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_UpRight(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (x+2 > IColumns.GAME_FIELD_WIDTH-1) return false;
        if (y-2 < 0) return false;

        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y-i][x+i]) ? 1 : 0;
        }

        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                matches[y-i][x+i] = true;
                matchedColorsCells.add(new ColumnCell(x+i, y-i));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_DownRight(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (x+2 > IColumns.GAME_FIELD_WIDTH-1) return false;
        if (y+2 > IColumns.GAME_FIELD_HEIGHT-1) return false;

        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y+i][x+i]) ? 1 : 0;
        }

        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                matches[y+i][x+i] = true;
                matchedColorsCells.add(new ColumnCell(x+i, y+i));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_Down(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (y+2 > IColumns.GAME_FIELD_HEIGHT-1) return false;

        /* we have to check colors of three cells. if colors of cells are equal
        the value of result variable will be more then 0;
        */
        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y+i][x]) ? 1 : 0;
        }

        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                matches[y+i][x] = true;
                matchedColorsCells.add(new ColumnCell(x, y+i));
            }
        }

        // return value is not used
        return result > 0;
    }

    // return value is not used
    private boolean search_Up(int x, int y) {
        if (!cellIsValid(x, y)) return false;

        int currentColor = getColor(x, y);
        if (currentColor == IColumns.BACKGROUND_COLOR) return false;
        if (y-2 < 0) return false;

        int result = 1;

        for (int i = 1; i <= 2; i++) {
            result *= (currentColor == GAMEBOARD[y-i][x]) ? 1 : 0;
        }

        // if all colors are equal
        if (result > 0) {
            for (int i = 0; i <= 2; i++) {
                matches[y-i][x] = true;
                matchedColorsCells.add(new ColumnCell(x, y-i));
            }
        }

        // return value is not used
        return result > 0;
    }

    public void deleteMatchedColors() {
        matchedColorsCells.clear();

        for (int y = IColumns.GAME_FIELD_HEIGHT-1; y >= 0; y--) {
            for (int x = 0; x < IColumns.GAME_FIELD_WIDTH; x++) {
                //if (matches[y][x]) {
                    while (matches[y][x]) {
                        for (int count=y; count > 0; count--) {
                            GAMEBOARD[count][x] = GAMEBOARD[count-1][x];
                            matches[count][x] = matches[count-1][x];
                            if (getColor(x, count) == IColumns.BACKGROUND_COLOR ) break;
                        }
                        GAMEBOARD[0][x] = IColumns.BACKGROUND_COLOR;
                    }
                //}
            }
        }
    }


    @Override
    public String toString() {
        String result = "";
        for (int y = 0; y < HEIGHT; y++) {

            for (int x = 0; x < WIDTH; x++) {
              result += "|" + GAMEBOARD[y][x];
            }
            result += "|\n";
        }

        for (int i = 0; i < WIDTH+2; i++) {
            result += "-";
        }

        return result;
    }

    /*
    public void drawBoard(Graphics g) {

        for (int y = 0; y < IColumns.GAME_FIELD_HEIGHT; y++) {
            for (int x = 0; x < IColumns.GAME_FIELD_WIDTH; x++) {

                g.setColor(IColumns.COLORS[getColor(x, y)]);
                g.fillRect(x * IColumns.CELLSIZE, y * IColumns.CELLSIZE,
                        IColumns.CELLSIZE, IColumns.CELLSIZE);

                if (GAMEBOARD[y][x] != IColumns.BACKGROUND_COLOR) {
                    g.setColor(Color.BLACK);
                    g.drawRect(x * IColumns.CELLSIZE, y * IColumns.CELLSIZE,
                            IColumns.CELLSIZE - 1, IColumns.CELLSIZE-1);
                }
            }
        }

        for (ColumnCell cell : matchedColorsCells) {
            int x = cell.getX();
            int y = cell.getY();

            g.setColor(IColumns.COLORS[IColumns.MATCH_COLOR]);
            g.drawRect(x * IColumns.CELLSIZE, y * IColumns.CELLSIZE,
                    IColumns.CELLSIZE-1, IColumns.CELLSIZE-1);
        }
    }

    */

    public Set<ColumnCell> getMatchedCells() {
        return matchedColorsCells;
    }

    public int[][] getGameBoard() {
        return GAMEBOARD;
    }
}
