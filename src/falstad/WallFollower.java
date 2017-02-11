package falstad;

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Cells;
import generation.Distance;
import generation.MazeConfiguration;

/*
 * WallFollower class implements the RobotDriver interface. It solves the maze by following a path alongside the wall to its left. 
 * It operates on the BasicRobot class. 
 * 
 * @author michaelCrombie
 */

public class WallFollower implements RobotDriver{
	
	BasicRobot basicRobot; //initialize robot
	private int pathLength = 0;
	
	public WallFollower(BasicRobot basicRobot){
		setRobot(basicRobot);
	}

	/**
	 * initializes the robot
	 */
	public void setRobot(Robot r) {
		basicRobot = (BasicRobot) r;
	}

	/**
	 * Sets the dimensions for the robots MazeConfiguration
	 */
	public void setDimensions(int width, int height) {
		basicRobot.mazeController.getMazeConfiguration().setHeight(height);
		basicRobot.mazeController.getMazeConfiguration().setWidth(width);
	}

	/**
	 * sets the distance to the exit for the Robot's MazeConfiguration
	 */
	public void setDistance(Distance distance) {
		basicRobot.mazeController.getMazeConfiguration().setMazedists(distance);
		
	}

	/*
	 * executes the wall following algorithm that finds the exit by always following along the wall to its left side
	 */
	public boolean drive2Exit() throws Exception {
		
		Cells cells = basicRobot.mazeController.getMazeConfiguration().getMazecells(); //need to use the Cells methods
		
		//keep going until the goal is reached or there is no energy left
		while (!basicRobot.mazeController.isOutside(basicRobot.getCurrentPosition()[0], basicRobot.getCurrentPosition()[1]) && this.getEnergyConsumption() < 2500){ 
			
			int x = basicRobot.getCurrentPosition()[0];
			int y = basicRobot.getCurrentPosition()[1];
			CardinalDirection direction = basicRobot.getCurrentDirection();
				
			//if there is a wall to the left but no wall in front, move forward one cell
			if (cells.hasWall(x , y, direction.rotateClockwise()) && !cells.hasWall(x, y, direction)){
				
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - 2 * basicRobot.getEnergyForSense()); //decrease batteryLevel for having to sense walls
				
				basicRobot.move(1, false);
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - basicRobot.getEnergyForStepForward()); //decrease batteryLevel for movement
				
				pathLength++; //iterate pathLength for each move
			}
			//if there is a wall in front and to the left, turn right 
			else if (cells.hasWall(x, y, direction) && cells.hasWall(x, y, direction.rotateClockwise())){
				
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - 2 * basicRobot.getEnergyForSense()); //decrease batteryLevel for having to sense walls
				
				basicRobot.rotate(Turn.RIGHT);
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - basicRobot.getEnergyForTurn()); //decrease batteryLevel for turn
			}

			//if there is no wall to the left, turn left and move forward
			else if (!cells.hasWall(x , y, direction.rotateClockwise())){
				
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - basicRobot.getEnergyForSense()); //decrease batteryLevel for having to sense walls
				
				
				basicRobot.rotate(Turn.LEFT);
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - basicRobot.getEnergyForTurn()); //decrease batteryLevel for turn
				
				basicRobot.move(1, false);
				basicRobot.setBatteryLevel(basicRobot.getBatteryLevel() - basicRobot.getEnergyForStepForward()); //decrease batteryLevel for movement
				
				pathLength++; //iterate pathLength for each move
			}
			

		}
		
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		float energy = 2500 - basicRobot.getBatteryLevel();
		return energy;
	}

	/**
	 * Returns the total length of the journey in number of cells traversed. Being at the initial position counts as 0. 
	 * This is used as a measure of efficiency for a robot driver.
	 */
	public int getPathLength() {
		return pathLength;
	}

}
