import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class GameSquare here.
 * 
 * @author Victor Tarnovski 
 * @version v2.0 05.04.2020
 */
public class GameSquare extends Actor
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
    
    //Define a debugging variable (See video linked in assignment outline)
    private final boolean debug = false;
    
    //Instance Variables    
    private int value;
	
	//cell size
	private static final int CellSize = 50;
	
	//board size
	private static final int BoardSize = 200;
       
    //Constructor
    public GameSquare()
    {
        this(2);
    }
    
    public GameSquare(int valueIn)
    {
        setValue(valueIn);
        displayValue();
    }
    
    /**
     * Tell the block to move in the given direction until it hits an obstacle
     * 
     * @param The direction in which the block is to move (UP = 0; RIGHT = 1; DOWN = 2; LEFT = 3;
     */
    public void move(int direction) 
    {
		int x;
		int y;
		
        //check if can move
        int movable = canMove(direction);
        //System.out.println("Movable: " + Integer.toString(movable));

        //if moveable, start a loop
        while (movable > 0)
        {
            //Get current coordinates
            x = getX();
			y = getY();
                        
            //Change x and y values to the "new" location based on direction
            switch (direction) {
				case UP:
					y--;
				break;
				case DOWN:
					y++;
				break;
				case RIGHT:
					x++;
				break;
				case LEFT:
					x--; 
				break;
			}

            //If Nothing in the way - move the block
            if (movable == 1) { 		
				setLocation(x, y);
                movable = canMove(direction);
            }
            //Merge the blocks
            else {
                //Find which block we need to merge with
				GameBoard Board = (GameBoard)getWorld();	
				List blockList = Board.getObjectsAt(x, y, GameSquare.class);
                            
				if (blockList.size() == 1) { //Error checking
                    GameSquare BlockToMergeWith = ((GameSquare)(blockList.get(0)));  
				
					//Tell the other block object we wish to merge with it.  If successful, delete this block from the game
					BlockToMergeWith.merge(movable);
					
					if (debug) {
						System.out.println("Value: " + Integer.toString(BlockToMergeWith.getValue()));					
					}
					
					BlockToMergeWith.displayValue();
					Board.removeObject(this);
				}
                return;
            }
        }
        
        //can't move, so don't move.
        return;
    }

    /**
     * Sets the value of the game square to the value of the input parameter.
     * Will only work if the value is a factor of 2
     * 
     * @param The number to use as the new value
     * @return If the number was set correctly return true, otherwise return false
     */
    
    public boolean setValue(int valueIn)
    {
		if (valueIn % 2 == 0){
			value = valueIn;
			return true;
		}
		else {
			return false;
		}
    }
    
    /**
     * Merge with another block and combine values.
     * Will only work if the two blocks are of the same value
     * 
     * @param The value of the block to be added
     * 
     * @return Return true if the merge is successful.
     */
    public boolean merge(int valueIn)
    {
		if (getValue() == valueIn){
			setValue(2 * valueIn);
			return true;
		} 
		else {
			return false;
		} 
    }
	
    /**
     * Returns the current value of the gameSquare
     * 
     * @return The current value (int) of the game square
     */
    
    public int getValue()
    {
        return value;
    }

    /**
     * Checks to see if the block can move
     * 
      * @param The direction in which the block is to move (UP = 0; RIGHT = 1; DOWN = 2; LEFT = 3;

     
     * @return int value representing what is in the space to be moved to.  0 -> Path Blocked, 1 -> Empty Space, int>1 value of block in the space to be moved to.
     */
    public int canMove(int direction)
    {
        int Result = 0;
        
        //Get World
        GameBoard Board = (GameBoard)getWorld();
        
        //Get x and y values of current object  
        int x = getX();
        int y = getY();
        
        //Change x and y values to the "new" location based on direction
		switch (direction) {
			case UP:
				y--;
			break;
			case DOWN:
				y++;
			break;
			case RIGHT:
				x++;
			break;
			case LEFT:
				x--;
			break;
		}
			
        //Test for outside border
        if (y >= 0 && x >= 0 && y < Board.getHeight() && x < Board.getWidth()){
            //Check to see if there is a block in the way
            List blockList = Board.getObjectsAt(x, y, GameSquare.class);
            
             if (blockList.size() == 0) {
                Result = 1; 
             }
             else {
                if (((GameSquare)(blockList.get(0))).getValue() == this.getValue()) {
                    Result = this.getValue();
                }
             }
        }

		return Result;	
    }
    
    /**
     * displayValue - Displays the current value of a block in an image, then sets that image to the block display image
     */    
    private void displayValue() 
    {
		GreenfootImage img = new GreenfootImage ("Tile" + Integer.toString(value) + ".png");
		img.scale(CellSize, CellSize);
		setImage(img);
		
		/*
        //Create an image consisting of the display value using built in greenfoot commands
        GreenfootImage displayImage;
        displayImage = new GreenfootImage( Integer.toString(value), 20, Color.BLACK, Color.WHITE);
        
        //Add the image as the new image for this object
        setImage(displayImage);
		*/
        
    }

}
