import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;;

/**
 * This is a simple game where you join together boxes of the same value and try to get to 2048
 * 
 * This assignment is to get you familar with workin in an OO environment and 
 * to get you used to accessing and using java documentation.  Below are two
 * useful links that will help you.
 * 
 * Greenfoot Javadoc:  https://www.greenfoot.org/files/javadoc/
 * Java 8 List Javadoc : https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/List.html
 * 
 * I also talk about access control in the video and you might find this page
 * interesting to check out if you want to know more about different java
 * class types : https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html
 * 
 * Extra Videos: be sure to check out the extra videos I will be releasing later this week on the following topics:
 * Overloading of constructors (this and super)
 * Incompatible types error discussion
 * Controlling debugging with boolean and print statements
 * Placement of blocks - simple vs efficient (cut from Kat 17min video)
 * 
 * @author Victor Tarnovski
 * @version v2.0 05.04.2020
 */
public class GameBoard extends World
{
    //Instance Constants
    
    /* Note: 
     * Since these variables are definied at STATIC they will never change (aka are constant).
     * Normally when we have constant variables like this we name them in ALL CAPS
    */
    //Define some directions to make it easier to reference which way the blocks are moving
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
	
	//number of blocks at the beginning of the game
    private static final int InitialNumberOfBlocks = 2;
	
	//number of squares per line
	private static final int SquaresPerLine = 4;
	
	//cell size
	private static final int CellSize = 50;
	
	//board size
	private static final int BoardSize = 200;
	  
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GameBoard()
    {    
        // Create a new world with 4x4 cells with a cell size of 50 pixels.
        super(SquaresPerLine, SquaresPerLine, CellSize, true); 
		
		GreenfootImage image = new GreenfootImage("Board.png");
		image.scale(BoardSize, BoardSize);
		setBackground(image);

        
        //populate gameboard with x randomly placed objects
		for (int i = 0; i < InitialNumberOfBlocks; i++) {	
			placeRandomBlock();
		}
    }
    
    /**
     * Place a block on a random location on the board
     * 
     * @return Returns true if successful, false if not successful
     */
    private boolean placeRandomBlock()
    {
		//empty cells will be numbered 0, 1, 2, 3, ...		
		int CurrentCell = 0;
		
		if (NumberOfOpenSpaces() > 0) {
			//gets a random cell position among all open cells
			int CellPosition = ThreadLocalRandom.current().nextInt(0, NumberOfOpenSpaces());
			//System.out.println("Cell Position: " + Integer.toString(CellPosition));  
			
			while (CurrentCell <= CellPosition) {
				for (int x = 0; x < getWidth(); x++) {
					for (int y = 0; y < getHeight(); y++) {
						//System.out.println("X: " + Integer.toString(x) + "Y: " + Integer.toString(y));
						//Get a list containing all of the GameSquare objects at position (x, y)
						List blockList = getObjectsAt(x, y, GameSquare.class);
				
						//check if the cell is empty
						if (blockList.size() == 0) {
							if (CurrentCell == CellPosition) {
								GameSquare Square = new GameSquare(2);
								addObject (Square, x, y);
							}
							
							CurrentCell++;
						}
					}
				}
				
			}
			return true;
		}
		else
		{
			return false;
		}
    }
    
    /**
     * Act - Check for key presses and tell each block to move itsself.
     */
    public void act() 
    {
        //Add key press actoins here
        String key = Greenfoot.getKey();
        
        //If a key was pressed...do something
        if (key != null) {
            
            //Note: you should disable this, but I wanted to show how you can debug in greenfoot
            //System.out.println(key);  
            
            switch(key) {
                case "up": 
                    //Tell the blocks to move up
                    //Start checking from the top, then move downwards
                    for (int x = 0; x < getWidth(); x++){
                        for (int y = 0; y < getHeight(); y++){
                            //Get a list containing all of the GameSquare objects at position (i,j)
                            List blockList = getObjectsAt(x, y, GameSquare.class);
            
                            //Tell the other block object we wish to merge with it.  If successful, delete this block from the game
                            if (blockList.size() == 1) { //Error checking
                               /* //Create a temporary holding space for a generic object
                                Object tempObject;
                                //Get the first (and only) entry in the list
                                tempObject = blockList.get(0);
                                //Create a temporary holding space for the gameSquare object
                                GameSquare tempSquare;
                                //Convert it from the generic "Object" to a GameSquare Object
                                tempSquare = (GameSquare)tempObject;  //Error Discussion: Incompatible Types - See video on this
                                //Then move UP.  
                                tempSquare.move(UP);
								*/
                                
                                //The above few lines of code is NOT how I would normally write this.
                                //You could accomplish all of the ablove using the single line of below
                                //It can be a bit confusing when code is all in one line.  Is this considered good form or bad form?
                                ((GameSquare)(blockList.get(0))).move(UP);  
                            }
                        }
                    }
                    break;
                //NOTE: The remaining cases are similar to the one above, but not exactly the same
                case "right":
                    //Tell the blocks to move right 
                    //Start checking from the right most col, then move left                    
					for (int y = 0; y < getHeight(); y++){
						for (int x = getWidth() - 1; x >= 0; x--){
							List blockList = getObjectsAt (x, y, GameSquare.class);
							
							if (blockList.size() == 1){
								((GameSquare)(blockList.get(0))).move(RIGHT);
							}
						}
					}
                    break;
                case "down":	
                    //Tell the blocks to move down
                    //Start checking from the bottom, then move up		
					for (int x = 0; x < getWidth(); x++){
						for (int y = getHeight() - 1; y >= 0; y--){
							List blockList = getObjectsAt (x, y, GameSquare.class);
							
							if (blockList.size() == 1){
								((GameSquare)(blockList.get(0))).move(DOWN);
							}	
						}
					}
                    break;
                case "left":
                    //Tell the blocks to move left
                    //Start checking from the left, then move right
					for (int y = 0; y < getHeight(); y++){
						for (int x = 0; x < getWidth(); x++){
							List blockList = getObjectsAt (x, y, GameSquare.class);
							
							if (blockList.size() == 1){
								((GameSquare)(blockList.get(0))).move(LEFT);
							}
						}
					}
                    break;

			}

			placeRandomBlock();
			CheckForWin();
			CheckForLoss();
		}

    }

    /**
     * Calculates the number of open spaces
     * 
     * @return Returns the number of open spaces
     */
	private int NumberOfOpenSpaces()
	{
		int Result = 0;
		
		for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                //Get a list containing all of the GameSquare objects at position (x, y)
                List blockList = getObjectsAt(x, y, GameSquare.class);
            
                //check if the cell is empty
                if (blockList.size() == 0) {
					Result++;
				}
			}
		}
		
		return Result;
	}
	
	private void CheckForWin (){
		boolean Win = false;
		
		for (int x = 0; x < getWidth(); x++){
			for (int y = 0; y < getHeight(); y++){
				List blockList = getObjectsAt (x, y, GameSquare.class);
				
				if (blockList.size() == 1){									
					if (((GameSquare)blockList.get(0)).getValue() == 2048){
						Win = true;
					}
				} 		
			}	
		}
		
		if (Win){
			Greenfoot.stop();
			GreenfootImage winImg = new GreenfootImage("Win.jpg");
			winImg.scale(BoardSize, BoardSize);
			setBackground (winImg);
			
			for (int x = 0; x < getWidth(); x++){
				for (int y = 0; y < getHeight(); y++){
					List blockList = getObjectsAt (x, y, GameSquare.class);
					
					if (blockList.size() == 1){									
						removeObjects( getObjectsAt (x, y, GameSquare.class));
					} 		
				}	
			}	
		}
	}
	
	private void CheckForLoss (){
		if (NumberOfOpenSpaces() == 0){
			boolean LossCondition = true;
			
			for (int x = 0; x < getWidth(); x++){
				for (int y = 0; y < getHeight(); y++){
					List blockList = getObjectsAt (x, y, GameSquare.class);
					
					if (blockList.size() == 1){
						/*System.out.println("X: " + Integer.toString(x) 
						+ "Y: " + Integer.toString(y)
						+ "UP: " + Integer.toString(((GameSquare)(blockList.get(0))).canMove(UP))
						+ "DOWN: " + Integer.toString(((GameSquare)(blockList.get(0))).canMove(DOWN))
						+ "LEFT: " + Integer.toString(((GameSquare)(blockList.get(0))).canMove(LEFT))
						+ "RIGHT: " + Integer.toString(((GameSquare)(blockList.get(0))).canMove(RIGHT))
						);*/
						
						if (((GameSquare)(blockList.get(0))).canMove(UP) +
							((GameSquare)(blockList.get(0))).canMove(DOWN) +
							((GameSquare)(blockList.get(0))).canMove(LEFT) +
							((GameSquare)(blockList.get(0))).canMove(RIGHT) > 0)
							{
								LossCondition = false;
							}
					} 		
				}	
			}
			
			//System.out.println("Loss Condition: " + Boolean.toString(LossCondition));
			
			if (LossCondition){	
				Greenfoot.stop();
				
				GreenfootImage lossImg = new GreenfootImage("GameOver.png");
				lossImg.scale(BoardSize, BoardSize);
				setBackground(lossImg);
				
				for (int x = 0; x < getWidth(); x++){
					for (int y = 0; y < getHeight(); y++){
						List blockList = getObjectsAt (x, y, GameSquare.class);
						
						if (blockList.size() == 1){									
							removeObjects( getObjectsAt (x, y, GameSquare.class));
						} 		
					}	
				}		
			}
		}
	}
}


