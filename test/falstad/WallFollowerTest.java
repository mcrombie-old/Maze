package falstad;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import generation.MazeFactory;
import generation.Order;

/*
 * testing code for wallFollower class
 * 
 * @author michaelCrombie
 */

public class WallFollowerTest {
	
	//initializing variables
	private BasicRobot basicRobot;
	private WallFollower wallFollower;
	private MazeController controller ;
	private String driver = "driver";
	private String fileName = "fileName";
	
	/*
	 * tests whether the exit is reached or not
	 */
	@Test
	public void testFindsExit() throws Exception {
		//initialize by creating a new factory and what not
		controller = new MazeController(Order.Builder.Kruskal, driver, fileName);
		MazeFactory mazeFactory = new MazeFactory();
		mazeFactory.order(controller);
		mazeFactory.waitTillDelivered();
		basicRobot = new BasicRobot(controller);
		wallFollower = new WallFollower(basicRobot);
		
		//drive the robot
		wallFollower.drive2Exit();
		//check if it is outside. If so, it must have found the exit. 
		assertTrue(basicRobot.mazeController.isOutside(basicRobot.getCurrentPosition()[0], basicRobot.getCurrentPosition()[1]));
	}
	
	/*
	 * tests that the pathLength and energyConsumption getters are working and being incremented after driving
	 */
	
	@Test
	public void testPathLengthAndEnergy() throws Exception{
		//initialize by creating a new factory and what not
		controller = new MazeController(Order.Builder.Kruskal, driver, fileName);
		MazeFactory mazeFactory = new MazeFactory();
		mazeFactory.order(controller);
		mazeFactory.waitTillDelivered();
		basicRobot = new BasicRobot(controller);
		wallFollower = new WallFollower(basicRobot);
		
		//pathLength should start at 0
		assertEquals(wallFollower.getPathLength(), 0);
		//energy consumption should start at 0
		assertTrue(wallFollower.getEnergyConsumption() == 0);
		
		//drive the robot
		wallFollower.drive2Exit();
		
		//pathLength should have gone up
		assertTrue(wallFollower.getPathLength() > 0);
		//energy consumption should have gone up
		assertTrue(wallFollower.getEnergyConsumption() > 0);
		
	}

}
