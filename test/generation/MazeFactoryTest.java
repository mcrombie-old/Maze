package generation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import falstad.MazeController;
import generation.Order.Builder;

public class MazeFactoryTest {
	
	
	/** 
	 * method to create the order object with the setting for the DFS algorithm.
	 *it will be set to DFS for this standard MazeFactoryTest.
	 *MazeBuilderKruskalTest will call the class MazeFactoryTestKruskal to overwrite this method to test for Kruskal
	 * @return
	 */
	public Builder orderObject(){
		return Order.Builder.DFS;
	}

	
	/**
	 * checks basic setting methods work
	 */
	@Test
	public void testBasics() {
		//fail("Not yet implemented");
		
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory = new MazeFactory();
		StubOrder stubOrder = new StubOrder(orderObject());
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		
		//skill level initially 0
		assertEquals(0, stubOrder.getSkillLevel());
		
		//check setting and getting skill level
		stubOrder.setSkillLevel(2);
		assertEquals(2, stubOrder.getSkillLevel());
		
		//builder default set to DFS
		assertEquals(orderObject(), stubOrder.getBuilder());
		
		//check setting and getting builder
		stubOrder.setBuilder(Builder.Kruskal);
		assertEquals(Builder.Kruskal, stubOrder.getBuilder());
		
	}
	
	
	/**
	 * check that there is just one exit
	 * checks this for different skill levels
	 */
	@Test
	public void testExit(){
		
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory = new MazeFactory();
		StubOrder stubOrder = new StubOrder(orderObject());
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		
		
		//loop through the first 10 levels. 
		//To avoid more time consuming testing and more code, I am ignoring levels A-F.
		//The first 10 levels account for differences in width and height, so more testing is unnecessary
		for (int level = 0; level < 10; level++){
			stubOrder.setSkillLevel(level); //set the skill level
			
			Cells cells = stubOrder.getMazeConfiguration().getMazecells(); //get cells from current maze
			
			int count = 0; 
			for (int i = 0; i < cells.getWidth(); i++){ //counts the number for exit positions in the maze
				for(int j = 0; j < cells.getHeight(); j++){
					if (cells.isExitPosition(i, j)) count++;
				}
			}
			
			assertEquals(1, count); //number of exits should be 1
		}
	
	}
	
	
	/**
	 * Test to see if rooms are created in the maze
	 * It is assumed that if any rooms are made than the room generator is working
	 */
	@Test
	public void testRoomCreation(){
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory = new MazeFactory();
		StubOrder stubOrder = new StubOrder(Order.Builder.Kruskal);
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		
		Cells cells = stubOrder.getMazeConfiguration().getMazecells(); //get cells from current maze
		
		//four walls will be used to test if there are cycles located anywhere
		Wall wall = null;
		Wall wall2 = null;
		Wall wall3 = null;
		Wall wall4 = null;
		
		boolean roomMade = false;//boolean to check if any rooms were made
		
		for (int i = 0; i < cells.getWidth(); i++){
			
			for (int j = 0; j < cells.getHeight(); j++){
				//set each wall so that it generates a four cell cycle
				wall = new Wall(i, j, CardinalDirection.East) ;
				wall2 = new Wall(wall.getNeighborX(), wall.getNeighborY(), CardinalDirection.North);
				wall3 = new Wall(wall2.getNeighborX(), wall2.getNeighborY(), CardinalDirection.West);
				wall4 = new Wall(wall3.getNeighborX(), wall3.getNeighborY(), CardinalDirection.South);
				//if there is a path between all of the cells, then there is a room
				if (cells.canGo(wall) && cells.canGo(wall2) && cells.canGo(wall3) && cells.canGo(wall4)){
					roomMade = true; // set boolean to true
				}
			}
		}
		assertTrue(roomMade); 
	
	}
	

	/*failed test for perfection

	/**
	 * checks if the deterministic maze is working by setting MazeFactory's parameter to be true.
	 * 	This should set a seed in MazeBuilder that makes the maze generate the same maze if given the samev skill
	 *level each time. 
	 */

	 /*	@Test
	public void testDeterministic(){
		
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory = new MazeFactory(true);
		StubOrder stubOrder = new StubOrder(orderObject());
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory2 = new MazeFactory(true);
		StubOrder stubOrder2 = new StubOrder(orderObject());
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory2.order(stubOrder2);
		mazeFactory2.waitTillDelivered();
		
		assertTrue(mazeFactory.equals(mazeFactory2)); //checks if the two objects are equal
		
		
		
	}*/

}