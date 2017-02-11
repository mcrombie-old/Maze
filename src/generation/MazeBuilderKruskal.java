package generation;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;



public class MazeBuilderKruskal extends MazeBuilder implements Runnable{
	
	
	/**
	 * constructor without parameters
	 */
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderPrim uses Kruskal's algorithm to generate maze.");
	}
	
	/**
	 * constructor with a parameter for the determinant. It decides whether the maze is perfect or not. 
	 * @param det
	 */
	
	public MazeBuilderKruskal(boolean det) {
		super(det);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	
	/**
	 * generates the maze using a randomized version of Kruskal's algorithm!
	 */
	
	@Override
	protected void generatePathways() {

		final ArrayList<Wall> candidates = new ArrayList<Wall>();
		createListOfWalls(candidates); //creates a list of all the walls that can be removed
		
		
		ArrayList<ArrayList<Tree>> sets = new ArrayList<ArrayList<Tree>>(); // Tree objects represent the sets to be joined
		
		for ( int i = 0; i < width; i++ ) { // Initialize the sets to the same dimension as the maze
			ArrayList<Tree> tmp = new ArrayList<Tree>();
			for ( int j = 0; j < height; j++ ) { //note that width must come before height or maze won't configure for skill 
				tmp.add(new Tree());			//levels with different width and height values
			}
			sets.add(tmp);
		}
		
		Wall curWall; //initialize current wall
		
		while(!candidates.isEmpty()){ // loop through the list of walls and consider each one only once

			curWall = extractWallFromCandidateSetRandomly(candidates); //randomly select a wall
			
			if (cells.canGo(curWall)){ // check if the wall can be gone through to account for rooms
			
				Tree set1 = sets.get(curWall.getX()).get(curWall.getY());//get position of the current wall (set1) 
				Tree set2 = sets.get(curWall.getNeighborX()).get(curWall.getNeighborY()); //and its neighbor (set2)
				
				if (!set1.connected(set2)){ //if the cells are not already in the same path we will delete
					cells.deleteWall(curWall); //delete wall from maze
					set1.connect(set2); //connect the sets so they are in the same path
				}
			}
		}
	}
	
	
	/**
	 * Pick a random position in the list of candidates, remove the candidate from the list and return it
	 * @param candidates
	 * @return candidate from the list, randomly chosen
	 * (borrowed from MazeBuilderPrim.java)
	 */
	protected Wall extractWallFromCandidateSetRandomly(final ArrayList<Wall> candidates) {
		return candidates.remove(random.nextIntWithinInterval(0, candidates.size()-1)); 
	}
	

	/**
	 * creates a list of all the walls in the maze before any deletions
	 * @param walls
	 */
	
	protected void createListOfWalls(ArrayList<Wall> walls) {
		//loops through each cell
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				Wall wall = new Wall(i, j, CardinalDirection.East) ;
				//set a wall at each direction
				
				for (CardinalDirection cd : CardinalDirection.values()) {
					wall.setWall(i, j, cd);
					if (cells.canGo(wall)) // 
					{
						walls.add(new Wall(i, j, cd));
					}
				}
			}
		}

	}

}
