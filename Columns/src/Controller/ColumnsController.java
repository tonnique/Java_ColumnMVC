package Controller;

import Model.ColumnsGame;
import Model.IColumns;
import View.ColumnPanel;
import View.IColumnsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ColumnsController implements KeyListener {

    ColumnsGame game;
    IColumnsView view;
    ColumnPanel canvas;
    Timer timer;


      public ColumnsController(IColumnsView v, ColumnPanel pnl) {
        this.view = v;
        this.canvas = pnl;
    }

    private void startGame() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        handlePressedKey(keyEvent.getKeyCode());
        view.redrawBoard();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    private void handlePressedKey(int keyCode) {
        if (game == null && (keyCode != KeyEvent.VK_SPACE &&
          keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_S)) return;

        switch (keyCode) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (game == null) {
                    game = new ColumnsGame(view);
                    canvas.setModel(game);
                }
                if (game.isGameOver()) {
                    game.restartGame();
                    startGame();
                } else {
                    game.dropBlock();
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                game.moveBlock(IColumns.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                game.moveBlock(IColumns.RIGHT);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                game.rotateBlockColors();
                break;
        }
    }


    private class Timer extends Thread {

        @Override
        public void run() {
            while(!game.isGameOver()) {

                try {
                    Thread.sleep(game.getTimeDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.redrawBoard();

                game.timeTick();

            }
            view.redrawBoard();
        }
    }

}

