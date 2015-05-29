import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
        
        if (x == 0 && y == 0) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell right = cells[x][y+1];
        		Cell botRight = cells[x+1][y+1];
        		Cell bot = cells[x+1][y];
        		
        		cellCheck.add(right); cellCheck.add(botRight); cellCheck.add(bot);
      
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        			count++;
        	  	}
        		
        		return count;
        }
        
        else if (x == 0 && y == 15) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell left = cells[x][y-1];
        		Cell botLeft = cells[x+1][y-1];
        		Cell bot = cells[x+1][y];
        		
        		cellCheck.add(left); cellCheck.add(botLeft); cellCheck.add(bot);
        		
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        				count++;	
        		}
        		
        		return count;
        } 
        
        else if (x == 15 && y == 0) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell right = cells[x][y+1];
        		Cell topRight = cells[x-1][y+1];
        		Cell top = cells[x-1][y];
        		
        		cellCheck.add(right); cellCheck.add(topRight); cellCheck.add(top);
      
        		for (int c = 0; c < cellCheck.size(); c++) 
        			if (cellCheck.get(c).isMine()) {
        				count++;
        		}
        		
        		return count;
        } 
        
        else if (x == 15 && y == 15) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell left = cells[x][y-1];
        		Cell topLeft = cells[x-1][y-1];
        		Cell top = cells[x-1][y];
       	
        		cellCheck.add(left); cellCheck.add(topLeft); cellCheck.add(top);
      
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        				count++;
        		}
        		
        		return count;
        } 
        
        else if (x == 0) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell right = cells[x][y+1];
        		Cell left = cells[x][y-1];
        		Cell bot = cells[x+1][y];
        		Cell botRight = cells[x+1][y+1];
        		Cell botLeft = cells[x+1][y-1];
        		
        		cellCheck.add(right); cellCheck.add(left); cellCheck.add(bot); cellCheck.add(botRight); cellCheck.add(botLeft);
       
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        				count++;	
        		}
        		
        		return count;
        } 
        
        else if (x == 15) {
	        	ArrayList<Cell> cellCheck = new ArrayList<Cell>();
	        	Cell right = cells[x][y+1];
	        	Cell left = cells[x][y-1];
	        	Cell top = cells[x-1][y];
	        	Cell topRight = cells[x-1][y+1];
	        	Cell topLeft = cells[x-1][y-1];
	        	
	        	cellCheck.add(right); cellCheck.add(left); cellCheck.add(top); cellCheck.add(topRight); cellCheck.add(topLeft);
       
	        	for (int c = 0; c < cellCheck.size(); c++) {
	        		if (cellCheck.get(c).isMine()) 
	        			count++;
	        	}
	        	
	        	return count;
        } 
        
        else if (y == 0) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell top = cells[x-1][y];
        		Cell topRight = cells[x-1][y+1];
        		Cell right = cells[x][y+1];
        		Cell botRight = cells[x+1][y+1];
        		Cell bot = cells[x+1][y];
        	
        		cellCheck.add(top); cellCheck.add(topRight); cellCheck.add(right); cellCheck.add(botRight); cellCheck.add(bot);
        	
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        				count++;
        		}
        	
        	return count;
        }
        
        else if (y == 15) {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell top = cells[x-1][y];
        		Cell topLeft = cells[x-1][y-1];
        		Cell left = cells[x][y-1];
        		Cell botLeft = cells[x+1][y-1];
        		Cell bot = cells[x+1][y];
        	
        		cellCheck.add(top); cellCheck.add(topLeft); cellCheck.add(left); cellCheck.add(botLeft); cellCheck.add(bot);
        	
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        				count++;
        		}
        	
        	return count;
        }
        
        else {
        		ArrayList<Cell> cellCheck = new ArrayList<Cell>();
        		Cell top = cells[x-1][y];
        		Cell topRight = cells[x-1][y+1];
        		Cell right = cells[x][y+1];
        		Cell botRight = cells[x+1][y+1];
        		Cell bot = cells[x+1][y];
        		Cell botLeft = cells[x+1][y-1];
        		Cell left = cells[x][y-1];
        		Cell topLeft = cells[x-1][y-1];
        	
        		cellCheck.add(top); cellCheck.add(topRight); cellCheck.add(right); cellCheck.add(botRight); cellCheck.add(bot); cellCheck.add(botLeft); cellCheck.add(left); cellCheck.add(topLeft);
        	
        		for (int c = 0; c < cellCheck.size(); c++) {
        			if (cellCheck.get(c).isMine()) 
        				count++;
        		}
        	
        	return count;
        } 
        
//        Gameplan: (Will be divided into steps - check in with me before you move to next step)
//           1. Add a counter for the corner cells 
//     		 2. Add a counter for the border cells 
//           3. Add a counter for the rest of the cells 
        
//           - Check all 8 of the cells around the current cell 
//           - If a cell has a mine in it, add 1 to the counter
//             (Check the Mines class to see the relevant methods)
//           - Need to account for corner cells because each corner cell will
//              only has 3 cells surrounding it instead of 9
//           - Need to account for border cells because each border cell will
//              only has 5 cells surrounding it instead of 9
//            - Return the count

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
        
        if (inGame)	{
        	
        		if (!cell.isCovered() && cell.getValue() < 1)
        			return 0;//display pressed image
        	
        		else if (cell.isCovered())
        			return 10;//display covered image

        		else if (!cell.isCovered() && cell.getValue() != 0)
        			return cell.getValue();//display correct value
        	 
        }
        
        else if (!inGame)	{
      
        		if (cell.isMine())
        			return 9;
        	
        		cell.uncover(); 
        	}

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
    		
    		if (x > 0 && x < 15 && y > 0 && y < 15) {
    			Cell top = cells[x-1][y];
  	 		Cell topLeft = cells[x-1][y-1];
  	 		Cell topRight = cells[x-1][y+1];
  	 		Cell right = cells[x][y+1];
  	 		Cell left = cells[x][y-1];
  	 		Cell bot = cells[x+1][y];
  	 		Cell bottomRight = cells[x+1][y+1];
  	 		Cell bottomLeft = cells[x+1][y-1];
  	 		
  	 		top.uncover();
  	 		topLeft.uncover();
  	 		topRight.uncover();
  	 		right.uncover();
  	 		left.uncover();
  	 		bot.uncover();
  	 		bottomRight.uncover();
  	 		bottomLeft.uncover();   
    		}
    			   
    		
    		
       
     	
     	//JOptionPane.showMessageDialog(null,"Coordinate = " + x + ", " + y + " Val = " + first.getValue(),"TITLE",JOptionPane.WARNING_MESSAGE);
     	
     	//Cell one = cells[x + 1][y];
     	//JOptionPane.showMessageDialog(null,"Clicked on = " + x + ", " + y + " Val = " + first.getValue(),"TITLE",JOptionPane.WARNING_MESSAGE);
     	//JOptionPane.showMessageDialog(null,"Right Coordinate = " + (x + 1) + ", " + y + " Val = " + first.getValue(),"TITLE",JOptionPane.WARNING_MESSAGE);
     ///	if (one.getValue() > 0)
     	//	JOptionPane.showMessageDialog(null, "Uncover", "TITLE",JOptionPane.WARNING_MESSAGE);
     	
     	
     	
     	/*
     	if (first.getValue() == 0) {
     		Cell one = cells[x + 1][y];
     		one.uncover();
     		Cell two = cells[x - 1][y];
     		two.uncover();
     		Cell three = cells[x][y + 1];
     		three.uncover();
     		Cell four = cells[x][y - 1];
     		four.uncover();
     		Cell five = cells[x + 1][y + 1];
     		five.uncover();
     		Cell six = cells[x + 1][y - 1];
     		six.uncover();
     		Cell seven = cells[x - 1][y + 1];
     		seven.uncover();
     		Cell eight = cells[x - 1][y - 1];
     		eight.uncover();
     	} */
     	/*
     	if (first.getValue() >= 1) {
     		first.uncover(); 
     		uncoverAroundCell(x + 1, y);
     		uncoverAroundCell(x - 1, y);
     		uncoverAroundCell(x, y + 1);
     		uncoverAroundCell(x, y - 1);
     		uncoverAroundCell(x - 1, y + 1);
     		uncoverAroundCell(x - 1, y - 1);
     		uncoverAroundCell(x + 1, y + 1);
     		uncoverAroundCell(x + 1, y - 1); 
     	}*/
     	
     		
     	
    	
    	/*
    	Cell first = cells[x][y];
    	if (first.getValue() > 0) {
    		first.uncover();
    	}
    	if (first.getValue() == 0) {
    		first.uncover();
    		if (x == 0 && y == 0) { // top left corner
    	    	Cell  right = cells[x][y+1];
    				if (!right.isMine()) {
    					right.uncover();
    					uncoverAroundCell((x),(y+1));
    				}
    			Cell bot = cells[x+1][y];
    				if (!bot.isMine()) {
    					bot.uncover();
    					uncoverAroundCell((x+1),(y));
    				}
    			Cell bottomRight = cells[x+1][y+1];
    				if (!bottomRight.isMine()) {
    					bottomRight.uncover();
    					uncoverAroundCell((x+1),(y+1));
    				}
    	    	}
    	    	else if (x == 15 && y == 0) { // bottom left corner
    	  	    Cell top = cells[x-1][y];
    	    		if (!top.isMine()) {
    	    			top.uncover();
    	    			uncoverAroundCell((x-1),(y));
    	    		}
    	    	Cell topRight = cells[x-1][y+1];
    				if (!topRight.isMine()) {
    					topRight.uncover();
    					uncoverAroundCell((x-1),(y+1));
    				}
    			Cell right = cells[x][y+1];
    				if (!right.isMine()) {
    					right.uncover();
    					uncoverAroundCell((x),(y+1));
    				}
       	   	    }
    	   	    else if (x == 0 && y == 15) { // top left corner
    	    	Cell left = cells[x-1][y];
    	     		if (!left.isMine()) {
    	      			left.uncover();
    	      			uncoverAroundCell((x-1),(y));
    	      		}
    	     	Cell botLeft = cells[x-1][y-1];
    	 			if (!botLeft.isMine()) {
    	 				botLeft.uncover();
    	 				uncoverAroundCell((x-1),(y-1));
    	 			}
    	 		Cell bot = cells[x][y-1];
    	 			if (!bot.isMine()) {
    	 				bot.uncover();
    	 				uncoverAroundCell((x),(y-1));
    	 			}
    	        }
    	   	    else if (x == 15 && y == 15) { // bot right corner
    	   	    Cell topLeft = cells[x-1][y-1];
    				if (!topLeft.isMine()) {
    					topLeft.uncover();
    					uncoverAroundCell((x-1),(y-1));
    				}
     			Cell left = cells[x][y-1];
    				if (!left.isMine()) {
    					left.uncover();
    					uncoverAroundCell((x),(y-1));
    				}
    			Cell top = cells[x-1][y];
    		   		if (!top.isMine()) {
    		   			top.uncover();
    		   			uncoverAroundCell((x-1),(y));
    		   		}
    	   	    }
    	   	    else if (x == 0) { // top row
    	   	    Cell right = cells[x][y+1];
    				if (!right.isMine()) {
    					right.uncover();
    					uncoverAroundCell((x),(y+1));
    				}
    			Cell left = cells[x][y-1];
    				if (!left.isMine()) {
    					left.uncover();
    					uncoverAroundCell((x),(y-1));
    				}
    			Cell bot = cells[x+1][y];
    				if (!bot.isMine()) {
    					bot.uncover();
    					uncoverAroundCell((x+1),(y));
    				}
    			Cell bottomRight = cells[x+1][y+1];
    				if (!bottomRight.isMine()) {
    					bottomRight.uncover();
    					uncoverAroundCell((x+1),(y+1));
    				}
    			Cell bottomLeft = cells[x+1][y-1];
    				if (!bottomLeft.isMine()) {
    					bottomLeft.uncover();
    					uncoverAroundCell((x+1),(y-1));
    				}
    	    	}
    	        else if (x == 15) { // bottom row
    	   	    Cell top = cells[x-1][y];
    	     		if (!top.isMine()) {
    	     			top.uncover();
    	     			uncoverAroundCell((x-1),(y));
    	     		}
    	 		Cell topLeft = cells[x-1][y-1];
    	 			if (!topLeft.isMine()) {
    	 				topLeft.uncover();
    	 				uncoverAroundCell((x-1),(y-1));
    	 			}
    	 		Cell topRight = cells[x-1][y+1];
    	 			if (!topRight.isMine()) {
    	 				topRight.uncover();
    	 				uncoverAroundCell((x-1),(y+1));
    	 			}
    	 		Cell right = cells[x][y+1];
    				if (!right.isMine()) {
    					right.uncover();
    					uncoverAroundCell((x),(y+1));
    				}
    			Cell left = cells[x][y-1];
    				if (!left.isMine()) {
    					left.uncover();
    					uncoverAroundCell((x),(y-1));
    				}
    	   	    }
    	   	    else if (y == 0) { // left column
    	    	Cell top = cells[x-1][y];
    	     		if (!top.isMine()) {
    	     			top.uncover();
    	     			uncoverAroundCell((x-1),(y));
    	     		}
    	   		Cell topRight = cells[x-1][y+1];
    	 			if (!topRight.isMine()) {
    	 				topRight.uncover();
    	 				uncoverAroundCell((x-1),(y+1));
    	 			}
    	 		Cell right = cells[x][y+1];
    				if (!right.isMine()) {
    					right.uncover();
    					uncoverAroundCell((x),(y+1));
    				}
    	    	Cell bot = cells[x+1][y];
    				if (!bot.isMine()) {
    					bot.uncover();
    					uncoverAroundCell((x+1),(y));
    				}
    			Cell bottomRight = cells[x+1][y+1];
    				if (!bottomRight.isMine()) {
    					bottomRight.uncover();
    					uncoverAroundCell((x+1),(y+1));
    				}
    	   	    }
    	   	    else if (y == 15) {
    	   	 	Cell top = cells[x-1][y];
    	     		if (!top.isMine()) {
    	     			top.uncover();
    	     			uncoverAroundCell((x-1),(y));
    	     		}
    	 		Cell topLeft = cells[x-1][y-1];
    	 			if (!topLeft.isMine()) {
    	 				topLeft.uncover();
    	 				uncoverAroundCell((x-1),(y-1));
    	 			}
    	 		Cell left = cells[x][y-1];
    				if (!left.isMine()) {
    					left.uncover();
    					uncoverAroundCell((x),(y-1));
    				}
    	 		Cell bot = cells[x+1][y];
    				if (!bot.isMine()) {
    					bot.uncover();
    					uncoverAroundCell((x+1),(y));
    				}
    			Cell bottomLeft = cells[x+1][y-1];
    				if (!bottomLeft.isMine()) {
    					bottomLeft.uncover();
    					uncoverAroundCell((x+1),(y-1));
    				}
    	        }
    	   	    else { // Any cell that is not on the outer rows or columns of the grid
    	   	    Cell top = cells[x-1][y];
    	     		if (!top.isMine()) {
    	     			top.uncover();
    	     			uncoverAroundCell((x-1),(y));
    	     		}
    	 		Cell topLeft = cells[x-1][y-1];
    	 			if (!topLeft.isMine()) {
    	 				topLeft.uncover();
    	 				uncoverAroundCell((x-1),(y-1));
    	 			}
    	 		Cell topRight = cells[x-1][y+1];
    	 			if (!topRight.isMine()) {
    	 				topRight.uncover();
    	 				uncoverAroundCell((x-1),(y+1));
    	 			}
    	 		Cell right = cells[x][y+1];
    	 			if (!right.isMine()) {
    	 				right.uncover();
    	 				uncoverAroundCell((x),(y+1));
    	 			}
    	 		Cell left = cells[x][y-1];
    	 			if (!left.isMine()) {
    	 				left.uncover();
    	 				uncoverAroundCell((x),(y-1));
    	 			}
    	 		Cell bot = cells[x+1][y];
    	 			if (!bot.isMine()) {
    	 				bot.uncover();
    	 				uncoverAroundCell((x+1),(y));
    	 			}
    	 		Cell bottomRight = cells[x+1][y+1];
    	 			if (!bottomRight.isMine()) {
    	 				bottomRight.uncover();
    	 				uncoverAroundCell((x+1),(y+1));
    	 			}
    	 		Cell bottomLeft = cells[x+1][y-1];
    	 			if (!bottomLeft.isMine()) {
    	 				bottomLeft.uncover();
    	 				uncoverAroundCell((x+1),(y-1));
    	 			}
    	   	    	}	
    	}

//    	Gameplan:
//    		- Method initially checks the value of the 8 cells around the current cell 
//    				- If the cell has a value of 0, uncover the cell and recursively call uncoverAroundCell on that cell
//    				- If the cell has a value greater than 0, uncover the cell 
//					- If the cell has a mine, keep it covered */
    	
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
