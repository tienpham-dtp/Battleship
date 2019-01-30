package battleship;

public class Submarine extends Ship {
	
static final int s_length = 1;

	@Override
	public int getLength() {
		return s_length;
	}

	@Override
	public String getShipType() {
		return "submarine";
	}
}
