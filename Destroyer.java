package battleship;

public class Destroyer extends Ship {
	static final int d_length = 2;

	@Override
	public int getLength() {
		return d_length;
	}

	@Override
	public String getShipType() {
		return "destroyer";
	}
}
