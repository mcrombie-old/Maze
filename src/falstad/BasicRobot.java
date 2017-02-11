package falstad;

//lala
import generation.CardinalDirection;
import generation.Cells;

/*
 * BasicRobot class implements the given Robot interface. It creates a robot that can perform movement and rotation based on the methods 
 * built into MazeController, which it operates on. 
 * 
 * @author michaelCrombie
 */

public class BasicRobot implements Robot{
	
	protected MazeController mazeController;
	private float batteryLevel;

	public BasicRobot(MazeController mazeController){
		setMaze(mazeController);	
		
	}
	
	/**
	 * Initializes the mazeController
	 */
	public void setMaze(MazeController maze) {
		mazeController = maze;
		this.setBatteryLevel(2500); //sets the initial battery level
	}

	/**
	 * rotates the robot given a direction of left, right, or around
	 */
	public void rotate(Turn turn) {

		if (turn == Turn.LEFT){ //90 degree turn left
			mazeController.rotate(1);
			System.out.println("Distance to wall is " + this.distanceToObstacle(Direction.FORWARD));
		}else if (turn == Turn.RIGHT){ //90 degree turn right
			mazeController.rotate(-1);
			System.out.println("Distance to wall is " + this.distanceToObstacle(Direction.FORWARD));
		}else if (turn == Turn.AROUND){ //180 degree turn. left because it is trivial 
			mazeController.rotate(2);
		}
		
		System.out.println(this.getCurrentDirection());
	
		try {
			System.out.println(this.getCurrentPosition()[0] + " " + this.getCurrentPosition()[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (this.canSeeGoal(Direction.FORWARD)){
			System.out.println("You can see!");
		}	
	}

	/**
	 * moves the robot forward however many steps the distance paramter indicates
	 */
	public void move(int distance, boolean manual) {
		mazeController.walk(distance);
		
		System.out.println(this.getCurrentDirection());
		
		try {
			System.out.println(this.getCurrentPosition()[0] + " " + this.getCurrentPosition()[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * finds the distance in number of steps to the next wall
	 * why do we need the direction?
	 */
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		CardinalDirection cDirection = null;
		int distance = 0; //distance to the wall
		
		if (!this.isAtGoal()){ //looking at exit outside maze causes out of bounds error
		
			//converts direction tob Cardinal direction
			if (direction == Direction.FORWARD){
				cDirection = mazeController.getCurrentDirection();
				
			}else if (direction == Direction.LEFT){
				cDirection = mazeController.getCurrentDirection().oppositeDirection().rotateClockwise();
				
			}else if (direction == Direction.RIGHT){
				cDirection = mazeController.getCurrentDirection().rotateClockwise();
				
			}else if (direction == Direction.BACKWARD){
				cDirection = mazeController.getCurrentDirection().oppositeDirection();
				
			}
	
			Cells cells = mazeController.getMazeConfiguration().getMazecells(); //need to use the Cells methods
			
			
			//checks if there are no walls in the given direction and adds up the spaces to the next wall
			if (!cells.hasWall(mazeController.getCurrentPosition()[0], mazeController.getCurrentPosition()[1], cDirection)){
				System.out.println("No Wall this way!"); //for my sake
				
				if (mazeController.getCurrentDirection() == CardinalDirection.East){
					for (int i = this.mazeController.getCurrentPosition()[0]; i < this.mazeController.getMazeConfiguration().getWidth(); i++){ //iterate i up as east represents right on the x axis
						if(!cells.hasWall(i, mazeController.getCurrentPosition()[1], mazeController.getCurrentDirection())){ // for each cell without a wall, increment hte distance
							distance++;
						}else{
							break;
						}
					}
					
				}else if (mazeController.getCurrentDirection() == CardinalDirection.North){
					for (int i = this.mazeController.getCurrentPosition()[1]; i > 0; i--){ //iterate i down as north represents down on the y axis
						if(!cells.hasWall(mazeController.getCurrentPosition()[0], i, mazeController.getCurrentDirection())){
							distance++;
						}else{
							break;
						}
					}
					
				}else if (mazeController.getCurrentDirection() == CardinalDirection.West){
					for (int i = this.mazeController.getCurrentPosition()[0]; i > 0; i--){ //iterate i down as west represents left on the x axis
						if(!cells.hasWall(i, mazeController.getCurrentPosition()[1], mazeController.getCurrentDirection())){
							distance++;
						}else{
							break;
						}
					}
					
				}else if (mazeController.getCurrentDirection() == CardinalDirection.South){
					for (int i = this.mazeController.getCurrentPosition()[1]; i < this.mazeController.getMazeConfiguration().getHeight(); i++){ //iterate i up as south represents up on the y axis
						if(!cells.hasWall(mazeController.getCurrentPosition()[0], i, mazeController.getCurrentDirection())){
							distance++;
						}else{
							break;
						}
					}		
				} 
			}	
		}
		return distance;
	}

	/**
	 * returns an array with the x and y values of the current position.
	 * the robot should start at position 3,1
	 */
	public int[] getCurrentPosition() throws Exception {
		return mazeController.getCurrentPosition();
	}

	/**
	 * Tells if current position is at the goal (the exit). 
	 * Used to recognize termination of a search!
	 */
	public boolean isAtGoal() {
		//use conditionals to check whether an exit can be found to the east, west, north, or south.
		//this is regardless of the direction the user is facing
		Cells cells = mazeController.getMazeConfiguration().getMazecells();
		
		if (cells.isExitPosition(mazeController.getCurrentPosition()[0], mazeController.getCurrentPosition()[1])){
			return true;
		}else if (cells.isExitPosition(mazeController.getCurrentPosition()[0] , mazeController.getCurrentPosition()[1])){
			return true;
		}else if (cells.isExitPosition(mazeController.getCurrentPosition()[0], mazeController.getCurrentPosition()[1])){
			return true;
		}else if (cells.isExitPosition(mazeController.getCurrentPosition()[0], mazeController.getCurrentPosition()[1] )){
			return true;
		}
		//not at goal otherwise
		return false;

	}

	/**
	 * Tells if a sensor can identify the goal in given direction relative to the robot's current forward 
	 * direction from the current position.
	 */
	public boolean canSeeGoal(Direction direction) throws UnsupportedOperationException {
		
		//check if the distance in the direction is infinite. This means it is outside the maze and thus the exit
		if (this.distanceToObstacle(direction) == Integer.MAX_VALUE){
			return true;
		}
		return false;
	}

	/**
	 * Tells if the given position is inside a room.
	 * This is false after cells.initialize() and before calling setInRoomToOne() or markAreaAsRoom().
	 * @param x coordinate of cell
	 * @param y coordinate of cell
	 * @precondition 0 <= x < width, 0 <= y < height
	 * @return true if (x,y) position resides in an area marked as a room before, false otherwise
	 */
	public boolean isInsideRoom() throws UnsupportedOperationException {
		return mazeController.getMazeConfiguration().hasMaskedBitsTrue(mazeController.getCurrentPosition()[0], mazeController.getCurrentPosition()[1], Constants.CW_IN_ROOM);
	}

	/*
	 * use MazeConfiguration room sensor
	 */
	public boolean hasRoomSensor() {
		return false;
	}

	
	/**
	 * returns the current direction the robot faces
	 */
	public CardinalDirection getCurrentDirection() {
		return mazeController.getCurrentDirection();
	}

	/*
	 *returns the battery level 
	 */
	public float getBatteryLevel() {
		return this.batteryLevel;
	}

	/*
	 * sets the battery level
	 */
	public void setBatteryLevel(float level) {
		this.batteryLevel = level;
	}
	
	/*
	 * returns amount of energy required for sensing in any direction
	 */
	public float getEnergyForSense() {
		return 1;
	}
	
	/*
	 * returns amount of energy required for a 90 degree turn
	 */
	public float getEnergyForTurn() {
		return 3;
	}

	/*
	 * returns amount of energy required to turn 360 degrees
	 */
	public float getEnergyForFullRotation() {
		return 12;
	}

	/*
	 * returns amount of energy required to move forward by one cell
	 */
	public float getEnergyForStepForward() {
		return 5;
	}

	/*
	 * tells if the robot has stopped due to lack of energy
	 */
	public boolean hasStopped() {
		if(this.getBatteryLevel() <= 0){
			return true;
		}
		return false;
	}


	/*
	 * there is a distance sensor for each direction, so always return true
	 */
	public boolean hasDistanceSensor(Direction direction) {
		return true;
	}

}
