package falstad;

import falstad.MazeController;
import falstad.MazePanel;
import generation.Factory;
import generation.MazeConfiguration;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.Order;
import generation.Order.Builder;

public class RobotStub implements Order {
	
	private MazeConfiguration mazeConfig ; 
	
	private Factory factory;
	
	private MazePanel panel ; 
	
	private String filename;
	
	protected MazeController controller;
	
	// about the maze and its generation
	private int skill; // user selected skill level, i.e. size of maze
	private Builder builder; // selected maze generation algorithm
	
	/**
	 * Constructor
	 * Default setting for maze generating algorithm is DFS.
	 */
	public RobotStub() {
		super() ;
		setBuilder(Order.Builder.DFS); 
		panel = new MazePanel() ;
		mazeConfig = new MazeContainer();
		factory = new MazeFactory() ;
		filename = null;
	}
	/**
	 * Constructor that also selects a particular generation method
	 */
	public RobotStub(String builder, String driver, String fileName)
	{
		super() ;
		controller = new MazeController(Order.Builder.Kruskal, driver, fileName) ;
		panel = new MazePanel() ;
		mazeConfig = new MazeContainer();
		factory = new MazeFactory() ;
		filename = null;
	}
	@Override
	public int getSkillLevel() {
		return skill;
	}

	@Override
	public Builder getBuilder() {
		return builder;
	}

	@Override
	public boolean isPerfect() {
		
		return false;
	}

	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		this.mazeConfig = mazeConfig ;
		
	}
	
	public MazeConfiguration getMazeConfiguration() {
		return mazeConfig ;
	}

	@Override
	public void updateProgress(int percentage) {
		
	}
	
	////////// set methods for fields ////////////////////////////////
	public void setSkillLevel(int skill) {
		this.skill = skill ;
	}

	public void setBuilder(Builder builder) {
		this.builder = builder ;
	}


}
