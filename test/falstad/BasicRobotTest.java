package falstad;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Cells;
import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.Order;

/*
 * Tests for BasicRobot class
 * 
 * @author michaelCrombie
 */

public class BasicRobotTest {

	//initializing variables
	BasicRobot basicRobot;
	private MazeController controller ;
	private String driver = "driver";
	private String fileName = "fileName";
	
	
	/**
	 * Tests the robot's ability to rotate and thus change the direction it is facing.
	 */
	@Test
	public void testRotationandDirections() {
		//initialize by creating a new factory and what not
		controller = new MazeController(Order.Builder.Kruskal, driver, fileName);
		MazeFactory mazeFactory = new MazeFactory();
		mazeFactory.order(controller);
		mazeFactory.waitTillDelivered();
		basicRobot = new BasicRobot(controller);
		
		//the initial direction is always east
		//rotates the robot left
		basicRobot.rotate(Turn.LEFT); //rotation left
		//the robot should now be facing South
		assertEquals(CardinalDirection.South, basicRobot.getCurrentDirection());
		//rotate left once more
		basicRobot.rotate(Turn.LEFT); //rotation left
		//the robot should now be facing West
		assertEquals(CardinalDirection.West, basicRobot.getCurrentDirection());
		//rotate left once more
		basicRobot.rotate(Turn.LEFT); //rotation left
		//the robot should now be facing North
		assertEquals(CardinalDirection.North, basicRobot.getCurrentDirection());
		//rotate left once more
		basicRobot.rotate(Turn.LEFT); //rotation left
		//the robot should now be facing East
		assertEquals(CardinalDirection.East, basicRobot.getCurrentDirection());
		
		//now test for rotating robot right
		basicRobot.rotate(Turn.RIGHT); //rotation right
		//the robot should now be facing North
		assertEquals(CardinalDirection.North, basicRobot.getCurrentDirection());
		//rotate right once more
		basicRobot.rotate(Turn.RIGHT); //rotation right
		//the robot should now be facing West
		assertEquals(CardinalDirection.West, basicRobot.getCurrentDirection());
		//rotate right once more
		basicRobot.rotate(Turn.RIGHT); //rotation right
		//the robot should now be facing South
		assertEquals(CardinalDirection.South, basicRobot.getCurrentDirection());
		//rotate right once more
		basicRobot.rotate(Turn.RIGHT); //rotation right
		//the robot should now be facing East
		assertEquals(CardinalDirection.East, basicRobot.getCurrentDirection());
		
		//now test for rotating the robot around
		basicRobot.rotate(Turn.AROUND); //rotation around
		//the robot should now be facing West
		assertEquals(CardinalDirection.West, basicRobot.getCurrentDirection());
		//rotate around once more
		basicRobot.rotate(Turn.AROUND); //rotation around
		//the robot should now be facing East
		assertEquals(CardinalDirection.East, basicRobot.getCurrentDirection());

	}
	
	/**
	 * Tests the robot's ability to move forward in different directions
	 * @throws Exception 
	 */
	@Test
	public void testMovement() throws Exception {
		//initialize by creating a new factory and what not
		controller = new MazeController(Order.Builder.Kruskal, driver, fileName);
		MazeFactory mazeFactory = new MazeFactory();
		mazeFactory.order(controller);
		mazeFactory.waitTillDelivered();
		basicRobot = new BasicRobot(controller);

		//go through each position in the maze
		for (int i = 1; i < controller.getMazeConfiguration().getWidth(); i++){
			for (int j = 1; j < controller.getMazeConfiguration().getHeight(); j++){
				int currentX = i; 
				int currentY = j;
				
				basicRobot.move(1, false); // move forward by one
				
				for (CardinalDirection cDirection: CardinalDirection.values()){ // go through each possible direction
					if (basicRobot.distanceToObstacle(Direction.FORWARD) != 0){ //make sure there isn't a wall in the way
						if (cDirection == CardinalDirection.East){
							assertTrue(basicRobot.getCurrentPosition()[0] == currentX + 1);
						}else if (cDirection == CardinalDirection.West){
							assertTrue(basicRobot.getCurrentPosition()[0] == currentX - 1);
						}else if (cDirection == CardinalDirection.North){
							assertTrue(basicRobot.getCurrentPosition()[1] == currentY - 1);
						}else if (cDirection == CardinalDirection.South){
							assertTrue(basicRobot.getCurrentPosition()[1] == currentY + 1);
						}
					}
				}
			}
		}	
	}
	
	/**
	 * Tests the robot's ability to sense the distance to an obstacle in any given direction
	 * @throws Exception 
	 */
	@Test
	public void testDirectionSensor() throws Exception{
		//initialize by creating a new factory and what not
		controller = new MazeController(Order.Builder.Kruskal, driver, fileName);
		MazeFactory mazeFactory = new MazeFactory();
		mazeFactory.order(controller);
		mazeFactory.waitTillDelivered();
		basicRobot = new BasicRobot(controller);
		
		Cells cells = controller.getMazeConfiguration().getMazecells(); //use cells to check for walls
		

		if (basicRobot.distanceToObstacle(Direction.FORWARD) == 0){
			assertTrue(cells.hasWall(basicRobot.getCurrentPosition()[0], basicRobot.getCurrentPosition()[1], basicRobot.getCurrentDirection()));
		}
			
	}
}
