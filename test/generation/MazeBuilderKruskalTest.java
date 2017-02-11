package generation;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import generation.Order.Builder;


/*
 *For white box testing, develop a
 *new test class MazeFactoryKruskalTest.java to test specifics of your new MazeBuilderKruskal
 *class.
*/

public class MazeBuilderKruskalTest extends MazeBuilderKruskal{

	
	/**
	 * tests for some basic getters and setters to understand testing and confirm the right builder is called
	 */
	@Test
	public void testBasics() {
		
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory = new MazeFactory();
		StubOrder stubOrder = new StubOrder(Order.Builder.Kruskal);
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		
		//skill level initially 0
		assertEquals(0, stubOrder.getSkillLevel());
		
		//check setting and getting skill level
		stubOrder.setSkillLevel(2);
		assertEquals(2, stubOrder.getSkillLevel());
		
		//builder default set to Kruskal
		assertEquals(Builder.Kruskal, stubOrder.getBuilder());
		
		//check setting and getting builder
		stubOrder.setBuilder(Builder.DFS);
		assertEquals(Builder.DFS, stubOrder.getBuilder());
		
	}
	

	/**
	 * The array of cells is built so that the width must be greater than or equal to the height of the maze.
	 *If the height is somehow greater, there will be an outOfBounds errors. Some of the skill levels have widths
	 *that are greater than their heights (i.e. levels 3,4,6,B,C,D,E,F), but there are none that have heights 
	 *greater than widths, so this error should not come up without the dimensions being tampered with. 
	 */
	@Test
	public void testWidthGreaterOrEqualHeight(){
		//instantiate mazeFactory using mazeConfgiuration gathered using the stubOrder stub
		MazeFactory mazeFactory = new MazeFactory();
		StubOrder stubOrder = new StubOrder(Order.Builder.Kruskal);
		//stubOrder.deliver(stubOrder.getMazeConfiguration());
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		
		Cells cells = stubOrder.getMazeConfiguration().getMazecells(); //get cells from current maze
		
		assertTrue(cells.getWidth() >= cells.getHeight()); //checks width >= height
		
		
	}
	
	/**
	 * tests to make sure the Tree data structure successfully joins the sets
	 */
	@Test
	public void testSetConnecting(){
		
		//create two empty sets
		Tree set1 = new Tree();
		Tree set2 = new Tree();
		//join them
		set1.connect(set2);
		//test they are in the smae set
		assertTrue(set1.root() == set2.root());
		
	}
	

	/**
	 * tests to make sure the Tree data structure successfully joins the sets
	 */
	@Test
	public void testExtractWall(){
		
		//initializes an empty array to store walls into
		final ArrayList<Wall> candidates = new ArrayList<Wall>();
		
		//adds 16 walls into the array
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				Wall wall = new Wall (0,0,CardinalDirection.East);
				candidates.add(wall);
			}
		}
		
		int count = 0; //used to count the number of walls
		Wall curWall;
		while(!candidates.isEmpty()){
			curWall = extractWallFromCandidateSetRandomly(candidates);
			count++;
		}
		assertEquals(16,count); //makes sure that all 16 walls were removed.

	}
	
}
