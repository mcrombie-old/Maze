package generation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import generation.Order.Builder;

public class MazeFactoryTestKruskal extends MazeFactoryTest{

	//simply overrides the OrderObject method in MazeFactoryTest to set it to Kruskal.
	//This allows me to run the MazeFactory tests on the Kruskal implementation. 
	@Override
	public Builder orderObject(){
		return Order.Builder.Kruskal;
	}


}
