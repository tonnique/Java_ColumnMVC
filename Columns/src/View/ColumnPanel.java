package View;

import Model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ColumnPanel extends JPanel {

    ColumnsGame model;

    private int height = IColumns.GAME_FIELD_HEIGHT;
    private int width = IColumns.GAME_FIELD_WIDTH;
    private int cellSize = IColumns.CELLSIZE;

    public ColumnPanel() {
        setPreferredSize(new Dimension(width * cellSize, height * cellSize));
    }

    public void setModel(ColumnsGame game) {
        this.model = game;
    }

    private void displayHello(Graphics g) {

        g.setColor(Color.green);
        g.setFont(new Font("San-Serif", Font.BOLD, 20));


        g.drawString("To Start The Game", 20, 70);
        g.drawString("Press Space", 50, 100);

    }

    private void displayGameOver(Graphics g) {
        g.setColor(Color.WHITE);
        for (int y = 0; y < IColumns.GAME_OVER_MSG.length; y++)
            for (int x = 0; x < IColumns.GAME_OVER_MSG[y].length; x++)
                if (IColumns.GAME_OVER_MSG[y][x] == 1)
                    g.fillRect(x*9+15, y*11+160, 10, 10);

        displayHello(g);
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //if (model == null || !model.isGamePlaying()) {
        if (model == null) {
           displayHello(g);
        }

        else {

          drawBoard(g, model.getGameBoard());


          if (model.isGameOver()) {
              displayGameOver(g);
          } else {
              drawBlock(g, model.getBlock());
          }
        }
    }


    public void drawBoard(Graphics g, ColumnsBoard board) {

        for (int y = 0; y < IColumns.GAME_FIELD_HEIGHT; y++) {
            for (int x = 0; x < IColumns.GAME_FIELD_WIDTH; x++) {

                g.setColor(IColumns.COLORS[board.getColor(x, y)]);
                g.fillRect(x * IColumns.CELLSIZE, y * IColumns.CELLSIZE,
                        IColumns.CELLSIZE, IColumns.CELLSIZE);

                if (board.getColor(x, y) != IColumns.BACKGROUND_COLOR) {
                    g.setColor(Color.BLACK);
                    g.drawRect(x * IColumns.CELLSIZE, y * IColumns.CELLSIZE,
                            IColumns.CELLSIZE - 1, IColumns.CELLSIZE-1);
                }
            }
        }

        for (ColumnCell cell : board.getMatchedCells()) {
            int x = cell.getX();
            int y = cell.getY();

            Graphics2D g2 = (Graphics2D) g;
            Stroke defaultStroke = g2.getStroke();
            float[] dash = new float[] {3, 6};
            BasicStroke pen = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1, dash, 0);
            g2.setStroke(pen);
            g.setColor(IColumns.COLORS[IColumns.MATCH_COLOR]);

            g.drawRect(x * IColumns.CELLSIZE, y * IColumns.CELLSIZE,
                    IColumns.CELLSIZE-2, IColumns.CELLSIZE-2);

            g2.setStroke(defaultStroke);
        }
    }

    public void drawBlock(Graphics g, ColumnsBlock block) {
        if (!block.isVisible()) return;

        for (int i=0; i < block.BLOCKHEIGHT; i++) {
            g.setColor(IColumns.COLORS[block.getColor(i)]);
            g.fillRect(block.getX() * IColumns.CELLSIZE, block.getY() * IColumns.CELLSIZE + IColumns.CELLSIZE * i,
                    IColumns.CELLSIZE, IColumns.CELLSIZE);
            g.setColor(Color.BLACK);
            g.drawRect(block.getX() * IColumns.CELLSIZE, block.getY() * IColumns.CELLSIZE + IColumns.CELLSIZE * i,
                    IColumns.CELLSIZE-1, IColumns.CELLSIZE-1);
        }

    }
}
