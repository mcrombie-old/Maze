package generation;

import falstad.MazeController;
import falstad.MazePanel;
import generation.Order.Builder;

public class StubOrder implements Order {
	
	private MazeConfiguration mazeConfig ; 
	
	private Factory factory;
	
	private MazePanel panel ; 
	
	private String filename;
	
	// about the maze and its generation
	private int skill; // user selected skill level, i.e. size of maze
	private Builder builder; // selected maze generation algorithm
	
	/**
	 * Constructor
	 * Default setting for maze generating algorithm is DFS.
	 */
	public StubOrder() {
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
	public StubOrder(Order.Builder builder)
	{
		super() ;
		setBuilder(builder) ;
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
