package Model;

import View.IColumnsView;
import sun.util.calendar.LocalGregorianCalendar;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class ColumnsGame {

    private IColumnsView view;
    private ColumnsBoard gameBoard;
    private ColumnsBlock currentBlock; // a falling block

    private int gameScore;
    private boolean gameOver; // flag defining if game is over

    private int timeDelay = IColumns.TIME_DELAY;

    public ColumnsGame(IColumnsView view) {
        this.view = view;
        gameOver = true;
    }

    public void restartGame() {

        gameScore = 0;
        resetGameBoard();
        currentBlock = new ColumnsBlock();
        gameOver = false;
    }

    private void resetGameBoard() {
        gameBoard = new ColumnsBoard();
    }

    public void dropBlock() {
        while (!currentBlock.isTouchesBoard(gameBoard, IColumns.DOWN))
             currentBlock.move(IColumns.DOWN);
    }

    public void rotateBlockColors() {
        if (!gameOver) { currentBlock.turnColors(); }
    }

    public void moveBlock(int direction) {
        if (gameOver) return;
        if (! currentBlock.isTouchesBoard(gameBoard, direction)) {
            currentBlock.move(direction);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public ColumnsBoard getGameBoard() {
        return gameBoard;
    }

    public ColumnsBlock getBlock() {
        return currentBlock;
    }


    void scoreUp(int value) {
        gameScore += value;
        view.updateScore(gameScore);

        timeDelay -= ((gameScore % 10 == 0) ? 10 : 0);
    }

    public int getTimeDelay() {
        return timeDelay;
    }


    public void timeTick() {

        view.redrawBoard();

        if (currentBlock.isTouchesBoard(gameBoard, IColumns.DOWN)) {
            gameBoard.mergeBlock(currentBlock);
             /* After merging block into gameboard and searching cells with same colors
	            we want to repaint board to indicate cells with common colors.
	            Here we have to set an invisible flag for the block to be invisible,
	            since when canvas processes the repaintComponent() method it repaints gameboard first,
	            - and than it repaints the block after that. If block is visible, -
	            all of its colors will be displayed again, and any marked cells as common colors
	            will be unmarked and replaced by a block colors. */
            currentBlock.isVisible(false);


            int matches; /* each match gives one score point.
                          one match is the similar colors on the game board
                          in one direction, - left, right, etc.*/
            while ((matches = gameBoard.findMatchedColors()) > 0) {

                Thread drawThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        view.redrawBoard();
                    }
                });
                drawThread.start();

                try {
                    drawThread.join();
                    Thread.sleep(200);
                    gameBoard.deleteMatchedColors();
                    scoreUp(matches);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            currentBlock = new ColumnsBlock();
            gameOver = !gameBoard.isEnoughRoomForNewBlock();

        } else {
            currentBlock.move(IColumns.DOWN);
        }
    }
}
