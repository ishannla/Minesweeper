import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;

public class Board extends JPanel {
    // Constants
	
    private final int CELL_SIZE  = 15;
    private final int NUM_IMAGES = 13;

    private final int IMAGE_MINE       = 9;
    private final int IMAGE_COVER      = 10;
    private final int IMAGE_MARK       = 11;
    private final int IMAGE_WRONG_MARK = 12;

    private JLabel statusBar;

    private int totalMines = 40;
    private int remainderMines;

    private int rows = 16, columns = 16;

    private Cell[][] cells;
    private Image[] img;

    private boolean inGame;

    public Board(JLabel statusBar) {
        this.statusBar = statusBar;
        this.img = new Image[NUM_IMAGES];
        
        for (int i = 0; i < NUM_IMAGES; i++) {
            String path = "img/j" + i + ".gif";
            img[i] = new ImageIcon(path).getImage();
        }

        this.setDoubleBuffered(true);
        this.addMouseListener(new MinesAdapter());
        this.newGame();
    }
    
    public void newGame () {
        Random random;

        random = new Random();

        this.inGame = true;
        this.remainderMines = totalMines;

        this.initCells();
        this.statusBar.setText(Integer.toString(this.remainderMines));

        int remainder = totalMines;
        while (remainder >= 0) {
            int randX = random.nextInt(this.rows);
            int randY = random.nextInt(this.columns);

            Cell cell = this.cells[randX][randY];
            if (!cell.isMine()) {
                cell.setMine(true);
                remainder--;
            }
        }

        this.setMineCounts();
    }

    private void initCells () {
        this.cells = new Cell[rows][columns];

        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.columns; ++j) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    

    private void setMineCounts() {

        for (int i = 0; i < this.columns; ++i) {
            for (int j = 0; j < this.rows; ++j) {
                Cell cell = this.cells[i][j];

                if (!cell.isMine()) {
                    int count = countMinesAround(i, j);
                    cell.setAroundMines(count);
                }
            }
        }
    }

    /***
     * 
     * @param x x coordinate in the grid
     * @param y y coordinate in the grid
     * @return number of mines around this position
     * 
     * You should explore nearby cells and return an accurate count of
     * nearby mines.
     */
    private int countMinesAround(int x, int y) {
        int count = 0;
        
        /* YOUR CODE GOES HERE! */ 
        
//        Gameplan:
//           - Need to initialize a counter variable
//           - Check all 8 of the cells around the current cell 
//           - If a cell has a mine in it, add 1 to the counter
//             (Check the Mines class to see the relevant methods)
//           - Need to account for corner cells because each corner cell will
//              only has 3 cells surrounding it instead of 9
//           - Need to account for border cells because each border cell will
//              only has 5 cells surrounding it instead of 9
//            - Return the count

        return count;
    }

    public void paint(Graphics g) {
        int coveredCells = 0;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                Cell cell = this.cells[i][j];
                int imageType;
                int xPosition, yPosition;

                if (cell.isCovered()) {
                    coveredCells++;
                }

                if (inGame) {
                    if (cell.isMine() && !cell.isCovered()) {
                        inGame = false;
                    }
                }

                imageType = this.decideImageType(cell);

                xPosition = (j * CELL_SIZE);
                yPosition = (i * CELL_SIZE);

                g.drawImage(img[imageType], xPosition, yPosition, this);
            }
        }

        if (coveredCells == 0 && inGame) {
            inGame = false;
            statusBar.setText("Game Won");
        } else if (!inGame) {
            statusBar.setText("Game Lost");
        }
    }

    /***
     * 
     * @param cell
     * @return IMAGE_MINE, IMAGE_COVER, IMAGE_MARK, or IMAGE_WRONG_MARK (integer).
     * You should make decision on which image type to display while the game is in
     * active (see the "inGame" variable), and if we are not in a game, you should
     * uncover the cell and reveal its contents based on whether the cell is a mine,
     * is marked, or is marked incorrectly.
     */
    private int decideImageType(Cell cell) {
        int imageType = cell.getValue();

        /* YOUR CODE GOES HERE! */
        
//    	Gameplan: 
//		- There are 13 Images (0 = Pressed, 1 - 8 = Values, 9 = Mine, 10 = Covered, 11 = Marked Correctly, 12 = Marked Incorrectly )
//      - Need to have separate if statements based on whether InGame is true or false
//      - If InGame is true
//        			- If the cell is covered, display covered image
//        			- If the cell is uncovered and has a value of 0, display pressed image
//        			- If the cell is uncovered and has a vale, display the correct value
//        			- If the cell is uncovered and has a mine, display the mine
//        			- If the cell is marked correctly, display the marked correctly image
//        			- If the cell is marked incorrectly, display the marked incorrectly image
//       			 (Reference the methods in the Cell class)
//      - If InGame is false 
//        			- Display all of the mines 
//        			- If the cell has a mine, display it no matter what 

// 		lol tbh I don't understand this method 100% I will ask them more on Monday
        
        return imageType;
    }

    public void findEmptyCells(int x, int y, int depth) {

        for (int i = -1; i <= 1; ++i) {
            int xIndex = x + i;

            if (xIndex < 0 || xIndex >= this.rows) {
                continue;
            }

            for (int j = -1; j <= 1; ++j) {
                int yIndex = y + j;

                if (yIndex < 0 || yIndex >= this.columns) {
                    continue;
                }

                if (!(i == 0 || j == 0)) {
                    continue;
                }

                Cell cell = this.cells[xIndex][yIndex];
                if (checkEmpty(cell)) {
                    cell.uncover();
                    cell.checked();

                    uncoverAroundCell(xIndex, yIndex);
                    findEmptyCells(xIndex, yIndex, depth + 1);
                }
            }
        }

        if (depth == 0) {
            this.clearAllCells();
        }
    }

    /***
     * 
     * @param x x coordinate in the grid
     * @param y y coordinate in the grid
     * 
     * This method is used to uncover nearby non-empty cells.
     */
    private void uncoverAroundCell(int x, int y) {
    	
    	/* YOUR CODE GOES HERE! */
    	ArrayList<Cell> cellList = new ArrayList<Cell>();
    	
    Cell first = (cells[x-1][y]);
    	if (first.getValue() == 0)
    		uncoverAroundCell((x-1),(y));
    	
    	
    
    	cellList.add(cells[x-1][y-1]);
    	cellList.add(cells[x-1][y+1]);
    	cellList.add(cells[x][y+1]);
    	cellList.add(cells[x][y-1]);
    	cellList.add(cells[x+1][y]);
    	cellList.add(cells[x+1][y+1]);
    	cellList.add(cells[x+1][y-1]);
    	for (int c = 0; c < cellList.size(); c++) {
    		if (cellList.get(c).getValue() == 0) {
    			cellList.get(c).uncover();
    			//uncoverAroundCell(cells.);
    		}
    		else if (cellList.get(c).getValue() > 0) {
    			cellList.get(c).uncover();
    		}
    		else {

    		}
    	}
//    	Gameplan:
//    		- Method initially checks the value of the 8 cells around the current cell 
//    				- If the cell has a value of 0, uncover the cell and recursively call uncoverAroundCell on that cell
//    				- If the cell has a value greater than 0, uncover the cell 
//					- If the cell has a mine, keep it covered
    	
    }

    private boolean checkEmpty(Cell cell) {
        if (!cell.isChecked()) {
            if (cell.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private void clearAllCells() {
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.columns; ++j) {
                this.cells[i][j].clearChecked();
            }
        }
    }

    class MinesAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int pressedCol = e.getX() / CELL_SIZE;
            int pressedRow = e.getY() / CELL_SIZE;

            boolean doRepaint = false;
            Cell pressedCell;

            if (!inGame) {
                newGame();
                repaint();
            }

            if ((pressedCol < 0 || pressedCol >= columns)
                || (pressedRow < 0 || pressedRow >= rows)) {
                return;
            }

            pressedCell = cells[pressedRow][pressedCol];

            if (e.getButton() == MouseEvent.BUTTON3) {
                doRepaint = true;

                if (!pressedCell.isCovered()) {
                    return;
                }

                String str;
                if (!pressedCell.isMarked()) {
                    pressedCell.setMark(true);
                    remainderMines--;
                } else {
                    pressedCell.setMark(false);
                    remainderMines++;
                }

                statusBar.setText(Integer.toString(remainderMines));
            } else {
                if (pressedCell.isMarked() || !pressedCell.isCovered()) {
                    return;
                }

                doRepaint = true;

                pressedCell.uncover();
                if (pressedCell.isMine()) {
                    inGame = false;
                } else if (pressedCell.isEmpty()) {
                    findEmptyCells(pressedRow, pressedCol, 0);
                }
            }

            if (doRepaint) {
                repaint();
            }
        }
    }
}
