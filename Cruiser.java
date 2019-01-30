package battleship;

public class Cruiser extends Ship {
	/*
	 * constructor for cruiser
	 */
	static final int c_length = 3;


	@Override
	public int getLength() {
		return c_length;
	}

	@Override
	public String getShipType() {
		return "cruiser";
	}
	
}
